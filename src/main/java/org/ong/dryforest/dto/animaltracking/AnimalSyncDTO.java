package org.ong.dryforest.dto.animaltracking;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalSyncDTO {

    private int id_animal;
    private String name;
    private int id_category_animal;
    private boolean isDeleted;
}
