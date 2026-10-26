package org.ong.dryforest.service.plantation;

import org.ong.dryforest.dto.plantation.PlantationDTO;
import org.ong.dryforest.dto.plantation.PlantationStatusByYearDTO;
import org.ong.dryforest.dto.plantation.PlantationViewDTO;
import org.ong.dryforest.dto.plantation.SurvivalRateDTO;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockSurvivalRateDTO;
import org.ong.dryforest.dto.species.SpeciesCarbonDTO;
import org.ong.dryforest.entity.Plantation;
import org.ong.dryforest.entity.PlantingMonitoring;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlantationService {

    Plantation create(PlantationDTO plantationDTO);

    Plantation update(
            int id,
            PlantationDTO plantationDTO
    );

    void deleteById(int id);

    List<Plantation> findAll();

    Page<Plantation> findAll(Pageable pageable);

    Plantation findById(int idPlantation);

    Plantation findByUuid(UUID uuid);

    void updatePlantation(
            Plantation plantation
    );

    void createPlantation(
            Plantation plantation
    );

    boolean existsByUuid(UUID uuid);

    Plantation mapToEntity(
            Map<String, Object> plantationMapping
    );

    List<PlantationViewDTO> getAllPlantations();


    Page<PlantationBlockSurvivalRateDTO>
    getSurvivalRateBySpeciesBySubPlotAndBlock(Pageable pageable);

    List<Map<String, Integer>>
    getTotalPlantationByBlock();

    List<PlantationStatusByYearDTO>
    plantationStatusByYear();

    List<SpeciesCarbonDTO>
    getCarbonSequesteredBySpeciesNative();

    List<SurvivalRateDTO>
    calculateSurvivalRateByYear(
            List<PlantingMonitoring> plantingMonitorings
    );

    List<SurvivalRateDTO>
    calculateSurvivalRateByYearFromPlantingMonitorings(
            List<PlantingMonitoring> plantingMonitorings
    );

    List<SurvivalRateDTO>
    survivalRateByYear();

    SurvivalRateDTO
    survivalRateGlobal();

    Page<PlantationViewDTO> getAllPlantations(Pageable pageable);

    Page<PlantationViewDTO> getPlantationsByCriteria(
            Integer idPlantationBlock,
            Integer idSubPlot,
            Integer idSpecies,
            Date datePlantation,
            Pageable pageable
    );

    Page<PlantationViewDTO> getPlantationsByIdPlantationBlock(
            int blockId,
            Pageable pageable
    );

    Page<PlantationViewDTO> getPlantationsByBlockAndSubPlot(
            int blockId,
            int subPlotId,
            Pageable pageable
    );
}