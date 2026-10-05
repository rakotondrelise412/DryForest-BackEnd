package org.ong.dryforest.dto.plantationBlock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ong.dryforest.dto.subPlot.SubPlotDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantationBlockDTO {

    @JsonProperty("id_plantation_block")
    private int idPlantationBlock;

    private UUID uuid;

    private String name;

    private double width;

    private double length;

    @JsonProperty("nb_sub_plot")
    private int nbSubPlot;

    private Map<String, Object> geom;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("is_synced")
    private boolean synced;

    @JsonProperty("is_deleted")
    private boolean deleted;

    @JsonProperty("id_zone")
    private int idZone;

    private List<SubPlotDTO> subPlots;
}