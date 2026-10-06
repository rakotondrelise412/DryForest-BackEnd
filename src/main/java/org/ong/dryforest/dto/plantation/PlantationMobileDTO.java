package org.ong.dryforest.dto.plantation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantationMobileDTO {

    private int id_plantation;

    private String plant_number;

    private int id_reforestation;

    private int id_species;

    private int id_sub_plot;
}