
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlantingMonitoringServiceImpl
        implements PlantingMonitoringService {

    private final PlantationService plantationService;
    private final PlantBiomassService plantBiomassService;
    private final PlantingMonitoringRepository plantingMonitoringRepository;

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private void calculateBiomass(PlantingMonitoring monitoring) {

        if (monitoring.getPlantation() == null) {
            throw new IllegalArgumentException(
                    "La plantation associée est obligatoire."
            );
        }

        Plantation plantation = monitoring.getPlantation();

        if (plantation.getSpecies() == null) {
            throw new IllegalArgumentException(
                    "L'espèce de la plantation est obligatoire."
            );
        }

        double diameter = monitoring.getDiameter();
        double height = monitoring.getHeight();
        double density = plantation.getSpecies().getDensity();

        if (diameter <= 0 || height <= 0 || density <= 0) {
            throw new IllegalArgumentException(
                    "Le diamètre, la hauteur et la densité doivent être positifs."
            );
        }

        monitoring.setDensity(density);

        double carbon =
                plantBiomassService.calculateCarbonForPlantation(
                        diameter,
                        height,
                        density
                );

        monitoring.setCarbon_sequestered(carbon);
    }


    @Override
    public void createPlantingMonitoring(
            PlantingMonitoring plantingMonitoring
    ) {

        try {
            calculateBiomass(plantingMonitoring);

            if (plantingMonitoring.getUuid() == null) {
                plantingMonitoring.setUuid(UUID.randomUUID());
            }

            LocalDateTime now = LocalDateTime.now();

            if (plantingMonitoring.getCreatedAt() == null) {
                plantingMonitoring.setCreatedAt(now);
            }

            plantingMonitoring.setUpdatedAt(now);
            plantingMonitoring.set_synced(true);

            plantingMonitoringRepository.save(plantingMonitoring);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Suivi plantation déjà existant.",
                    e
            );
        }
    }

    @Override
    public PlantingMonitoring create(PlantingMonitoringDTO dto) {

        try {
            if (dto.getId_plantation() <= 0) {
                throw new IllegalArgumentException(
                        "L'identifiant de la plantation est obligatoire."
                );
            }

            Plantation plantation =
                    plantationService.findById(dto.getId_plantation());

            PlantingMonitoring monitoring = new PlantingMonitoring();

            monitoring.setUuid(
                    dto.getUuid() != null
                            ? dto.getUuid()
                            : UUID.randomUUID()
            );

            LocalDateTime now = LocalDateTime.now();

            monitoring.setCreatedAt(
                    dto.getCreated_at() != null
                            ? dto.getCreated_at()
                            : now
            );

            monitoring.setUpdatedAt(
                    dto.getUpdated_at() != null
                            ? dto.getUpdated_at()
                            : now
            );

            monitoring.setDate_planting_monitoring(
                    dto.getDate_planting_monitoring()
            );

            monitoring.setDiameter(dto.getDiameter());
            monitoring.setHeight(dto.getHeight());
            monitoring.setImage(dto.getImage());
            monitoring.setAuto_generation(dto.isAuto_generation());
            monitoring.setPlantation(plantation);

            calculateBiomass(monitoring);

            monitoring.set_synced(true);

            return plantingMonitoringRepository.save(monitoring);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Suivi plantation déjà existant.",
                    e
            );
        }
    }


    @Override
    public PlantingMonitoring updatePlantingMonitoring(
            PlantingMonitoring plantingMonitoring
    ) {

        if (plantingMonitoring.getId() <= 0) {
            throw new IllegalArgumentException(
                    "L'identifiant du suivi est obligatoire."
            );
        }

        PlantingMonitoring existing =
                findById(plantingMonitoring.getId());

        if (plantingMonitoring.getUuid() != null) {
            existing.setUuid(plantingMonitoring.getUuid());
        }

        existing.setDate_planting_monitoring(
                plantingMonitoring.getDate_planting_monitoring()
        );

        existing.setDiameter(plantingMonitoring.getDiameter());
        existing.setHeight(plantingMonitoring.getHeight());
        existing.setImage(plantingMonitoring.getImage());

        existing.setAuto_generation(
                plantingMonitoring.getAuto_generation()
        );

        if (plantingMonitoring.getPlantation() != null) {
            existing.setPlantation(
                    plantationService.findById(
                            plantingMonitoring.getPlantation().getId()
                    )
            );
        }

        calculateBiomass(existing);

        existing.set_synced(true);
        existing.setUpdatedAt(LocalDateTime.now());

        return plantingMonitoringRepository.save(existing);
    }


    @Override
    public void deletePlantingMonitoring(
            PlantingMonitoring plantingMonitoring
    ) {

        PlantingMonitoring existing =
                findById(plantingMonitoring.getId());

        try {
            plantingMonitoringRepository.delete(existing);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "Impossible de supprimer ce suivi plantation.",
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
            Map<String, Object> mapping
    ) {

        PlantingMonitoring monitoring = new PlantingMonitoring();

        Object uuid = mapping.get("uuid");
        if (uuid != null) {
            monitoring.setUuid(UUID.fromString(uuid.toString()));
        }

        Object diameter = mapping.get("diameter");
        if (diameter instanceof Number) {
            monitoring.setDiameter(((Number) diameter).doubleValue());
        }

        Object height = mapping.get("height");
        if (height instanceof Number) {
            monitoring.setHeight(((Number) height).doubleValue());
        }

        monitoring.setImage((String) mapping.get("image"));

        Object date = mapping.get("date_planting_monitoring");
        if (date != null) {
            monitoring.setDate_planting_monitoring(
                    LocalDate.parse(date.toString())
            );
        }

        Object autoGeneration = mapping.get("auto_generation");
        if (autoGeneration instanceof Boolean) {
            monitoring.setAuto_generation((Boolean) autoGeneration);
        } else if (autoGeneration instanceof Number) {
            monitoring.setAuto_generation(
                    ((Number) autoGeneration).intValue() != 0
            );
        } else if (autoGeneration != null) {
            monitoring.setAuto_generation(
                    Boolean.parseBoolean(autoGeneration.toString())
            );
        }

        Object createdAt = mapping.get("created_at");
        if (createdAt != null) {
            monitoring.setCreatedAt(
                    LocalDateTime.parse(createdAt.toString())
            );
        }

        Object updatedAt = mapping.get("updated_at");
        if (updatedAt != null) {
            monitoring.setUpdatedAt(
                    LocalDateTime.parse(updatedAt.toString())
            );
        }

        Object synced = mapping.get("is_synced");
        if (synced instanceof Boolean) {
            monitoring.set_synced((Boolean) synced);
        }

        Object idPlantation = mapping.get("id_plantation");

        if (idPlantation instanceof Number) {
            monitoring.setPlantation(
                    plantationService.findById(
                            ((Number) idPlantation).intValue()
                    )
            );
        }

        return monitoring;
    }


    @Override
    @Transactional(readOnly = true)
    public List<PlantingMonitoring> findAll() {
        return plantingMonitoringRepository.findAllByIsDeletedFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public PlantingMonitoring findById(int id) {
        return plantingMonitoringRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Suivi plantation introuvable avec id : " + id
                        )
                );
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<SpeciesStatDTO> statisticSpecies() {

        List<PlantingMonitoring> monitorings = findAll();
        List<SpeciesStatDTO> result = new ArrayList<>();

        if (monitorings.isEmpty()) {
            return result;
        }

        for (PlantingMonitoring monitoring : monitorings) {

            if (monitoring == null || monitoring.getPlantation() == null) {
                continue;
            }

            Plantation plantation = monitoring.getPlantation();

            if (plantation.getSpecies() == null) {
                continue;
            }

            SpeciesStatDTO dto = new SpeciesStatDTO();

            dto.setId_species(plantation.getSpecies().getId());
            dto.setScientific_name(
                    plantation.getSpecies().getScientific_name()
            );
            dto.setMg_name(plantation.getSpecies().getMg_name());
            dto.setEn_name(plantation.getSpecies().getEn_name());
            dto.setFr_name(plantation.getSpecies().getFr_name());

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

            dto.setId_plantation(plantation.getId());

            result.add(dto);
        }

        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public List<SpeciesStatDTO>
    statisticSpeciesAverageBySpeciesAndReforestationDate() {

        List<SpeciesStatDTO> stats = statisticSpecies();

        if (stats.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, List<SpeciesStatDTO>> grouped =
                stats.stream().collect(
                        Collectors.groupingBy(dto ->
                                dto.getId_species() + "_" +
                                        (dto.getDate_reforestation() != null
                                                ? dto.getDate_reforestation()
                                                : "null")
                        )
                );

        return calculateAverages(grouped);
    }


    @Override
    @Transactional(readOnly = true)
    public List<SpeciesStatDTO>
    statisticSpeciesAverageByReforestationDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException(
                    "Les dates de début et de fin sont obligatoires."
            );
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "La date de début doit précéder la date de fin."
            );
        }

        List<SpeciesStatDTO> stats = statisticSpecies();

        Map<String, List<SpeciesStatDTO>> grouped =
                stats.stream()
                        .filter(dto -> dto.getDate_reforestation() != null)
                        .filter(dto -> {
                            LocalDate date = dto.getDate_reforestation();
                            return !date.isBefore(startDate)
                                    && !date.isAfter(endDate);
                        })
                        .collect(
                                Collectors.groupingBy(dto ->
                                        dto.getId_species() + "_" +
                                                dto.getDate_reforestation()
                                )
                        );

        return calculateAverages(grouped);
    }


    private List<SpeciesStatDTO> calculateAverages(
            Map<String, List<SpeciesStatDTO>> grouped
    ) {

        List<SpeciesStatDTO> result = new ArrayList<>();

        for (List<SpeciesStatDTO> group : grouped.values()) {

            if (group.isEmpty()) {
                continue;
            }

            SpeciesStatDTO first = group.get(0);

            double avgDiameter = group.stream()
                    .mapToDouble(SpeciesStatDTO::getDiameter)
                    .average()
                    .orElse(0.0);

            double avgHeight = group.stream()
                    .mapToDouble(SpeciesStatDTO::getHeight)
                    .average()
                    .orElse(0.0);

            double avgCarbon = group.stream()
                    .mapToDouble(SpeciesStatDTO::getCarbon_sequestered)
                    .average()
                    .orElse(0.0);

            SpeciesStatDTO dto = new SpeciesStatDTO();

            dto.setId_species(first.getId_species());
            dto.setScientific_name(first.getScientific_name());
            dto.setMg_name(first.getMg_name());
            dto.setEn_name(first.getEn_name());
            dto.setFr_name(first.getFr_name());
            dto.setDate_reforestation(first.getDate_reforestation());
            dto.setId_plantation(first.getId_plantation());

            dto.setDiameter(round2(avgDiameter));
            dto.setHeight(round2(avgHeight));
            dto.setCarbon_sequestered(round2(avgCarbon));

            result.add(dto);
        }

        return result;
    }
}
