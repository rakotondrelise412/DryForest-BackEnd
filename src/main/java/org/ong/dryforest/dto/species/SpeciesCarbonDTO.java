package org.ong.dryforest.dto.species;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpeciesCarbonDTO {
    private Integer speciesId;
    private String speciesName;
    private double totalCarbon;
}
