package org.ong.dryforest.service.plantingMonitoring;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.PlantingMonitoringDTO;
import org.ong.dryforest.dto.species.SpeciesStatDTO;
import org.ong.dryforest.entity.Plantation;
import org.ong.dryforest.entity.PlantingMonitoring;
import org.ong.dryforest.repository.PlantingMonitoringRepository;
import org.ong.dryforest.service.biomass.PlantBiomassService;
import org.ong.dryforest.service.plantation.PlantationService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantingMonitoringServiceImpl
        implements PlantingMonitoringService {

    private final PlantationService plantationService;

    private final PlantBiomassService plantBiomassService;

    private final PlantingMonitoringRepository
            plantingMonitoringRepository;

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public PlantingMonitoring createPlantingMonitoring(
            PlantingMonitoring plantingMonitoring
    ) {

        try {

            plantingMonitoring.set_synced(true);

            return plantingMonitoringRepository.save(
                    plantingMonitoring
            );

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Suivi plantation déjà existant",
                    e
            );
        }
    }


    @Override
    public PlantingMonitoring updatePlantingMonitoring(
            PlantingMonitoring plantingMonitoring
    ) {

        findById(plantingMonitoring.getId());

        /*
         * Recalcul de la densité et du carbone
         * à partir de la plantation et de son espèce.
         */
        Plantation plantation =
                plantingMonitoring.getPlantation();

        if (plantation != null &&
                plantation.getSpecies() != null) {

            double density =
                    plantation.getSpecies().getDensity();

            plantingMonitoring.setDensity(density);

            double carbon =
                    plantBiomassService.calculateCarbonForPlantation(
                            plantingMonitoring.getDiameter(),
                            plantingMonitoring.getHeight(),
                            density
                    );

            plantingMonitoring.setCarbon_sequestered(carbon);
        }

        plantingMonitoring.set_synced(true);

        plantingMonitoring.setUpdatedAt(
                LocalDateTime.now()
        );

        return plantingMonitoringRepository.save(
                plantingMonitoring
        );
    }


    @Override
    public void deletePlantingMonitoring(
            PlantingMonitoring plantingMonitoring
    ) {

        try {

            PlantingMonitoring existing =
                    findById(plantingMonitoring.getId());

            plantingMonitoringRepository.delete(existing);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "Impossible de supprimer ce suivi plantation",
                    e
            );
        }
    }


    @Override
    public boolean existsByUuid(UUID uuid) {

        return plantingMonitoringRepository
                .existsByUuidAndIsDeletedFalse(uuid);
    }


    @Override
    public PlantingMonitoring mapToEntity(
            Map<String, Object> plantingMonitoringMapping
    ) {

        PlantingMonitoring plantingMonitoring =
                new PlantingMonitoring();

        Object uuid =
                plantingMonitoringMapping.get("uuid");

        if (uuid != null) {

            plantingMonitoring.setUuid(
                    UUID.fromString(uuid.toString())
            );
        }

        Object diameter =
                plantingMonitoringMapping.get("diameter");

        if (diameter != null) {

            plantingMonitoring.setDiameter(
                    ((Number) diameter).doubleValue()
            );
        }

        Object height =
                plantingMonitoringMapping.get("height");

        if (height != null) {

            plantingMonitoring.setHeight(
                    ((Number) height).doubleValue()
            );
        }

        Object carbon =
                plantingMonitoringMapping.get(
                        "carbon_sequestered"
                );

        if (carbon != null) {

            plantingMonitoring.setCarbon_sequestered(
                    ((Number) carbon).doubleValue()
            );
        }

        Object density =
                plantingMonitoringMapping.get("density");

        if (density != null) {

            plantingMonitoring.setDensity(
                    ((Number) density).doubleValue()
            );
        }

        plantingMonitoring.setImage(
                (String) plantingMonitoringMapping.get(
                        "image"
                )
        );

        Object date =
                plantingMonitoringMapping.get(
                        "date_planting_monitoring"
                );

        if (date != null) {

            plantingMonitoring.setDate_planting_monitoring(
                    LocalDate.parse(date.toString())
            );
        }

        Object autoGeneration =
                plantingMonitoringMapping.get(
                        "auto_generation"
                );

        if (autoGeneration instanceof Boolean) {

            plantingMonitoring.setAuto_generation(
                    (Boolean) autoGeneration
            );

        } else if (autoGeneration instanceof Number) {

            plantingMonitoring.setAuto_generation(
                    ((Number) autoGeneration).intValue() != 0
            );

        } else if (autoGeneration != null) {

            plantingMonitoring.setAuto_generation(
                    Boolean.parseBoolean(
                            autoGeneration.toString()
                    )
            );
        }

        Object createdAt =
                plantingMonitoringMapping.get("created_at");

        if (createdAt != null) {

            plantingMonitoring.setCreatedAt(
                    LocalDateTime.parse(
                            createdAt.toString()
                    )
            );
        }

        Object updatedAt =
                plantingMonitoringMapping.get("updated_at");

        if (updatedAt != null) {

            plantingMonitoring.setUpdatedAt(
                    LocalDateTime.parse(
                            updatedAt.toString()
                    )
            );
        }

        Object synced =
                plantingMonitoringMapping.get("is_synced");

        if (synced != null) {

            plantingMonitoring.set_synced(
                    (Boolean) synced
            );
        }

        Object idPlantationObject =
                plantingMonitoringMapping.get(
                        "id_plantation"
                );

        if (idPlantationObject != null) {

            int idPlantation =
                    ((Number) idPlantationObject).intValue();

            Plantation plantation =
                    plantationService.findById(
                            idPlantation
                    );

            plantingMonitoring.setPlantation(
                    plantation
            );
        }

        return plantingMonitoring;
    }

    @Override
    public List<PlantingMonitoring> findAll() {

        return plantingMonitoringRepository
                .findAllByIsDeletedFalse();
    }

    @Override
    public PlantingMonitoring findById(int id) {

        return plantingMonitoringRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Suivi plantation introuvable avec id : "
                                        + id
                        )
                );
    }


    @Override
    public PlantingMonitoring findByUuid(UUID uuid) {

        return plantingMonitoringRepository
                .findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Suivi plantation introuvable avec uuid : "
                                        + uuid
                        )
                );
    }


    @Override
    public PlantingMonitoring create(
            PlantingMonitoringDTO dto
    ) {

        try {

            PlantingMonitoring plantingMonitoring =
                    new PlantingMonitoring();

            UUID uuid = dto.getUuid();

            if (uuid == null) {
                uuid = UUID.randomUUID();
            }

            plantingMonitoring.setUuid(uuid);


            LocalDateTime createdAt =
                    dto.getCreatedAt() != null
                            ? dto.getCreatedAt()
                            : LocalDateTime.now();

            LocalDateTime updatedAt =
                    dto.getUpdatedAt() != null
                            ? dto.getUpdatedAt()
                            : LocalDateTime.now();

            plantingMonitoring.setCreatedAt(createdAt);

            plantingMonitoring.setUpdatedAt(updatedAt);

            plantingMonitoring.set_synced(true);


            plantingMonitoring.setDate_planting_monitoring(
                    dto.getDate_planting_monitoring()
            );

            plantingMonitoring.setDiameter(
                    dto.getDiameter()
            );

            plantingMonitoring.setHeight(
                    dto.getHeight()
            );

            plantingMonitoring.setImage(
                    dto.getImage()
            );

            plantingMonitoring.setAuto_generation(
                    dto.isAuto_generation()
            );


            Plantation plantation =
                    plantationService.findById(
                            dto.getId_plantation()
                    );

            plantingMonitoring.setPlantation(
                    plantation
            );

            double density =
                    plantation.getSpecies().getDensity();

            plantingMonitoring.setDensity(density);

            double carbon =
                    plantBiomassService.calculateCarbonForPlantation(
                            dto.getDiameter(),
                            dto.getHeight(),
                            density
                    );

            plantingMonitoring.setCarbon_sequestered(
                    carbon
            );


            return plantingMonitoringRepository.save(plantingMonitoring);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Suivi plantation déjà existant",
                    e
            );
        }
    }


    @Override
    public List<SpeciesStatDTO> statisticSpecies() {

        List<Plantation> plantations =
                plantationService.findAll();

        List<PlantingMonitoring> monitorings =
                this.findAll();

        List<SpeciesStatDTO> result =
                new ArrayList<>();

        if (plantations == null ||
                monitorings == null ||
                plantations.isEmpty() ||
                monitorings.isEmpty()) {

            return result;
        }


        for (PlantingMonitoring monitoring :
                monitorings) {

            if (monitoring == null ||
                    monitoring.getPlantation() == null) {

                continue;
            }

            Plantation plantation =
                    monitoring.getPlantation();

            if (plantation.getSpecies() == null) {
                continue;
            }


            SpeciesStatDTO dto =
                    new SpeciesStatDTO();

            dto.setId_species(
                    plantation.getSpecies().getId()
            );

            dto.setScientific_name(
                    plantation.getSpecies()
                            .getScientific_name()
            );

            dto.setMg_name(
                    plantation.getSpecies()
                            .getMg_name()
            );

            dto.setEn_name(
                    plantation.getSpecies()
                            .getEn_name()
            );

            dto.setFr_name(
                    plantation.getSpecies()
                            .getFr_name()
            );

            dto.setDiameter(
                    round2(
                            monitoring.getDiameter()
                                    - plantation.getDiameter()
                    )
            );

            dto.setHeight(
                    round2(
                            monitoring.getHeight()
                                    - plantation.getHeight()
                    )
            );

            dto.setCarbon_sequestered(
                    round2(
                            monitoring.getCarbon_sequestered()
                                    - plantation.getCarbon_sequestered()
                    )
            );

            if (plantation.getReforestation() != null) {

                dto.setDate_reforestation(
                        plantation.getReforestation()
                                .getDate_reforestation()
                );
            }

            System.out.println(
                    "MONITORING ID = " + monitoring.getId()
                            + " | PLANTATION ID = " + plantation.getId()
                            + " | PLANTATION CLASS = " + plantation.getClass()
            );

            dto.setId_plantation(
                    plantation.getId()
            );

            result.add(dto);
        }

        return result;
    }


    // ============================================================
    // MOYENNE PAR ESPECE + DATE DE REFORESTATION
    // ============================================================

    @Override
    public List<SpeciesStatDTO>
    statisticSpeciesAverageBySpeciesAndReforestationDate() {

        List<SpeciesStatDTO> stats =
                statisticSpecies();

        if (stats == null || stats.isEmpty()) {
            return new ArrayList<>();
        }


        Map<String, List<SpeciesStatDTO>> grouped =
                stats.stream()
                        .collect(
                                Collectors.groupingBy(
                                        dto ->
                                                dto.getId_species()
                                                        + "_"
                                                        + (
                                                        dto.getDate_reforestation()
                                                                != null
                                                                ? dto.getDate_reforestation()
                                                                : "null"
                                                )
                                )
                        );


        List<SpeciesStatDTO> result =
                new ArrayList<>();


        for (List<SpeciesStatDTO> group :
                grouped.values()) {

            if (group.isEmpty()) {
                continue;
            }

            SpeciesStatDTO first =
                    group.get(0);


            double avgDiameter =
                    group.stream()
                            .mapToDouble(
                                    SpeciesStatDTO::getDiameter
                            )
                            .average()
                            .orElse(0.0);

            double avgHeight =
                    group.stream()
                            .mapToDouble(
                                    SpeciesStatDTO::getHeight
                            )
                            .average()
                            .orElse(0.0);

            double avgCarbon =
                    group.stream()
                            .mapToDouble(
                                    SpeciesStatDTO::getCarbon_sequestered
                            )
                            .average()
                            .orElse(0.0);


            SpeciesStatDTO dto =
                    new SpeciesStatDTO();


            dto.setId_species(
                    first.getId_species()
            );

            dto.setScientific_name(
                    first.getScientific_name()
            );

            dto.setMg_name(
                    first.getMg_name()
            );

            dto.setEn_name(
                    first.getEn_name()
            );

            dto.setFr_name(
                    first.getFr_name()
            );

            dto.setDate_reforestation(
                    first.getDate_reforestation()
            );

            dto.setId_plantation(
                    first.getId_plantation()
            );
            dto.setDiameter(round2(avgDiameter));
            dto.setHeight(round2(avgHeight));
            dto.setCarbon_sequestered(round2(avgCarbon));
            result.add(dto);
        }

        return result;
    }


    // ============================================================
    // MOYENNE PAR PERIODE DE REFORESTATION
    // ============================================================

    @Override
    public List<SpeciesStatDTO>
    statisticSpeciesAverageByReforestationDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {

        List<SpeciesStatDTO> stats =
                statisticSpecies();

        if (stats == null ||
                stats.isEmpty() ||
                startDate == null ||
                endDate == null) {

            return new ArrayList<>();
        }


        Map<String, List<SpeciesStatDTO>> grouped =
                stats.stream()

                        .filter(
                                dto ->
                                        dto.getDate_reforestation()
                                                != null
                        )

                        .filter(dto -> {

                            LocalDate date =
                                    dto.getDate_reforestation();

                            return (
                                    date.isEqual(startDate)
                                            ||
                                            date.isAfter(startDate)
                            )
                                    &&
                                    (
                                            date.isEqual(endDate)
                                                    ||
                                                    date.isBefore(endDate)
                                    );
                        })

                        .collect(
                                Collectors.groupingBy(
                                        dto ->
                                                dto.getId_species()
                                                        + "_"
                                                        + dto.getDate_reforestation()
                                )
                        );


        List<SpeciesStatDTO> result =
                new ArrayList<>();


        for (List<SpeciesStatDTO> group :
                grouped.values()) {

            if (group.isEmpty()) {
                continue;
            }

            SpeciesStatDTO first =
                    group.get(0);


            double avgDiameter =
                    group.stream()
                            .mapToDouble(
                                    SpeciesStatDTO::getDiameter
                            )
                            .average()
                            .orElse(0.0);

            double avgHeight =
                    group.stream()
                            .mapToDouble(
                                    SpeciesStatDTO::getHeight
                            )
                            .average()
                            .orElse(0.0);

            double avgCarbon =
                    group.stream()
                            .mapToDouble(
                                    SpeciesStatDTO::getCarbon_sequestered
                            )
                            .average()
                            .orElse(0.0);


            SpeciesStatDTO dto =
                    new SpeciesStatDTO();


            dto.setId_species(
                    first.getId_species()
            );

            dto.setScientific_name(
                    first.getScientific_name()
            );

            dto.setMg_name(
                    first.getMg_name()
            );

            dto.setEn_name(
                    first.getEn_name()
            );

            dto.setFr_name(
                    first.getFr_name()
            );

            dto.setDate_reforestation(
                    first.getDate_reforestation()
            );

            dto.setId_plantation(
                    first.getId_plantation()
            );

            dto.setDiameter(round2(avgDiameter));
            dto.setHeight(round2(avgHeight));
            dto.setCarbon_sequestered(round2(avgCarbon));

            result.add(dto);
        }

        return result;
    }
}