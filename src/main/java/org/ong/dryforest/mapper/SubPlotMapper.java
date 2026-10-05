package org.ong.dryforest.mapper;

import org.locationtech.jts.geom.Point;
import org.ong.dryforest.dto.subPlot.SubPlotDTO;
import org.ong.dryforest.dto.subPlot.SubPlotFilterWebDTO;
import org.ong.dryforest.dto.subPlot.SubPlotMobileDTO;
import org.ong.dryforest.entity.SubPlot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubPlotMapper {

    private SubPlotMapper() {
    }

    public static SubPlotMobileDTO toSubPlotMobileDTO(
            SubPlot subPlot) {

        SubPlotMobileDTO dto =
                new SubPlotMobileDTO();

        dto.setIdSubPlot(
                subPlot.getId()
        );

        dto.setName(
                subPlot.getName()
        );

        if (subPlot.getPlantationBlock() != null) {

            dto.setPlantationBlockId(
                    subPlot
                            .getPlantationBlock()
                            .getId()
            );
        }

        return dto;
    }

    public static SubPlotFilterWebDTO toWebFilterDTO(
            SubPlot subPlot) {

        SubPlotFilterWebDTO dto =
                new SubPlotFilterWebDTO();

        dto.setIdSubPlot(
                subPlot.getId()
        );

        dto.setName(
                subPlot.getName()
        );

        return dto;
    }

    public static SubPlotDTO toSubPlotDTO(
            SubPlot subPlot) {

        SubPlotDTO dto =
                new SubPlotDTO();

        dto.setIdSubPlot(
                subPlot.getId()
        );

        dto.setUuid(
                subPlot.getUuid()
        );

        dto.setName(
                subPlot.getName()
        );

        if (subPlot.getWidth() != null) {
            dto.setWidth(
                    subPlot.getWidth()
            );
        }

        if (subPlot.getLength() != null) {
            dto.setHeight(
                    subPlot.getLength()
            );
        }

        if (subPlot.getLocation() != null) {

            Point point =
                    subPlot.getLocation();

            Map<String, Object> location =
                    new HashMap<>();

            location.put(
                    "type",
                    "Point"
            );

            location.put(
                    "coordinates",
                    List.of(
                            point.getX(),
                            point.getY()
                    )
            );

            dto.setLocation(location);
        }

        dto.setCreatedAt(
                subPlot.getCreatedAt()
        );

        dto.setUpdatedAt(
                subPlot.getUpdatedAt()
        );

        dto.setSynced(
                subPlot.isSynced()
        );

        dto.setDeleted(
                subPlot.isDeleted()
        );

        if (subPlot.getPlantationBlock() != null) {

            dto.setPlantationBlockId(
                    subPlot
                            .getPlantationBlock()
                            .getId()
            );
        }

        return dto;
    }
}