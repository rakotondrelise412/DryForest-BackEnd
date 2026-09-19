package org.ong.dryforest.dto.plantation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantationStatusByYearDTO {
    private Integer year;
    private Integer aliveCount;
    private Integer deadCount;
    private Integer totalCount;
}
