package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.PlantingMonitoringDTO;
import org.ong.dryforest.entity.PlantingMonitoring;

public class PlantingMonitoringMapper {

    public static PlantingMonitoringDTO toMonitoringDTO(
            PlantingMonitoring plantingMonitoring
    ) {

        PlantingMonitoringDTO dto =
                new PlantingMonitoringDTO();

        dto.setId_planting_monitoring(
                plantingMonitoring.getId()
        );

        dto.setUuid(
                plantingMonitoring.getUuid()
        );

        dto.setDate_planting_monitoring(
                plantingMonitoring.getDate_planting_monitoring()
        );

        dto.setDiameter(
                plantingMonitoring.getDiameter()
        );

        dto.setHeight(
                plantingMonitoring.getHeight()
        );

        dto.setImage(
                plantingMonitoring.getImage()
        );

        dto.setAuto_generation(
                plantingMonitoring.getAuto_generation()
        );

        dto.setCreatedAt(
                plantingMonitoring.getCreatedAt()
        );

        dto.setUpdatedAt(
                plantingMonitoring.getUpdatedAt()
        );

        dto.setIsSynced(
                plantingMonitoring.is_synced()
        );

        dto.setDeletedAt(
                plantingMonitoring.isDeleted()
        );

        if (plantingMonitoring.getPlantation() != null) {

            dto.setId_plantation(
                    plantingMonitoring
                            .getPlantation()
                            .getId()
            );
        }

        return dto;
    }
}