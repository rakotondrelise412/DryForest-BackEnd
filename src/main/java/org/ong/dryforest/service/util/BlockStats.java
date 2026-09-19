package org.ong.dryforest.service.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.plantationBlock.PlantationBlockSurvivalRateDTO;
import org.ong.dryforest.dto.subPlot.SubPlotSurvivalRateDTO;

public class BlockStats {
    private final Integer blockId;
    private final String blockName;
    private final Map<Integer, SubPlotStats> subPlotMap = new LinkedHashMap<>();

    public BlockStats(Integer blockId, String blockName) {
        this.blockId = blockId;
        this.blockName = blockName;
    }

    public void addPlantation(Integer subPlotId, String subPlotName,
                       Integer speciesId, String speciesName,
                       boolean alive) {
        SubPlotStats subPlotStats = subPlotMap.computeIfAbsent(
            subPlotId,
            id -> new SubPlotStats(subPlotId, subPlotName)
        );
        subPlotStats.addPlantation(speciesId, speciesName, alive);
    }

    public PlantationBlockSurvivalRateDTO toDTO() {
        List<SubPlotSurvivalRateDTO> subPlots = subPlotMap.values()
            .stream()
            .map(SubPlotStats::toDTO)
            .collect(Collectors.toList());

        return new PlantationBlockSurvivalRateDTO(blockId, blockName, subPlots);
    }
}
