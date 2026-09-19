package org.ong.dryforest.service.plantingMonitoring;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.PlantingMonitoringDTO;
import org.ong.dryforest.dto.species.SpeciesStatDTO;
import org.ong.dryforest.entity.PlantingMonitoring;

public interface PlantingMonitoringService {
    public PlantingMonitoring create(PlantingMonitoringDTO plantingMonitoringDTO);

    PlantingMonitoring createPlantingMonitoring(PlantingMonitoring plantingMonitoring);

    PlantingMonitoring findById(int id);

    PlantingMonitoring findByUuid(UUID uuid);

    List<PlantingMonitoring> findAll();

    PlantingMonitoring updatePlantingMonitoring(PlantingMonitoring plantingMonitoring);

    void deletePlantingMonitoring(PlantingMonitoring plantingMonitoring);

    boolean existsByUuid(UUID uuid);

    PlantingMonitoring mapToEntity(Map<String, Object> plantingMonitoringMapping);

    List<SpeciesStatDTO> statisticSpecies();

    List<SpeciesStatDTO> statisticSpeciesAverageBySpeciesAndReforestationDate();

    List<SpeciesStatDTO> statisticSpeciesAverageByReforestationDateRange(LocalDate startDate, LocalDate endDate);
}
