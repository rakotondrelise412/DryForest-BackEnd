package org.ong.dryforest.dto.plantation;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlantationStatusByYearDTO {
    private Integer year;
    private Integer aliveCount;
    private Integer deadCount;
    private Integer totalCount;
}
