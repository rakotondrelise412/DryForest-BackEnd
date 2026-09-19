package org.ong.dryforest.dto.species;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesStatDTO {
    private int id_species;
    private String scientific_name;
    private String mg_name;
    private String fr_name;
    private String en_name;
    private double diameter;
    private double height;
    private double carbon_sequestered;
    private LocalDate date_reforestation;
    private int id_plantation;
}
