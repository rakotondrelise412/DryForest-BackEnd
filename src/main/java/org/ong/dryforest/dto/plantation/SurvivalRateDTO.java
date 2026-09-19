package org.ong.dryforest.dto.plantation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurvivalRateDTO {
    private Integer year;
    private Double alivePercentage;
    private Double deadPercentage;
    private Double autoGenerationPercentage;
}
