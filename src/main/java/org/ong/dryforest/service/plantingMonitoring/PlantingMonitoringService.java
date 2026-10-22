package org.ong.dryforest.service.plantingMonitoring;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.PlantingMonitoringDTO;
import org.ong.dryforest.dto.species.SpeciesStatDTO;
import org.ong.dryforest.entity.PlantingMonitoring;
import org.ong.dryforest.service.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlantingMonitoringService {
    PlantingMonitoring create(PlantingMonitoringDTO plantingMonitoringDTO);

    void createPlantingMonitoring(PlantingMonitoring plantingMonitoring);

    PlantingMonitoring findById(int id);

    PlantingMonitoring findByUuid(UUID uuid);

    List<PlantingMonitoring> findAll();

    Page<PlantingMonitoring> findAll(Pageable pageable);

    PlantingMonitoring updatePlantingMonitoring(PlantingMonitoring plantingMonitoring);

    void deletePlantingMonitoring(PlantingMonitoring plantingMonitoring);

    boolean existsByUuid(UUID uuid);

    PlantingMonitoring mapToEntity(Map<String, Object> plantingMonitoringMapping);

    List<SpeciesStatDTO> statisticSpecies();

    PageResponse<SpeciesStatDTO>
    statisticSpeciesAverageBySpeciesAndReforestationDate(Pageable pageable);

    PageResponse<SpeciesStatDTO>
    statisticSpeciesAverageByReforestationDateRange(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );


}
