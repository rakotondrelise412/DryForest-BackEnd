package org.ong.dryforest.dto.animaltracking;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalRequestDTO {

    private String name;
    private int id_category_animal;
}