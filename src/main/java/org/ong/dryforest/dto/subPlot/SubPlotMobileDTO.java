package org.ong.dryforest.dto.subPlot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubPlotMobileDTO {

    @JsonProperty("id_sub_plot")
    private int idSubPlot;

    private String name;

    @JsonProperty("plantation_block_id")
    private int plantationBlockId;
}