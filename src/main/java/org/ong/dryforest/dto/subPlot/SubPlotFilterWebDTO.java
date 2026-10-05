package org.ong.dryforest.dto.subPlot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubPlotFilterWebDTO {

    @JsonProperty("id_sub_plot")
    private int idSubPlot;

    private String name;
}