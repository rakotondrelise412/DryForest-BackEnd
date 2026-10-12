package org.ong.dryforest.dto.species;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesWebDTO {
    private int id;
    private String mg_name;
    private String fr_name;
    private String en_name;
    private String scientific_name;
}
