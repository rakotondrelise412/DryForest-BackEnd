package org.ong.dryforest.service.plantationBlock;

import org.ong.dryforest.dto.plantationBlock.PlantationBlockDTO;
import org.ong.dryforest.entity.PlantationBlock;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlantationBlockService {

    List<PlantationBlock> findAll();

    Page<PlantationBlock> findAll(Pageable pageable);

    PlantationBlock findById(int id);

    PlantationBlock findByUuid(UUID uuid);

    PlantationBlock createPlantationBlock(
            PlantationBlockDTO dto
    );

    PlantationBlock createPlantationBlock(
            PlantationBlock plantationBlock
    );

    PlantationBlock updatePlantationBlock(
            int id,
            PlantationBlockDTO dto
    );

    void updatePlantationBlock(
            PlantationBlock plantationBlock
    );

    void deletePlantationBlock(int id);


    boolean existsByUuid(UUID uuid);

    PlantationBlock mapToEntity(
            Map<String, Object> data
    );
}