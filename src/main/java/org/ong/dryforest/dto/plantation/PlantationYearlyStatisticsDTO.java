package org.ong.dryforest.dto.plantation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantationYearlyStatisticsDTO {

    private int year;

    private long total_plantations;

    private long total_alive;

    private long total_dead;

    private long total_unknown;

    private double survival_rate;

    private double mortality_rate;
}