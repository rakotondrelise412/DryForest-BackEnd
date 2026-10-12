package org.ong.dryforest.dto.species;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesDTO {
    private int id;
    private String mg_name;
    private String fr_name;
    private String en_name;
    private String scientific_name;
    private double density;
    private int id_species_type;

}
