package org.ong.dryforest.dto.plantationBlock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.subPlot.SubPlotDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantationBlockDTO {
    private int id_plantation_block;
    private UUID uuid;
    private String name;
    private double width;
    private double length;
    private int nb_sub_plot;
    private Map<String, Object> geom;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean is_synced;
    private boolean is_deleted;
    private int id_zone;

    private List<SubPlotDTO> subPlots;

    
    public List<SubPlotDTO> getSubPlots() {
        return subPlots;
    }
    public void setSubPlots(List<SubPlotDTO> subPlots) {
        this.subPlots = subPlots;
    }
}
