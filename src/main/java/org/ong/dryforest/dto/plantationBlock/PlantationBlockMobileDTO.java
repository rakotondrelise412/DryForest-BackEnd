package org.ong.dryforest.dto.plantationBlock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantationBlockMobileDTO {

    private int idPlantationBlock;

    private String name;

    private int idZone;
}