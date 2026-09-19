package org.ong.dryforest.service.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.species.SpeciesSurvivalRateDTO;
import org.ong.dryforest.dto.subPlot.SubPlotSurvivalRateDTO;

public class SubPlotStats {
    private final Integer subPlotId;
    private final String subPlotName;
    private final Map<Integer, SpeciesStats> speciesMap = new LinkedHashMap<>();

    SubPlotStats(Integer subPlotId, String subPlotName) {
        this.subPlotId = subPlotId;
        this.subPlotName = subPlotName;
    }

    public void addPlantation(Integer speciesId, String speciesName, boolean alive) {
        SpeciesStats stats = speciesMap.computeIfAbsent(
            speciesId,
            id -> new SpeciesStats(speciesId, speciesName)
        );
        stats.addPlantation(alive);
    }

    public SubPlotSurvivalRateDTO toDTO() {
        List<SpeciesSurvivalRateDTO> speciesStats = speciesMap.values()
            .stream()
            .map(SpeciesStats::toDTO)
            .collect(Collectors.toList());

        return new SubPlotSurvivalRateDTO(subPlotId, subPlotName, speciesStats);
    }
}
