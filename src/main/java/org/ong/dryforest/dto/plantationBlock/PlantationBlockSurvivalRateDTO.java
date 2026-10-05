package org.ong.dryforest.dto.plantationBlock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ong.dryforest.dto.subPlot.SubPlotSurvivalRateDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantationBlockSurvivalRateDTO {

    private Integer blockId;

    private String blockName;

    private List<SubPlotSurvivalRateDTO> subPlots;
}