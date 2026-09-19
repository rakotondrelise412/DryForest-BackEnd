package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.plantationBlock.PlantationBlockDTO;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockMobileDTO;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockWebDTO;
import org.ong.dryforest.entity.PlantationBlock;
import org.ong.dryforest.service.util.PolygonToMap;

public class PlantationBlockMapper {

    public static PlantationBlockMobileDTO toPlantationBlockMobileDTO(PlantationBlock plantationBlock){
        
        PlantationBlockMobileDTO plantationBlockMobileDTO = new PlantationBlockMobileDTO();

        plantationBlockMobileDTO.setId_plantation_block(plantationBlock.getId());
        plantationBlockMobileDTO.setName(plantationBlock.getName());
        plantationBlockMobileDTO.setId_zone(plantationBlock.getZone().getId());

        return plantationBlockMobileDTO;
    }

    public static PlantationBlockWebDTO toPlantationBlockWebDTO(PlantationBlock plantationBlock){
        PlantationBlockWebDTO plantationBlockWebDTO = new PlantationBlockWebDTO();
        plantationBlockWebDTO.setId_plantation_block(plantationBlock.getId());
        plantationBlockWebDTO.setName(plantationBlock.getName());

        return plantationBlockWebDTO;
    }

    public static PlantationBlockDTO toPlantationBlockDTO(PlantationBlock plantationBlock){
        PlantationBlockDTO plantationBlockDTO = new PlantationBlockDTO();

        plantationBlockDTO.setId_plantation_block(plantationBlock.getId());
        plantationBlockDTO.setUuid(plantationBlock.getUuid());
        plantationBlockDTO.setName(plantationBlock.getName());
        plantationBlockDTO.setWidth(plantationBlock.getWidth());
        plantationBlockDTO.setLength(plantationBlock.getLength());
        plantationBlockDTO.setNb_sub_plot(plantationBlock.getNb_sub_plot());

        if (plantationBlock.getGeom() != null) {
            plantationBlockDTO.setGeom(PolygonToMap.polygonToMap(plantationBlock.getGeom()));
        }

        plantationBlockDTO.setCreated_at(plantationBlock.getCreatedAt());
        plantationBlockDTO.setUpdated_at(plantationBlock.getUpdatedAt());
        plantationBlockDTO.set_synced(plantationBlock.is_synced());
        plantationBlockDTO.set_deleted(plantationBlock.isDeleted());

        if (plantationBlock.getZone() != null) {
            plantationBlockDTO.setId_zone(plantationBlock.getZone().getId());
        }

        return plantationBlockDTO;
    }
}
