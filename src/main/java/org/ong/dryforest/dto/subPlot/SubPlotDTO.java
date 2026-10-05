package org.ong.dryforest.dto.subPlot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubPlotDTO {

    @JsonProperty("id_sub_plot")
    private int idSubPlot;

    private UUID uuid;

    private String name;

    private double width;

    private double height;

    private Map<String, Object> location;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("is_synced")
    private boolean synced;

    @JsonProperty("is_deleted")
    private boolean deleted;

    @JsonProperty("plantation_block_id")
    private int plantationBlockId;
}