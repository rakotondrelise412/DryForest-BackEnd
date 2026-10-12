package org.ong.dryforest.dto.species;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpeciesCarbonDTO {
    private Integer speciesId;
    private String speciesName;
    private double totalCarbon;
}
