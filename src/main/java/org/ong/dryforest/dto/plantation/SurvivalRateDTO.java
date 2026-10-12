package org.ong.dryforest.dto.plantation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurvivalRateDTO {
    private Integer year;
    private Double alivePercentage;
    private Double deadPercentage;
    private Double autoGenerationPercentage;
}
