package org.ong.dryforest.dto.species;

import lombok.*;

@Getter
@Setter
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
