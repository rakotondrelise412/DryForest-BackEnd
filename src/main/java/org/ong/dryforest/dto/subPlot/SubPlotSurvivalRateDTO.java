package org.ong.dryforest.dto.subPlot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import org.ong.dryforest.dto.species.SpeciesSurvivalRateDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubPlotSurvivalRateDTO {
    private Integer subPlotId;
    private String subPlotName;
    private List<SpeciesSurvivalRateDTO> speciesStats;
}