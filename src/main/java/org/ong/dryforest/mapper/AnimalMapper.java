package org.ong.dryforest.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.animaltracking.AnimalDTO;
import org.ong.dryforest.entity.Animal;

public class AnimalMapper {
    
    public static AnimalDTO toAnimalDTO(Animal animal){
        AnimalDTO animalDTO = new AnimalDTO();

        animalDTO.setId_animal(animal.getId());
        animalDTO.setName(animal.getName());
        animalDTO.setId_category_animal(animal.getCategory_animal().getId());
        
        return animalDTO;
    }

    public static List<AnimalDTO> toDTOList(List<Animal> animals){
        List<AnimalDTO> animalDTO = new ArrayList<>();

        animalDTO = animals.stream().map(AnimalMapper::toAnimalDTO).collect(Collectors.toList());

        return animalDTO;
    }
}
