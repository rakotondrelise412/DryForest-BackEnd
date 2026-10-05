package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.plantationBlock.PlantationBlockDTO;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockMobileDTO;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockWebDTO;
import org.ong.dryforest.entity.PlantationBlock;
import org.ong.dryforest.service.util.PolygonToMap;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PlantationBlockMapper {

    private PlantationBlockMapper() {
    }

    public static PlantationBlockMobileDTO toPlantationBlockMobileDTO(
            PlantationBlock plantationBlock) {

        PlantationBlockMobileDTO dto =
                new PlantationBlockMobileDTO();

        dto.setIdPlantationBlock(
                plantationBlock.getId()
        );

        dto.setName(
                plantationBlock.getName()
        );

        if (plantationBlock.getZone() != null) {
            dto.setIdZone(
                    plantationBlock.getZone().getId()
            );
        }

        return dto;
    }

    public static PlantationBlockWebDTO toPlantationBlockWebDTO(
            PlantationBlock plantationBlock) {

        PlantationBlockWebDTO dto =
                new PlantationBlockWebDTO();

        dto.setIdPlantationBlock(
                plantationBlock.getId()
        );

        dto.setName(
                plantationBlock.getName()
        );

        return dto;
    }

    public static PlantationBlockDTO toPlantationBlockDTO(
            PlantationBlock plantationBlock) {

        PlantationBlockDTO dto =
                new PlantationBlockDTO();

        dto.setIdPlantationBlock(
                plantationBlock.getId()
        );

        dto.setUuid(
                plantationBlock.getUuid()
        );

        dto.setName(
                plantationBlock.getName()
        );

        if (plantationBlock.getWidth() != null) {
            dto.setWidth(
                    plantationBlock.getWidth()
            );
        }

        if (plantationBlock.getLength() != null) {
            dto.setLength(
                    plantationBlock.getLength()
            );
        }

        dto.setNbSubPlot(
                plantationBlock.getNbSubPlot()
        );

        if (plantationBlock.getZone() != null) {
            dto.setIdZone(
                    plantationBlock.getZone().getId()
            );
        }

        if (plantationBlock.getGeom() != null) {
            dto.setGeom(
                    PolygonToMap.polygonToMap(
                            plantationBlock.getGeom()
                    )
            );
        }

        dto.setCreatedAt(
                plantationBlock.getCreatedAt()
        );

        dto.setUpdatedAt(
                plantationBlock.getUpdatedAt()
        );

        dto.setSynced(
                plantationBlock.isSynced()
        );

        dto.setDeleted(
                plantationBlock.isDeleted()
        );

        if (plantationBlock.getSubPlots() != null) {
            dto.setSubPlots(
                    plantationBlock.getSubPlots()
                            .stream()
                            .map(SubPlotMapper::toSubPlotDTO)
                            .collect(Collectors.toList())
            );
        } else {
            dto.setSubPlots(
                    new ArrayList<>()
            );
        }

        return dto;
    }
}