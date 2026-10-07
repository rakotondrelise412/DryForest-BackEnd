package org.ong.dryforest.dto.animaltracking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalObservedByZoneDTO {

    private Integer idZone;
    private String zoneName;
    private Integer idAnimal;
    private String animalName;
    private Long totalObserved;
}