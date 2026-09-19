package org.ong.dryforest.service.util;

import org.ong.dryforest.dto.species.SpeciesSurvivalRateDTO;

public class SpeciesStats {
    private final Integer speciesId;
    private final String speciesName;
    private long aliveCount = 0;
    private long deadCount = 0;

    SpeciesStats(Integer speciesId, String speciesName) {
        this.speciesId = speciesId;
        this.speciesName = speciesName;
    }

    public void addPlantation(boolean alive) {
        if (alive) {
            aliveCount++;
        } else {
            deadCount++;
        }
    }

    public SpeciesSurvivalRateDTO toDTO() {
        long total = aliveCount + deadCount;

        double aliveRate = 0.0;
        double deadRate = 0.0;

        if (total > 0) {
            aliveRate = ((double) aliveCount / (double) total) * 100.0;
            deadRate = ((double) deadCount / (double) total) * 100.0;
        }

        aliveRate = Math.round(aliveRate * 100.0) / 100.0;
        deadRate = Math.round(deadRate * 100.0) / 100.0;

        return new SpeciesSurvivalRateDTO(
            speciesId,
            speciesName,
            aliveCount,
            deadCount,
            total,
            aliveRate,
            deadRate
        );
    }
}
