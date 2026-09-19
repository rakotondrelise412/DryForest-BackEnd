package org.ong.dryforest.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.animaltracking.CategoryAnimalDTO;
import org.ong.dryforest.entity.CategoryAnimal;


public class CategoryAnimalMapper {
    public static CategoryAnimalDTO toCategoryAnimalDTO(CategoryAnimal categoryAnimal){
        CategoryAnimalDTO categoryAnimalDTO = new CategoryAnimalDTO();

        categoryAnimalDTO.setId(categoryAnimal.getId());
        categoryAnimalDTO.setName(categoryAnimal.getName());

        return categoryAnimalDTO;
    }

    public static List<CategoryAnimalDTO> toDTOList(List<CategoryAnimal> categoryAnimals){
        List<CategoryAnimalDTO> typeObservationPatrol = new ArrayList<>();

        typeObservationPatrol = categoryAnimals.stream().map(CategoryAnimalMapper::toCategoryAnimalDTO).collect(Collectors.toList());

        return typeObservationPatrol;
    }
}
