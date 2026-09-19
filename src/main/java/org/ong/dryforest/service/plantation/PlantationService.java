package org.ong.dryforest.service.plantation;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.plantation.PlantationDTO;
import org.ong.dryforest.dto.plantation.PlantationStatusByYearDTO;
import org.ong.dryforest.dto.plantation.PlantationViewDTO;
import org.ong.dryforest.dto.plantation.SurvivalRateDTO;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockSurvivalRateDTO;
import org.ong.dryforest.dto.species.SpeciesCarbonDTO;
import org.ong.dryforest.entity.Plantation;
import org.ong.dryforest.entity.PlantingMonitoring;

public interface PlantationService {
    Plantation create(PlantationDTO plantationDTO);

    List<Plantation> findAll();

    Plantation findById(int id_plantation);

    Plantation findByUuid(UUID uuid);

    Plantation updatePlantation(Plantation plantation);

    void deletePlantation(Plantation plantation);

    Plantation createPlantation(Plantation plantation);

    boolean existsByUuid(UUID uuid);

    Plantation mapToEntity(Map<String, Object> plantationMapping);

    List<PlantationViewDTO> getAllPlantations();

    List<PlantationViewDTO> getPlantationsByIdPlantationBlock(int blockId);

    List<PlantationViewDTO> getPlantationsByBlockAndSubPlot(int blockId, int subPlotId);

    List<Map<String, Integer>> getTotalPlantationByBlock();

    List<PlantationViewDTO> getPlantationsByCriteria(Integer idPlantationBlock, Integer idSubPlot, Integer idSpecies,
            Date datePlantation);

    List<PlantationStatusByYearDTO> plantationStatusByYear();

    List<SpeciesCarbonDTO> getCarbonSequesteredBySpeciesNative();

    List<SurvivalRateDTO> calculateSurvivalRateByYear(List<PlantingMonitoring> plantingMonitorings);

    List<SurvivalRateDTO> calculateSurvivalRateByYearFromPlantingMonitorings(
            List<PlantingMonitoring> plantingMonitorings);

    List<SurvivalRateDTO> survivalRateByYear();

    SurvivalRateDTO survivalRateGlobal();

    List<PlantationBlockSurvivalRateDTO> getSurvivalRateBySpeciesBySubPlotAndBlock();
}
