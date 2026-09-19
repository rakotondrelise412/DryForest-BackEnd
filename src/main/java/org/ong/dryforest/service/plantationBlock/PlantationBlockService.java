package org.ong.dryforest.service.plantationBlock;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.entity.PlantationBlock;

public interface PlantationBlockService {
    List<PlantationBlock> findAll();
    // PlantationBlock createPlantationBlock(PlantationBlockDTO plantationBlockDTO) throws Exception;
    PlantationBlock createPlantationBlock(PlantationBlock plantationBlock);
    PlantationBlock findById(int id_plantationBlock);
    PlantationBlock findByUuid(UUID uuid);
    PlantationBlock updatePlantationBlock(PlantationBlock plantationBlock);
    void deletedPlantationBlock(PlantationBlock plantationBlock);
    boolean existsByUuid(UUID uuid);
    PlantationBlock mapToEntity(Map<String, Object> plantationBlockMapping);

}
