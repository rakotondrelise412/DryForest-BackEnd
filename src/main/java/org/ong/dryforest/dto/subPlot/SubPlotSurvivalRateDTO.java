package org.ong.dryforest.dto.subPlot;

import lombok.*;
import org.ong.dryforest.dto.species.SpeciesSurvivalRateDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubPlotSurvivalRateDTO {

    private Integer subPlotId;

    private String subPlotName;

    private List<SpeciesSurvivalRateDTO> speciesStats;
}