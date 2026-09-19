package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.PlantingMonitoringDTO;
import org.ong.dryforest.entity.PlantingMonitoring;

public class PlantingMonitoringMapper {
    public static PlantingMonitoringDTO toMonitoringDTO (PlantingMonitoring plantingMonitoring){
        PlantingMonitoringDTO plantingMonitoringDTO = new PlantingMonitoringDTO();
        
        plantingMonitoringDTO.setId_planting_monitoring(plantingMonitoring.getId());
        plantingMonitoringDTO.setUuid(plantingMonitoring.getUuid());
        plantingMonitoringDTO.setDate_planting_monitoring(plantingMonitoring.getDate_planting_monitoring());
        plantingMonitoringDTO.setDiameter(plantingMonitoring.getDiameter());
        plantingMonitoringDTO.setHeight(plantingMonitoring.getHeight());
        plantingMonitoringDTO.setImage(plantingMonitoring.getImage());
        plantingMonitoringDTO.setAuto_generation(plantingMonitoring.getAuto_generation());
        plantingMonitoringDTO.setCreatedAt(plantingMonitoring.getCreatedAt());
        plantingMonitoringDTO.setUpdatedAt(plantingMonitoring.getUpdatedAt());
        plantingMonitoringDTO.setIsSynced(plantingMonitoring.is_synced());
        plantingMonitoringDTO.setDeletedAt(plantingMonitoring.isDeleted());
        plantingMonitoringDTO.setId_plantation(plantingMonitoring.getPlantation().getId());

        return plantingMonitoringDTO;
    }
}
