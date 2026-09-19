package org.ong.dryforest.service.plantation;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.ong.dryforest.dto.plantation.PlantationDTO;
import org.ong.dryforest.dto.plantation.PlantationStatusByYearDTO;
import org.ong.dryforest.dto.plantation.PlantationViewDTO;
import org.ong.dryforest.dto.plantation.SurvivalRateDTO;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockSurvivalRateDTO;
import org.ong.dryforest.dto.species.SpeciesCarbonDTO;
import org.ong.dryforest.entity.Plantation;
import org.ong.dryforest.entity.PlantationBlock;
import org.ong.dryforest.entity.PlantingMonitoring;
import org.ong.dryforest.entity.SubPlot;
import org.ong.dryforest.mapper.PlantationMapper;
import org.ong.dryforest.repository.PlantationRepository;
import org.ong.dryforest.repository.PlantingMonitoringRepository;
import org.ong.dryforest.service.plantationBlock.PlantationBlockService;
import org.ong.dryforest.service.reforestation.ReforestationService;
import org.ong.dryforest.service.species.SpeciesService;
import org.ong.dryforest.service.subPlot.SubPlotService;
import org.ong.dryforest.service.util.BlockStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
public class PlantationServiceImpl implements PlantationService {
    // Constante pour le ratio de carbone (0.47 pour les forêts tropicales)
    private static final double CARBON_RATIO = 0.47;
    
    @Autowired
    PlantationRepository plantationRepository;
    @Autowired
    SpeciesService speciesService;
    @Autowired
    ReforestationService reforestationService;
    @Autowired
    SubPlotService subPlotService;
    @Autowired
    PlantationBlockService plantationBlockService;
    @Autowired
    PlantingMonitoringRepository plantingMonitoringRepository;

    @Override
    public List<Plantation> findAll(){ return plantationRepository.findAllByIsDeletedFalse(); }

    @Override
    public Plantation createPlantation(Plantation plantation){
        try {
            plantation.set_synced(true);
            return plantationRepository.save(plantation);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Pointage déjà existant");
        }
    }


    @Override
    public Plantation findById(int id_plantation){
        return plantationRepository.findByIdAndIsDeletedFalse(id_plantation).orElseThrow(() -> new RuntimeException("Plantation not found for this id plantation"));
    }

    @Override
    public Plantation findByUuid(UUID uuid){
        return plantationRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Plantation not found by uuid: " + uuid));
    }

    @Override
    public Plantation updatePlantation(Plantation plantation){
        findById(plantation.getId());
        return plantationRepository.save(plantation);
    }

