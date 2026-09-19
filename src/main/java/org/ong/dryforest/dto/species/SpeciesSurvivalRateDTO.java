package org.ong.dryforest.dto.species;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesSurvivalRateDTO {
    private Integer speciesId;
    private String speciesName;
    private long aliveCount;
    private long deadCount;
    private long totalCount;
    private double aliveRate;
    private double deadRate;
}
