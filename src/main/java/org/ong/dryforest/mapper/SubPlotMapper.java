package org.ong.dryforest.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Point;

import org.ong.dryforest.dto.subPlot.SubPlotDTO;
import org.ong.dryforest.dto.subPlot.SubPlotFilterWebDTO;
import org.ong.dryforest.dto.subPlot.SubPlotMobileDTO;
import org.ong.dryforest.entity.SubPlot;

public class SubPlotMapper {
    public static SubPlotMobileDTO toSubPlotMobileDTO(SubPlot subPlot){
        SubPlotMobileDTO subPlotMobileDTO = new SubPlotMobileDTO();

        subPlotMobileDTO.setId_sub_plot(subPlot.getId());
        subPlotMobileDTO.setName(subPlot.getName());
        subPlotMobileDTO.setPlantation_block_id(subPlot.getPlantation_block().getId());
        
        return subPlotMobileDTO;
    }

    public static SubPlotFilterWebDTO toWebFilterDTO(SubPlot subPlot){
        SubPlotFilterWebDTO subPlotFilterWebDTO = new SubPlotFilterWebDTO();

        subPlotFilterWebDTO.setId_sub_plot(subPlot.getId());
        subPlotFilterWebDTO.setName(subPlot.getName());

        return subPlotFilterWebDTO;
    }

    public static SubPlotDTO toSubPlotDTO(SubPlot subPlot){
        SubPlotDTO subPlotDTO = new SubPlotDTO();

        subPlotDTO.setId_sub_plot(subPlot.getId());
        subPlotDTO.setUuid(subPlot.getUuid());
        subPlotDTO.setName(subPlot.getName());
        subPlotDTO.setWidth(subPlot.getWidth());
        subPlotDTO.setHeight(subPlot.getLength()); // adapte si ton entity a une vraie propriété height

        if (subPlot.getLocation() != null) {
            Point p = subPlot.getLocation();

            Map<String, Object> location = new HashMap<>();
            location.put("type", "Point");

            // GeoJSON: [longitude, latitude]
            location.put("coordinates", List.of(p.getX(), p.getY()));

            subPlotDTO.setLocation(location);
        }

        subPlotDTO.setCreated_at(subPlot.getCreatedAt());
        subPlotDTO.setUpdated_at(subPlot.getUpdatedAt());
        subPlotDTO.setIs_synced(subPlot.is_synced());
        subPlotDTO.setIs_deleted(subPlot.isDeleted());

        if (subPlot.getPlantation_block() != null) {
            subPlotDTO.setPlantation_block_id(subPlot.getPlantation_block().getId());
        }

        return subPlotDTO;
    }
}
