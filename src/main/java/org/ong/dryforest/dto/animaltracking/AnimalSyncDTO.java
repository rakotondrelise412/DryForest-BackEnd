package org.ong.dryforest.dto.animaltracking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalSyncDTO {

    private int id_animal;
    private String name;
    private int id_category_animal;
    private boolean isDeleted;
}
