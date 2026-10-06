package org.ong.dryforest.service.plantation;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

@Service
@RequiredArgsConstructor
public class PlantationServiceImpl implements PlantationService {

    // ============================================================
    // CONSTANTES
    // ============================================================

    private static final double CARBON_RATIO = 0.47;

    private static final String PLANT_NUMBER_PREFIX = "Plt_";

    // ============================================================
    // DEPENDENCIES
    // ============================================================

    private final PlantationRepository plantationRepository;

    private final SpeciesService speciesService;

    private final ReforestationService reforestationService;

    private final SubPlotService subPlotService;

    private final PlantationBlockService plantationBlockService;

    private final PlantingMonitoringRepository
            plantingMonitoringRepository;

    // ============================================================
    // READ
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<Plantation> findAll() {

        return plantationRepository
                .findAllByIsDeletedFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public Plantation findById(int id_plantation) {

        return plantationRepository
                .findByIdAndIsDeletedFalse(id_plantation)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Plantation not found for id: "
                                        + id_plantation
                        )
                );
    }
    @Override
    @Transactional(readOnly = true)
    public Plantation findByUuid(UUID uuid) {

        return plantationRepository
                .findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Plantation not found by uuid: "
                                        + uuid
                        )
                );
    }

    @Override
    public boolean existsByUuid(UUID uuid) {

        return plantationRepository
                .existsByUuidAndIsDeletedFalse(uuid);
    }

    // ============================================================
    // CREATE
    // ============================================================

    @Override
    @Transactional
    public Plantation create(PlantationDTO dto) {

        try {

            Plantation plantation = new Plantation();

            // ----------------------------------------------------
            // UUID
            // ----------------------------------------------------

            UUID uuid = dto.getUuid();

            if (uuid == null) {
                uuid = UUID.randomUUID();
            }

            plantation.setUuid(uuid);

            // ----------------------------------------------------
            // NUMERO PLANTATION
            // ----------------------------------------------------

            String plantNumber =
                    generatePlantNumber();

            plantation.setPlant_number(plantNumber);

            // ----------------------------------------------------
            // DATES
            // ----------------------------------------------------

            LocalDateTime createdAt =
                    dto.getCreatedAt() != null
                            ? dto.getCreatedAt()
                            : LocalDateTime.now();

            LocalDateTime updatedAt =
                    dto.getUpdatedAt() != null
                            ? dto.getUpdatedAt()
                            : LocalDateTime.now();

            plantation.setCreatedAt(createdAt);
            plantation.setUpdatedAt(updatedAt);

            // ----------------------------------------------------
            // SYNCHRONISATION
            // ----------------------------------------------------

            plantation.set_synced(true);

            // ----------------------------------------------------
            // INFORMATIONS PLANTATION
            // ----------------------------------------------------

            plantation.setDate_plantation(
                    dto.getDate_plantation()
            );

            plantation.setDiameter(
                    dto.getDiameter()
            );

            plantation.setHeight(
                    dto.getHeight()
            );

            plantation.setImage(
                    dto.getImage()
            );

            // ----------------------------------------------------
            // SPECIES
            // ----------------------------------------------------

            var species =
                    speciesService.findSpeciesEntityById(
                            dto.getId_species()
                    );

            plantation.setSpecies(species);

            // ----------------------------------------------------
            // REFORESTATION
            // ----------------------------------------------------

            var reforestation =
                    reforestationService.findById(
                            dto.getId_reforestation()
                    );

            plantation.setReforestation(reforestation);

            // ----------------------------------------------------
            // SUB PLOT
            // ----------------------------------------------------

            var subPlot =
                    subPlotService.findById(
                            dto.getId_sub_plot()
                    );

            plantation.setSubPlot(subPlot);

            // ----------------------------------------------------
            // CARBONE
            // ----------------------------------------------------

            double biomass = calculateDryAGB(
                    dto.getDiameter(),
                    dto.getHeight(),
                    species.getDensity()
            );

            double carbon =
                    calculateCarbon(biomass);

            plantation.setCarbon_sequestered(carbon);

            // ----------------------------------------------------
            // STATUS
            // ----------------------------------------------------

            plantation.setStatus(false);

            // ----------------------------------------------------
            // SAVE
            // ----------------------------------------------------

            return plantationRepository.save(plantation);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Plantation already exists",
                    e
            );
        }
    }

    /**
     * Génère automatiquement :
     * Plt_1
     * Plt_2
     * Plt_3
     * ...
     */
    private String generatePlantNumber() {

        String lastNumber =
                plantationRepository.getLastPlantNumber();

        if (lastNumber == null ||
                lastNumber.isBlank()) {

            return PLANT_NUMBER_PREFIX + "1";
        }

        try {

            String number =
                    lastNumber.replace(
                            PLANT_NUMBER_PREFIX,
                            ""
                    );

            int next =
                    Integer.parseInt(number) + 1;

            return PLANT_NUMBER_PREFIX + next;

        } catch (NumberFormatException e) {

            return PLANT_NUMBER_PREFIX + "1";
        }
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @Override
    @Transactional
    public Plantation update(
            int id,
            PlantationDTO dto
    ) {

        Plantation plantation =
                findById(id);

        // ----------------------------------------------------
        // UUID
        // ----------------------------------------------------

        if (dto.getUuid() != null) {
            plantation.setUuid(dto.getUuid());
        }

        // ----------------------------------------------------
        // Plant number
        // ----------------------------------------------------

        /*
         * On ne régénère PAS plant_number lors d'un update.
         */
        if (dto.getPlant_number() != null &&
                !dto.getPlant_number().isBlank()) {

            plantation.setPlant_number(
                    dto.getPlant_number()
            );
        }

        // ----------------------------------------------------
        // Informations
        // ----------------------------------------------------

        if (dto.getDate_plantation() != null) {

            plantation.setDate_plantation(
                    dto.getDate_plantation()
            );
        }

        plantation.setDiameter(
                dto.getDiameter()
        );

        plantation.setHeight(
                dto.getHeight()
        );

        if (dto.getImage() != null) {

            plantation.setImage(
                    dto.getImage()
            );
        }

        // ----------------------------------------------------
        // Relations
        // ----------------------------------------------------

        if (dto.getId_species() > 0) {

            var species =
                    speciesService.findSpeciesEntityById(
                            dto.getId_species()
                    );

            plantation.setSpecies(species);

            double biomass =
                    calculateDryAGB(
                            dto.getDiameter(),
                            dto.getHeight(),
                            species.getDensity()
                    );

            plantation.setCarbon_sequestered(
                    calculateCarbon(biomass)
            );
        }

        if (dto.getId_reforestation() > 0) {

            plantation.setReforestation(
                    reforestationService.findById(
                            dto.getId_reforestation()
                    )
            );
        }

        if (dto.getId_sub_plot() > 0) {

            plantation.setSubPlot(
                    subPlotService.findById(
                            dto.getId_sub_plot()
                    )
            );
        }

        // ----------------------------------------------------
        // Sync / date
        // ----------------------------------------------------

        plantation.set_synced(true);
        plantation.setUpdatedAt(
                LocalDateTime.now()
        );

        return plantationRepository.save(
                plantation
        );
    }

    // ============================================================
    // DELETE
    // ============================================================

    @Override
    @Transactional
    public void deleteById(int id) {

        Plantation plantation =
                findById(id);

        /*
         * Suppression logique.
         *
         * On ne supprime pas réellement la ligne SQL,
         * car ton application utilise is_deleted.
         */
        plantation.setDeleted(true);
        plantation.setUpdatedAt(
                LocalDateTime.now()
        );

        plantationRepository.save(
                plantation
        );
    }

    // ============================================================
    // EXISTING METHODS
    // ============================================================

    @Override
    @Transactional
    public Plantation createPlantation(
            Plantation plantation
    ) {

        try {

            plantation.set_synced(true);

            return plantationRepository.save(
                    plantation
            );

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Pointage déjà existant",
                    e
            );
        }
    }

    @Override
    @Transactional
    public Plantation updatePlantation(
            Plantation plantation
    ) {

        findById(plantation.getId());

        plantation.setUpdatedAt(
                LocalDateTime.now()
        );

        return plantationRepository.save(
                plantation
        );
    }

    @Override
    @Transactional
    public void deletePlantation(
            Plantation plantation
    ) {

        Plantation existing =
                findById(plantation.getId());

        try {

            /*
             * On garde ta méthode existante.
             *
             * Si tu veux une suppression logique partout,
             * utilise deleteById() dans le controller.
             */
            plantationRepository.delete(existing);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "Impossible de supprimer cette plantation",
                    e
            );
        }
    }

    // ============================================================
    // SYNC MAP
    // ============================================================

    @Override
    public Plantation mapToEntity(
            Map<String, Object> plantationMapping
    ) {

        Plantation plantation =
                new Plantation();

        plantation.setUuid(
                UUID.fromString(
                        (String) plantationMapping.get(
                                "uuid_plantation"
                        )
                )
        );

        plantation.setPlant_number(
                (String) plantationMapping.get(
                        "plant_number"
                )
        );

        plantation.setDate_plantation(
                LocalDate.parse(
                        (String) plantationMapping.get(
                                "date_plantation"
                        )
                )
        );

        plantation.setDiameter(
                ((Number) plantationMapping.get(
                        "diameter"
                )).doubleValue()
        );

        plantation.setHeight(
                ((Number) plantationMapping.get(
                        "height"
                )).doubleValue()
        );

        plantation.setCarbon_sequestered(
                ((Number) plantationMapping.get(
                        "carbon_sequestered"
                )).doubleValue()
        );

        plantation.setImage(
                (String) plantationMapping.get(
                        "image"
                )
        );

        plantation.setStatus(
                (Boolean) plantationMapping.get(
                        "status"
                )
        );

        return plantation;
    }

    // ============================================================
    // CARBON CALCULATION
    // ============================================================

    public static double calculateDryAGB(
            double diameterCm,
            double heightM,
            double woodDensityGcm3
    ) {

        if (diameterCm <= 0 ||
                heightM <= 0 ||
                woodDensityGcm3 <= 0) {

            throw new IllegalArgumentException(
                    "Les valeurs doivent être positives."
            );
        }

        return 0.0673 *
                Math.pow(
                        woodDensityGcm3 *
                                diameterCm *
                                diameterCm *
                                heightM,
                        0.976
                );
    }

    public static double convertWetToDryAGB(
            double wetAGB,
            double waterContentPercent
    ) {

        if (wetAGB <= 0 ||
                waterContentPercent < 0 ||
                waterContentPercent > 100) {

            throw new IllegalArgumentException(
                    "Valeurs invalides pour la biomasse humide " +
                            "ou le contenu en eau."
            );
        }

        return wetAGB *
                (1 - waterContentPercent / 100);
    }

    public static double calculateCarbon(
            double dryAGB
    ) {

        return CARBON_RATIO * dryAGB;
    }

    // ============================================================
    // VIEWS
    // ============================================================

    @Override
    @Transactional
    public List<PlantationViewDTO>
    getAllPlantations() {

        List<Object[]> rows =
                plantationRepository
                        .findAllPlantationsView();

        return PlantationMapper
                .toPlantationViewDTOList(rows);
    }

    @Override
    @Transactional
    public List<PlantationViewDTO>
    getPlantationsByCriteria(
            Integer idPlantationBlock,
            Integer idSubPlot,
            Integer idSpecies,
            Date datePlantation
    ) {

        List<Object[]> rows =
                plantationRepository
                        .searchPlantationsByCriteria(
                                idPlantationBlock,
                                idSubPlot,
                                idSpecies,
                                datePlantation
                        );

        return PlantationMapper
                .toPlantationViewDTOList(rows);
    }

    @Override
    @Transactional
    public List<PlantationViewDTO>
    getPlantationsByIdPlantationBlock(
            int blockId
    ) {

        List<Object[]> rows =
                plantationRepository
                        .findPlantationsByBlockId(
                                blockId
                        );

        return PlantationMapper
                .toPlantationViewDTOList(rows);
    }

    @Override
    @Transactional
    public List<PlantationViewDTO>
    getPlantationsByBlockAndSubPlot(
            int blockId,
            int subPlotId
    ) {

        List<Object[]> rows =
                plantationRepository
                        .findPlantationsByBlockAndSubPlot(
                                blockId,
                                subPlotId
                        );

        return PlantationMapper
                .toPlantationViewDTOList(rows);
    }

    // ============================================================
    // TOTAL PER BLOCK
    // ============================================================

    @Override
    @Transactional
    public List<Map<String, Integer>>
    getTotalPlantationByBlock() {

        List<PlantationViewDTO> plantations =
                getAllPlantations();

        Map<Integer, Integer> countsByBlockId =
                new HashMap<>();

        for (PlantationViewDTO plantation :
                plantations) {

            if (plantation == null ||
                    plantation.idPlantationBlock == null) {

                continue;
            }

            countsByBlockId.merge(
                    plantation.idPlantationBlock,
                    1,
                    Integer::sum
            );
        }

        Map<Integer, String> blockIdToName =
                new HashMap<>();

        try {

            List<PlantationBlock> allBlocks =
                    plantationBlockService.findAll();

            for (PlantationBlock block :
                    allBlocks) {

                if (block != null) {

                    blockIdToName.put(
                            block.getId(),
                            block.getName()
                    );
                }
            }

        } catch (Exception ignored) {
        }

        List<Map<String, Integer>> result =
                new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry :
                countsByBlockId.entrySet()) {

            Integer blockId = entry.getKey();

            Integer count = entry.getValue();

            String blockName =
                    blockIdToName.get(blockId);

            if (blockName == null) {

                try {

                    PlantationBlock block =
                            plantationBlockService
                                    .findById(blockId);

                    blockName =
                            block != null
                                    ? block.getName()
                                    : "#" + blockId;

                } catch (Exception e) {

                    blockName =
                            "#" + blockId;
                }
            }

            Map<String, Integer> map =
                    new HashMap<>();

            map.put(
                    blockName,
                    count
            );

            result.add(map);
        }

        return result;
    }

    // ============================================================
    // STATUS BY YEAR
    // ============================================================

    @Override
    @Transactional
    public List<PlantationStatusByYearDTO>
    plantationStatusByYear() {

        List<Object[]> rows =
                plantationRepository
                        .plantationStatusByYear();

        return PlantationMapper
                .toPlantationStatusByYearDTOList(
                        rows
                );
    }

    // ============================================================
    // CARBON BY SPECIES
    // ============================================================

    @Override
    @Transactional
    public List<SpeciesCarbonDTO>
    getCarbonSequesteredBySpeciesNative() {

        List<Object[]> rows =
                plantationRepository
                        .sumCarbonBySpeciesNative();

        return rows.stream()
                .map(row -> {

                    Integer speciesId =
                            row[0] == null
                                    ? null
                                    : ((Number) row[0])
                                    .intValue();

                    String speciesName =
                            row.length > 1 &&
                                    row[1] != null
                                    ? row[1].toString()
                                    : null;

                    double total =
                            row.length > 2 &&
                                    row[2] != null
                                    ? ((Number) row[2])
                                    .doubleValue()
                                    : 0d;

                    return new SpeciesCarbonDTO(
                            speciesId,
                            speciesName,
                            total
                    );
                })
                .collect(Collectors.toList());
    }

    // ============================================================
    // SURVIVAL RATE BY YEAR
    // ============================================================

    @Override
    public List<SurvivalRateDTO>
    calculateSurvivalRateByYear(
            List<PlantingMonitoring> plantingMonitorings
    ) {

        List<PlantationStatusByYearDTO>
                statusByYear =
                plantationStatusByYear();

        if (statusByYear == null ||
                statusByYear.isEmpty()) {

            return Collections.emptyList();
        }

        final Map<Integer, Integer>
                autoCountByYear;

        if (plantingMonitorings == null ||
                plantingMonitorings.isEmpty()) {

            autoCountByYear =
                    Collections.emptyMap();

        } else {

            autoCountByYear =
                    plantingMonitorings.stream()
                            .filter(Objects::nonNull)
                            .filter(pm ->
                                    pm.getAuto_generation() > 0
                            )
                            .filter(pm ->
                                    pm.getPlantation() != null &&
                                            pm.getPlantation()
                                                    .getDate_plantation() != null
                            )
                            .collect(
                                    Collectors.groupingBy(
                                            pm ->
                                                    pm.getPlantation()
                                                            .getDate_plantation()
                                                            .getYear(),
                                            Collectors.summingInt(
                                                    PlantingMonitoring::getAuto_generation
                                            )
                                    )
                            );
        }

        return statusByYear.stream()
                .map(status -> {

                    Integer year =
                            status.getYear();

                    int alive =
                            status.getAliveCount() == null
                                    ? 0
                                    : status.getAliveCount();

                    int dead =
                            status.getDeadCount() == null
                                    ? 0
                                    : status.getDeadCount();

                    int total =
                            status.getTotalCount() == null
                                    ? 0
                                    : status.getTotalCount();

                    double alivePct = 0.0;
                    double deadPct = 0.0;
                    double autoPct = 0.0;

                    if (total > 0) {

                        alivePct =
                                ((double) alive / total)
                                        * 100.0;

                        deadPct =
                                ((double) dead / total)
                                        * 100.0;

                        int autoCount =
                                autoCountByYear
                                        .getOrDefault(
                                                year,
                                                0
                                        );

                        autoPct =
                                ((double) autoCount / total)
                                        * 100.0;
                    }

                    alivePct =
                            Math.round(alivePct * 100.0)
                                    / 100.0;

                    deadPct =
                            Math.round(deadPct * 100.0)
                                    / 100.0;

                    autoPct =
                            Math.round(autoPct * 100.0)
                                    / 100.0;

                    return new SurvivalRateDTO(
                            year,
                            alivePct,
                            deadPct,
                            autoPct
                    );
                })
                .sorted(
                        Comparator.comparing(
                                SurvivalRateDTO::getYear
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<SurvivalRateDTO>
    calculateSurvivalRateByYearFromPlantingMonitorings(
            List<PlantingMonitoring> plantingMonitorings
    ) {

        if (plantingMonitorings == null ||
                plantingMonitorings.isEmpty()) {

            return Collections.emptyList();
        }

        return calculateSurvivalRateByYear(
                plantingMonitorings
        );
    }

    @Override
    @Transactional
    public List<SurvivalRateDTO>
    survivalRateByYear() {

        List<PlantingMonitoring> monitorings =
                plantingMonitoringRepository
                        .findAllByIsDeletedFalse();

        return calculateSurvivalRateByYearFromPlantingMonitorings(
                monitorings
        );
    }

    // ============================================================
    // GLOBAL SURVIVAL
    // ============================================================

    @Override
    @Transactional
    public SurvivalRateDTO survivalRateGlobal() {

        List<PlantationStatusByYearDTO>
                statusByYear =
                plantationStatusByYear();

        long totalAlive = 0;
        long totalDead = 0;
        long totalAll = 0;

        if (statusByYear != null) {

            for (PlantationStatusByYearDTO status :
                    statusByYear) {

                if (status == null) {
                    continue;
                }

                totalAlive +=
                        status.getAliveCount() == null
                                ? 0
                                : status.getAliveCount();

                totalDead +=
                        status.getDeadCount() == null
                                ? 0
                                : status.getDeadCount();

                totalAll +=
                        status.getTotalCount() == null
                                ? 0
                                : status.getTotalCount();
            }
        }

        int totalAutoGeneration = 0;

        try {

            List<PlantingMonitoring> monitorings =
                    plantingMonitoringRepository
                            .findAllByIsDeletedFalse();

            if (monitorings != null) {

                totalAutoGeneration =
                        monitorings.stream()
                                .filter(Objects::nonNull)
                                .mapToInt(
                                        PlantingMonitoring
                                                ::getAuto_generation
                                )
                                .sum();
            }

        } catch (Exception ignored) {
            totalAutoGeneration = 0;
        }

        double alivePct = 0;
        double deadPct = 0;
        double autoPct = 0;

        if (totalAll > 0) {

            alivePct =
                    ((double) totalAlive / totalAll)
                            * 100;

            deadPct =
                    ((double) totalDead / totalAll)
                            * 100;

            autoPct =
                    ((double) totalAutoGeneration / totalAll)
                            * 100;
        }

        alivePct =
                Math.round(alivePct * 100) / 100.0;

        deadPct =
                Math.round(deadPct * 100) / 100.0;

        autoPct =
                Math.round(autoPct * 100) / 100.0;

        return new SurvivalRateDTO(
                null,
                alivePct,
                deadPct,
                autoPct
        );
    }

    // ============================================================
    // SURVIVAL BY BLOCK / SUBPLOT / SPECIES
    // ============================================================

    @Override
    @Transactional
    public List<PlantationBlockSurvivalRateDTO>
    getSurvivalRateBySpeciesBySubPlotAndBlock() {

        List<Plantation> plantations =
                plantationRepository
                        .findAllByIsDeletedFalse();

        if (plantations == null ||
                plantations.isEmpty()) {

            return Collections.emptyList();
        }

        Map<Integer, BlockStats> blockMap =
                new LinkedHashMap<>();

        for (Plantation plantation :
                plantations) {

            if (plantation == null ||
                    plantation.getSpecies() == null ||
                    plantation.getSubPlot() == null) {

                continue;
            }

            SubPlot subPlot =
                    plantation.getSubPlot();

            PlantationBlock block =
                    subPlot.getPlantationBlock();

            if (block == null) {
                continue;
            }

            if (block.getId() <= 0 ||
                    subPlot.getId() <= 0 ||
                    plantation.getSpecies().getId() <= 0) {

                continue;
            }

            boolean alive =
                    plantation.isStatus();

            BlockStats blockStats =
                    blockMap.computeIfAbsent(
                            block.getId(),
                            id ->
                                    new BlockStats(
                                            block.getId(),
                                            block.getName()
                                    )
                    );

            blockStats.addPlantation(
                    subPlot.getId(),
                    subPlot.getName(),
                    plantation.getSpecies().getId(),
                    plantation.getSpecies().getMg_name(),
                    alive
            );
        }

        return blockMap.values()
                .stream()
                .map(BlockStats::toDTO)
                .collect(Collectors.toList());
    }
}