    @Override
    public void deletePlantation(Plantation plantation){
        findById(plantation.getId());
        try {
            plantationRepository.delete(plantation);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cette plantation");
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid){
        return plantationRepository.existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public Plantation mapToEntity(Map<String, Object> plantationMapping){
        Plantation plantation = new Plantation();

        plantation.setUuid(UUID.fromString((String)plantationMapping.get("uuid_plantation")));
        plantation.setPlant_number((String)plantationMapping.get("plant_number"));
        plantation.setDate_plantation(LocalDate.parse((String)plantationMapping.get("date_plantation")));
        plantation.setDiameter((double)plantationMapping.get("diameter"));
        plantation.setHeight((double)plantationMapping.get("height"));
        plantation.setCarbon_sequestered((double)plantationMapping.get("carbon_sequestered"));
        plantation.setImage((String)plantationMapping.get("image"));
        plantation.setStatus((boolean)plantationMapping.get("status"));

        return  plantation;
    }

    // Méthode pour calculer la biomasse sèche (dry AGB) d'un arbre à partir de D, H, rho
    public static double calculateDryAGB(double diameterCm, double heightM, double woodDensityGcm3) {
        if (diameterCm <= 0 || heightM <= 0 || woodDensityGcm3 <= 0) {
            throw new IllegalArgumentException("Les valeurs doivent être positives.");
        }
        return 0.0673 * Math.pow(woodDensityGcm3 * diameterCm * diameterCm * heightM, 0.976);
    }

    // Méthode pour convertir biomasse humide en biomasse sèche
    public static double convertWetToDryAGB(double wetAGB, double waterContentPercent) {
        if (wetAGB <= 0 || waterContentPercent < 0 || waterContentPercent > 100) {
            throw new IllegalArgumentException("Valeurs invalides pour la biomasse humide ou le contenu en eau.");
        }
        return wetAGB * (1 - waterContentPercent / 100);
    }

    // Méthode pour calculer le carbone séquestré pour un arbre (à partir de dry AGB)
    public static double calculateCarbon(double dryAGB) {
        return CARBON_RATIO * dryAGB;
    }

    @Override
    public Plantation create(PlantationDTO plantationDTO) {
        try {
            Plantation plantation = new Plantation();
            String prefix = "Plt_";
            int plant_num = 1;

            if (plantationDTO.getUuid() == null){
                plantationDTO.setUuid(UUID.randomUUID());
            }
            plantation.setUuid(plantationDTO.getUuid());

            if (plantationRepository.getLastPlantNumber() != null) {
                String last_numb = plantationRepository.getLastPlantNumber();
                String num_plant = last_numb.replace(prefix, "");
                plant_num = Integer.parseInt(num_plant) + 1;
            }
            plantationDTO.setPlant_number(prefix + plant_num);
            plantation.setPlant_number(plantationDTO.getPlant_number());

            if (plantationDTO.getCreatedAt() == null) plantationDTO.setCreatedAt(LocalDateTime.now());
            plantationDTO.setUpdatedAt(LocalDateTime.now());
            plantation.setCreatedAt(plantationDTO.getCreatedAt());
            plantation.setUpdatedAt(plantationDTO.getUpDateAt());

            if (plantationDTO.getIsSynced() == false) {
                plantationDTO.setIsSynced(true);
            }
            plantation.set_synced(plantationDTO.getIsSynced());

            plantation.setDate_plantation(plantationDTO.getDate_plantation());
            plantation.setDiameter(plantationDTO.getDiameter());
            plantation.setHeight(plantationDTO.getHeight());
            plantation.setImage(plantationDTO.getImage());

            double biomasse = calculateDryAGB(plantationDTO.getDiameter(), plantationDTO.getHeight(), speciesService.findSpeciesById(plantationDTO.getId_species()).getDensity());
            plantation.setCarbon_sequestered(calculateCarbon(biomasse));
            
            plantation.setSpecies(speciesService.findSpeciesById(plantationDTO.getId_species()));
            plantation.setReforestation(reforestationService.findById(plantationDTO.getId_reforestation()));
            plantation.setSubPlot(subPlotService.findById(plantationDTO.getId_sub_plot()));
            plantation.setStatus(false);

            return plantationRepository.save(plantation);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Plantation already exist");
        }
    }

    @Override
    public List<PlantationViewDTO> getAllPlantations() {
        List<Object[]> rows = plantationRepository.findAllPlantationsView();
        return PlantationMapper.toPlantationViewDTOList(rows);
    }

    @Override
    public List<PlantationViewDTO> getPlantationsByCriteria(Integer idPlantationBlock,
                                                        Integer idSubPlot,
                                                        Integer idSpecies,
                                                        Date datePlantation) {
        List<Object[]> rows = plantationRepository.searchPlantationsByCriteria(idPlantationBlock, idSubPlot, idSpecies, datePlantation);
        return PlantationMapper.toPlantationViewDTOList(rows);
    }

    @Override
    public List<PlantationViewDTO> getPlantationsByIdPlantationBlock(int blockId) {
        List<Object[]> rows = plantationRepository.findPlantationsByBlockId(blockId);
        return PlantationMapper.toPlantationViewDTOList(rows);
    }

    @Override
    public List<PlantationViewDTO> getPlantationsByBlockAndSubPlot(int blockId, int subPlotId) {
        List<Object[]> rows = plantationRepository.findPlantationsByBlockAndSubPlot(blockId, subPlotId);
        return PlantationMapper.toPlantationViewDTOList(rows);
    }

    @Override
    public List<Map<String, Integer>> getTotalPlantationByBlock() {
        List<PlantationViewDTO> plantations = getAllPlantations();

        // compter par idPlantationBlock
        Map<Integer, Integer> countsByBlockId = new HashMap<>();
        for (PlantationViewDTO p : plantations) {
            if (p == null || p.idPlantationBlock == null) continue;
            countsByBlockId.merge(p.idPlantationBlock, 1, Integer::sum);
        }

        // récupérer tous les plantation blocks (optimisation) — suppose plantationBlockService a une méthode findAll()
        Map<Integer, String> blockIdToName = new HashMap<>();
        try {
            List<PlantationBlock> allBlocks = plantationBlockService.findAll(); // adapte si le nom est different
            for (PlantationBlock b : allBlocks) {
                if (b != null) blockIdToName.put(b.getId(), b.getName());
            }
        } catch (Exception ex) {
            // fallback : si findAll() n'existe pas, on convertira en appel findById pour chaque clé
        }

        // construire le résultat final : List<Map<String,Integer>> où clé = blockName, value = count
        List<Map<String, Integer>> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : countsByBlockId.entrySet()) {
            Integer blockId = e.getKey();
            Integer cnt = e.getValue();

            String blockName = blockIdToName.get(blockId);
            if (blockName == null) {
                // fallback : appeler findById si nécessaire (coûteux)
                try {
                    PlantationBlock pb = plantationBlockService.findById(blockId);
                    blockName = (pb != null) ? pb.getName() : ("#"+blockId);
                } catch (Exception ex) {
                    blockName = ("#"+blockId);
                }
            }
            Map<String, Integer> map = new HashMap<>();
            map.put(blockName, cnt);
            result.add(map);
        }

        return result;
    }

    // @Override
    // public List<Plantation> getPlantationsAutoRegeneration(List<PlantingMonitoring> plantingMonitorings){
    //     List<Plantation> autoRegenerated = new ArrayList<>();
    //     for (PlantingMonitoring plantingMonitoring : plantingMonitorings) {
    //         if (plantingMonitoring.getAuto_generation() > 0) {
    //             Plantation plantation = this.findById(plantingMonitoring.getPlantation().getId());
    //             autoRegenerated.add(plantation);
    //         }
    //     }
    //     return autoRegenerated;
    // }

    // @Override
    // public List<AutoRegenerationByYearDTO> countAutoRegeneratedPlantationsByYear(List<Plantation> plantationsWithAutoGen) {
    //     if (plantationsWithAutoGen == null || plantationsWithAutoGen.isEmpty()) {
    //         return Collections.emptyList();
    //     }

    //     // regroupe par année (protection null date)
    //     Map<Integer, Long> countsByYear = plantationsWithAutoGen.stream()
    //         .filter(Objects::nonNull)
    //         .filter(p -> p.getDate_plantation() != null)           // ignore plantations sans date
    //         .collect(Collectors.groupingBy(
    //             p -> p.getDate_plantation().getYear(),
    //             Collectors.counting()
    //         ));

    //     // transforme en DTO trié par année croissante
    //     return countsByYear.entrySet().stream()
    //         .map(e -> new AutoRegenerationByYearDTO(e.getKey(), e.getValue().intValue()))
    //         .sorted(Comparator.comparing(AutoRegenerationByYearDTO::getYear))
    //         .collect(Collectors.toList());
    // }

    @Override
    public List<SurvivalRateDTO> calculateSurvivalRateByYear(List<PlantingMonitoring> plantingMonitorings) {
        // 1) récupérer les stats annuelles existantes (alive / dead / total)
        List<PlantationStatusByYearDTO> statusByYear = this.plantationStatusByYear(); // méthode existante

        if (statusByYear == null || statusByYear.isEmpty()) {
            return Collections.emptyList();
        }

        // 2) calculer map year -> autoCount. On l'initialise une seule fois, donc effectively final.
        final Map<Integer, Integer> autoCountByYear;
        if (plantingMonitorings == null || plantingMonitorings.isEmpty()) {
            autoCountByYear = Collections.emptyMap();
        } else {
            autoCountByYear = plantingMonitorings.stream()
                .filter(Objects::nonNull)
                .filter(pm -> pm.getAuto_generation() > 0)
                // chaque monitoring est lié à une plantation : on prend l'année de la plantation
                .filter(pm -> pm.getPlantation() != null && pm.getPlantation().getDate_plantation() != null)
                .collect(Collectors.groupingBy(
                    pm -> pm.getPlantation().getDate_plantation().getYear(),   // key = year of plantation
                    Collectors.summingInt(pm -> pm.getAuto_generation())      // sum auto_generation
                ));
        }


        // 3) pour chaque année de statusByYear, calculer les pourcentages
        List<SurvivalRateDTO> result = statusByYear.stream()
            .map(s -> {
                Integer year = s.getYear();
                int alive = s.getAliveCount() == null ? 0 : s.getAliveCount();
                int dead  = s.getDeadCount()  == null ? 0 : s.getDeadCount();
                int total = s.getTotalCount() == null ? 0 : s.getTotalCount();

                double alivePct = 0.0;
                double deadPct = 0.0;
                double autoPct = 0.0;
                

                if (total > 0) {
                    alivePct = ((double) alive / (double) total) * 100.0;
                    deadPct  = ((double) dead  / (double) total) * 100.0;
                    int autoCount = autoCountByYear.getOrDefault(year, 0);
                    autoPct   = ((double) autoCount / (double) total) * 100.0;
                }

                // arrondir à 2 décimales (optionnel)
                alivePct = Math.round(alivePct * 100.0) / 100.0;
                deadPct  = Math.round(deadPct * 100.0) / 100.0;
                autoPct  = Math.round(autoPct * 100.0) / 100.0;

                return new SurvivalRateDTO(year, alivePct, deadPct, autoPct);
            })
            .sorted(Comparator.comparing(SurvivalRateDTO::getYear))
            .collect(Collectors.toList());

        return result;
    }

    @Override
    public List<SurvivalRateDTO> calculateSurvivalRateByYearFromPlantingMonitorings(List<PlantingMonitoring> plantingMonitorings) {
        if (plantingMonitorings == null || plantingMonitorings.isEmpty()) {
            return Collections.emptyList();
        }

        return calculateSurvivalRateByYear(plantingMonitorings);
    }

    @Override
    public List<SurvivalRateDTO> survivalRateByYear(){
        List<PlantingMonitoring> plantingMonitorings = plantingMonitoringRepository.findAllByIsDeletedFalse();
        List<SurvivalRateDTO> survivalRate = this.calculateSurvivalRateByYearFromPlantingMonitorings(plantingMonitorings);
        return survivalRate;
    }

    @Override
    public SurvivalRateDTO survivalRateGlobal() {
        // 1) récupérer les stats annuelles (alive / dead / total)
        List<PlantationStatusByYearDTO> statusByYear = this.plantationStatusByYear();

        long totalAlive = 0L;
        long totalDead  = 0L;
        long totalAll   = 0L;

        if (statusByYear != null && !statusByYear.isEmpty()) {
            for (PlantationStatusByYearDTO s : statusByYear) {
                if (s == null) continue;
                totalAlive += (s.getAliveCount()  == null ? 0 : s.getAliveCount());
                totalDead  += (s.getDeadCount()   == null ? 0 : s.getDeadCount());
                totalAll   += (s.getTotalCount()  == null ? 0 : s.getTotalCount());
            }
        }

        // 2) total auto-generation : somme de tous les planting monitorings (auto_generation)
        int totalAutoGen = 0;
        try {
            List<PlantingMonitoring> pms = plantingMonitoringRepository.findAllByIsDeletedFalse();
            if (pms != null && !pms.isEmpty()) {
                totalAutoGen = pms.stream()
                    .filter(Objects::nonNull)
                    .mapToInt(pm -> pm.getAuto_generation())
                    .sum();
            }
        } catch (Exception ex) {
            // ne pas casser l'app si repo indisponible — on considère auto = 0
            totalAutoGen = 0;
        }

        // 3) calcul des pourcentages globaux
        double alivePct = 0.0;
        double deadPct  = 0.0;
        double autoPct  = 0.0;

        if (totalAll > 0) {
            alivePct = ((double) totalAlive / (double) totalAll) * 100.0;
            deadPct  = ((double) totalDead  / (double) totalAll) * 100.0;
            autoPct  = ((double) totalAutoGen / (double) totalAll) * 100.0;
        }

        // arrondir à 2 décimales
        alivePct = Math.round(alivePct * 100.0) / 100.0;
        deadPct  = Math.round(deadPct  * 100.0) / 100.0;
        autoPct  = Math.round(autoPct  * 100.0) / 100.0;

        // on renvoie un DTO "global" : year = null (ou 0 si tu préfères)
        return new SurvivalRateDTO(null, alivePct, deadPct, autoPct);
    }


    // @Override
    // public List<Map<String, Integer>> getTotalPlantationByBlock() {
    //     List<PlantationViewDTO> plantations = getAllPlantations();

    //     Map<String, Integer> counts = new HashMap<>();

    //     for (PlantationViewDTO p : plantations) {
    //         if (p == null || p.idPlantationBlock == null) continue;

    //         int blockId = p.idPlantationBlock;
    //         PlantationBlock plantationBlock = plantationBlockService.findById(blockId);

    //         if (counts.containsKey(blockId)) {
    //             counts.put(plantationBlock.getName(), counts.get(blockId) + 1);
    //         } else {
    //             counts.put(plantationBlock.getName(), 1);
    //         }
    //     }

    //     List<Map<String, Integer>> result = new ArrayList<>();
    //     for (Map.Entry<String, Integer> entry : counts.entrySet()) {
    //         Map<String, Integer> m = new HashMap<>();
    //         m.put(entry.getKey(), entry.getValue());
    //         result.add(m);
    //     }

    //     return result;
    // }

    @Override
    public List<PlantationStatusByYearDTO> plantationStatusByYear(){
        List<Object[]> plantationStatusByYearDTO = plantationRepository.plantationStatusByYear();
        return PlantationMapper.toPlantationStatusByYearDTOList(plantationStatusByYearDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpeciesCarbonDTO> getCarbonSequesteredBySpeciesNative() {
        List<Object[]> rows = plantationRepository.sumCarbonBySpeciesNative();

        return rows.stream()
            .map(r -> {
                // r[0] = species_id (Number), r[1] = species_name (String), r[2] = total_carbon (Number)
                Integer speciesId = null;
                if (r[0] != null) {
                    speciesId = ((Number) r[0]).intValue();
                }

                String speciesName = null;
                if (r.length > 1 && r[1] != null) {
                    speciesName = r[1].toString();
                }

                double total = 0d;
                // total may be at index 2 (or 1 if you change order) — here it's 2
                if (r.length > 2 && r[2] != null) {
                    total = ((Number) r[2]).doubleValue();
                }

                return new SpeciesCarbonDTO(speciesId, speciesName, total);
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlantationBlockSurvivalRateDTO> getSurvivalRateBySpeciesBySubPlotAndBlock() {
        List<Plantation> plantations = plantationRepository.findAllByIsDeletedFalse();

        if (plantations == null || plantations.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Integer, BlockStats> blockMap = new LinkedHashMap<>();

        for (Plantation p : plantations) {
            if (p == null || p.getSpecies() == null || p.getSubPlot() == null) {
                continue;
            }

            SubPlot subPlot = p.getSubPlot();
            PlantationBlock block = subPlot.getPlantation_block(); // adapte si ton getter a un autre nom

            if (block == null || block == null || subPlot == null || p.getSpecies() == null) {
                continue;
            }

            if (block.getId() <= 0 || subPlot.getId() <= 0 || p.getSpecies().getId() <= 0) {
                continue;
            }

            boolean alive = Boolean.TRUE.equals(p.isStatus()); 
            // Si chez toi false = vivant, remplace par :
            // boolean alive = !Boolean.TRUE.equals(p.getStatus());

            BlockStats blockStats = blockMap.computeIfAbsent(
                block.getId(),
                id -> new BlockStats(block.getId(), block.getName())
            );

            blockStats.addPlantation(
                subPlot.getId(),
                subPlot.getName(),
                p.getSpecies().getId(),
                p.getSpecies().getMg_name(),
                alive
            );
        }

        return blockMap.values()
            .stream()
            .map(BlockStats::toDTO)
            .collect(Collectors.toList());
    }
}
