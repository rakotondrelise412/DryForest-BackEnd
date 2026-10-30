package org.ong.dryforest.dto.plantation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantationBySiteStatisticsDTO {

    private int id_site;

    private String site_name;

    private PlantationStatisticsDTO global_statistics;

    private List<PlantationYearlyStatisticsDTO> yearly_statistics;
}