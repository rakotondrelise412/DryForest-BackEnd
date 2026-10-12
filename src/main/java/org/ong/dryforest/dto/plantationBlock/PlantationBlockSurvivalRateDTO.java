package org.ong.dryforest.dto.plantationBlock;

import lombok.*;
import org.ong.dryforest.dto.subPlot.SubPlotSurvivalRateDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantationBlockSurvivalRateDTO {

    private Integer blockId;

    private String blockName;

    private List<SubPlotSurvivalRateDTO> subPlots;
}