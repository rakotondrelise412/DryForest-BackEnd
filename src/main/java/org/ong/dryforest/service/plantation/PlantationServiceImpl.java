package org.ong.dryforest.service.plantation;

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
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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
public class PlantationServiceImpl
        implements PlantationService {

    // ============================================================
    // CONSTANTES
    // ============================================================

    private static final double CARBON_RATIO = 0.47;

    private static final double AGB_COEFFICIENT = 0.0673;

    private static final double AGB_EXPONENT = 0.976;

    private static final String PLANT_NUMBER_PREFIX = "Plt_";

    // ============================================================
    // DEPENDANCES
    // ============================================================

    private final PlantationRepository plantationRepository;

    private final SpeciesService speciesService;

    private final ReforestationService reforestationService;

    private final SubPlotService subPlotService;

    private final PlantationBlockService plantationBlockService;

    private final PlantingMonitoringRepository
            plantingMonitoringRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Plantation> findAll() {
        return plantationRepository
                .findAllByIsDeletedFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public Plantation findById(int idPlantation) {

        return plantationRepository
                .findByIdAndIsDeletedFalse(idPlantation)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Plantation not found for id: "
                                        + idPlantation
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

    @Override
    @Transactional
    public Plantation create(
            PlantationDTO dto
    ) {

        try {

            Plantation plantation =
                    new Plantation();

            UUID uuid =
                    dto.getUuid() != null
                            ? dto.getUuid()
                            : UUID.randomUUID();

            plantation.setUuid(uuid);

            plantation.setPlant_number(
                    generatePlantNumber()
            );

            plantation.setCreatedAt(
                    dto.getCreatedAt() != null
                            ? dto.getCreatedAt()
                            : LocalDateTime.now()
            );

            plantation.setUpdatedAt(
                    dto.getUpdatedAt() != null
                            ? dto.getUpdatedAt()
                            : LocalDateTime.now()
            );

            plantation.set_synced(true);

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

            var species =
                    speciesService.findSpeciesEntityById(
                            dto.getId_species()
                    );

            plantation.setSpecies(species);

            plantation.setReforestation(
                    reforestationService.findById(
                            dto.getId_reforestation()
                    )
            );

            plantation.setSubPlot(
                    subPlotService.findById(
                            dto.getId_sub_plot()
                    )
            );

            double carbon =
                    calculateCarbonForPlantation(
                            dto.getDiameter(),
                            dto.getHeight(),
                            species.getDensity()
                    );

            plantation.setCarbon_sequestered(
                    carbon
            );

            plantation.setStatus(false);

            return plantationRepository.save(
                    plantation
            );

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Plantation already exists",
                    e
            );
        }
    }


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

            int nextNumber =
                    Integer.parseInt(number) + 1;

            return PLANT_NUMBER_PREFIX + nextNumber;

        } catch (NumberFormatException e) {

            return PLANT_NUMBER_PREFIX + "1";
        }
    }


    @Override
    @Transactional
    public Plantation update(
            int id,
            PlantationDTO dto
    ) {

        Plantation plantation =
                findById(id);

        if (dto.getUuid() != null) {
            plantation.setUuid(dto.getUuid());
        }

        if (dto.getPlant_number() != null &&
                !dto.getPlant_number().isBlank()) {

            plantation.setPlant_number(
                    dto.getPlant_number()
            );
        }

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

        if (dto.getId_species() > 0) {

            var species =
                    speciesService.findSpeciesEntityById(
                            dto.getId_species()
                    );

            plantation.setSpecies(species);

            double carbon =
                    calculateCarbonForPlantation(
                            dto.getDiameter(),
                            dto.getHeight(),
                            species.getDensity()
                    );

            plantation.setCarbon_sequestered(
                    carbon
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

        plantation.set_synced(true);

        plantation.setUpdatedAt(
                LocalDateTime.now()
        );

        return plantationRepository.save(
                plantation
        );
    }


    @Override
    @Transactional
    public void deleteById(int id) {

        Plantation plantation =
                findById(id);

        plantation.setDeleted(true);

        plantation.setUpdatedAt(
                LocalDateTime.now()
        );

        plantationRepository.save(
                plantation
        );
    }


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

        findById(
                plantation.getId()
        );

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
                findById(
                        plantation.getId()
                );

        try {

            plantationRepository.delete(
                    existing
            );

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "Impossible de supprimer cette plantation",
                    e
            );
        }
    }


    @Override
    public Plantation mapToEntity(
            Map<String, Object> plantationMapping
    ) {

        Plantation plantation =
                new Plantation();

        Object uuidValue =
                plantationMapping.get(
                        "uuid_plantation"
                );

        if (uuidValue != null) {
            plantation.setUuid(
                    UUID.fromString(
                            uuidValue.toString()
                    )
            );
        }

        plantation.setPlant_number(
                (String) plantationMapping.get(
                        "plant_number"
                )
        );

        Object dateValue =
                plantationMapping.get(
                        "date_plantation"
                );

        if (dateValue != null) {

            plantation.setDate_plantation(
                    java.time.LocalDate.parse(
                            dateValue.toString()
                    )
            );
        }

        Object diameterValue =
                plantationMapping.get("diameter");

        if (diameterValue instanceof Number number) {
            plantation.setDiameter(
                    number.doubleValue()
            );
        }

        Object heightValue =
                plantationMapping.get("height");

        if (heightValue instanceof Number number) {
            plantation.setHeight(
                    number.doubleValue()
            );
        }

        Object carbonValue =
                plantationMapping.get(
                        "carbon_sequestered"
                );

        if (carbonValue instanceof Number number) {
            plantation.setCarbon_sequestered(
                    number.doubleValue()
            );
        }

        plantation.setImage(
                (String) plantationMapping.get(
                        "image"
                )
        );

        Object statusValue =
                plantationMapping.get("status");

        if (statusValue instanceof Boolean booleanValue) {
            plantation.setStatus(
                    booleanValue
            );
        }

        return plantation;
    }

    // ============================================================
    // CALCUL BIOMASSE
    // ============================================================

    public static double calculateDryAGB(
            double diameterCm,
            double heightM,
            double woodDensity
    ) {

        if (diameterCm <= 0 ||
                heightM <= 0 ||
                woodDensity <= 0) {

            throw new IllegalArgumentException(
                    "Le diamètre, la hauteur et la densité " +
                            "doivent être positifs."
            );
        }

        return AGB_COEFFICIENT *
                Math.pow(
                        woodDensity
                                * diameterCm
                                * diameterCm
                                * heightM,
                        AGB_EXPONENT
                );
    }


    public static double calculateCarbon(
            double dryAGB
    ) {

        if (dryAGB < 0) {
            throw new IllegalArgumentException(
                    "La biomasse ne peut pas être négative."
            );
        }

        return CARBON_RATIO * dryAGB;
    }

    private static double calculateCarbonForPlantation(
            double diameter,
            double height,
            double density
    ) {

        double biomass =
                calculateDryAGB(
                        diameter,
                        height,
                        density
                );

        return calculateCarbon(biomass);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PlantationViewDTO>
    getAllPlantations() {

        return PlantationMapper
                .toPlantationViewDTOList(
                        plantationRepository
                                .findAllPlantationsView()
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<PlantationViewDTO>
    getPlantationsByCriteria(
            Integer idPlantationBlock,
            Integer idSubPlot,
            Integer idSpecies,
            Date datePlantation
    ) {

        return PlantationMapper
                .toPlantationViewDTOList(
                        plantationRepository
                                .searchPlantationsByCriteria(
                                        idPlantationBlock,
                                        idSubPlot,
                                        idSpecies,
                                        datePlantation
                                )
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<PlantationViewDTO>
    getPlantationsByIdPlantationBlock(
            int blockId
    ) {

        return PlantationMapper
                .toPlantationViewDTOList(
                        plantationRepository
                                .findPlantationsByBlockId(
                                        blockId
                                )
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<PlantationViewDTO>
    getPlantationsByBlockAndSubPlot(
            int blockId,
            int subPlotId
    ) {

        return PlantationMapper
                .toPlantationViewDTOList(
                        plantationRepository
                                .findPlantationsByBlockAndSubPlot(
                                        blockId,
                                        subPlotId
                                )
                );
    }


    @Override
    @Transactional(readOnly = true)
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

            List<PlantationBlock> blocks =
                    plantationBlockService.findAll();

            for (PlantationBlock block : blocks) {

                if (block == null) {
                    continue;
                }

                blockIdToName.put(
                        block.getId(),
                        block.getName()
                );
            }

        } catch (Exception ignored) {
        }

        List<Map<String, Integer>> result =
                new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry :
                countsByBlockId.entrySet()) {

            Integer blockId =
                    entry.getKey();

            Integer count =
                    entry.getValue();

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

                } catch (Exception ignored) {

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


    @Override
    @Transactional(readOnly = true)
    public List<PlantationStatusByYearDTO>
    plantationStatusByYear() {

        return PlantationMapper
                .toPlantationStatusByYearDTOList(
                        plantationRepository
                                .plantationStatusByYear()
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<SpeciesCarbonDTO>
    getCarbonSequesteredBySpeciesNative() {

        List<Object[]> rows =
                plantationRepository
                        .sumCarbonBySpeciesNative();

        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }

        return rows.stream()
                .filter(Objects::nonNull)
                .map(row -> {

                    Integer speciesId =
                            row.length > 0 &&
                                    row[0] != null
                                    ? ((Number) row[0])
                                    .intValue()
                                    : null;

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
                                    : 0.0;

                    total =
                            Math.round(
                                    total * 100.0
                            ) / 100.0;

                    return new SpeciesCarbonDTO(
                            speciesId,
                            speciesName,
                            total
                    );
                })
                .collect(Collectors.toList());
    }


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

        Map<Integer, Integer> autoCountByYear;

        if (plantingMonitorings == null ||
                plantingMonitorings.isEmpty()) {

            autoCountByYear =
                    Collections.emptyMap();

        } else {

            autoCountByYear =
                    plantingMonitorings.stream()
                            .filter(Objects::nonNull)
                            .filter(
                                    monitoring ->
                                            Boolean.TRUE.equals(
                                                    monitoring
                                                            .getAuto_generation()
                                            )
                            )
                            .filter(
                                    monitoring ->
                                            monitoring
                                                    .getPlantation()
                                                    != null
                                                    &&
                                                    monitoring
                                                            .getPlantation()
                                                            .getDate_plantation()
                                                            != null
                            )
                            .collect(
                                    Collectors.groupingBy(
                                            monitoring ->
                                                    monitoring
                                                            .getPlantation()
                                                            .getDate_plantation()
                                                            .getYear(),
                                            Collectors.summingInt(
                                                    monitoring ->
                                                            Boolean.TRUE.equals(
                                                                    monitoring
                                                                            .getAuto_generation()
                                                            )
                                                                    ? 1
                                                                    : 0
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
                            Math.round(
                                    alivePct * 100.0
                            ) / 100.0;

                    deadPct =
                            Math.round(
                                    deadPct * 100.0
                            ) / 100.0;

                    autoPct =
                            Math.round(
                                    autoPct * 100.0
                            ) / 100.0;

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
    @Transactional(readOnly = true)
    public List<SurvivalRateDTO>
    survivalRateByYear() {

        List<PlantingMonitoring> monitorings =
                plantingMonitoringRepository
                        .findAllByIsDeletedFalse();

        return calculateSurvivalRateByYearFromPlantingMonitorings(
                monitorings
        );
    }


    @Override
    @Transactional(readOnly = true)
    public SurvivalRateDTO
    survivalRateGlobal() {

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
                                        monitoring ->
                                                Boolean.TRUE.equals(
                                                        monitoring
                                                                .getAuto_generation()
                                                )
                                                        ? 1
                                                        : 0
                                )
                                .sum();
            }

        } catch (Exception ignored) {
        }

        // ----------------------------------------------------
        // POURCENTAGES
        // ----------------------------------------------------

        double alivePct = 0.0;
        double deadPct = 0.0;
        double autoPct = 0.0;

        if (totalAll > 0) {

            alivePct =
                    ((double) totalAlive / totalAll)
                            * 100.0;

            deadPct =
                    ((double) totalDead / totalAll)
                            * 100.0;

            autoPct =
                    ((double) totalAutoGeneration / totalAll)
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
                null,
                alivePct,
                deadPct,
                autoPct
        );
    }


    @Override
    @Transactional(readOnly = true)
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