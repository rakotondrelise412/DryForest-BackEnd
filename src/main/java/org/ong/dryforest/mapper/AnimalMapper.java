package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.animaltracking.AnimalDTO;
import org.ong.dryforest.dto.animaltracking.AnimalSyncDTO;
import org.ong.dryforest.entity.Animal;

import java.util.List;

public class AnimalMapper {

    private AnimalMapper() {
    }

    // =========================
    // Animal -> AnimalDTO
    // =========================
    public static AnimalDTO toAnimalDTO(Animal animal) {

        AnimalDTO dto = new AnimalDTO();

        dto.setId_animal(animal.getId());
        dto.setName(animal.getName());

        if (animal.getCategory_animal() != null) {
            dto.setId_category_animal(
                    animal.getCategory_animal().getId()
            );
        }

        return dto;
    }

    // =========================
    // List<Animal> -> List<AnimalDTO>
    // =========================
    public static List<AnimalDTO> toDTOList(
            List<Animal> animals
    ) {

        return animals.stream()
                .map(AnimalMapper::toAnimalDTO)
                .toList();
    }

    // =========================
    // Animal -> AnimalSyncDTO
    // =========================
    public static AnimalSyncDTO toAnimalSyncDTO(
            Animal animal
    ) {

        AnimalSyncDTO dto = new AnimalSyncDTO();

        dto.setId_animal(animal.getId());
        dto.setName(animal.getName());

        if (animal.getCategory_animal() != null) {
            dto.setId_category_animal(
                    animal.getCategory_animal().getId()
            );
        }

        dto.setDeleted(animal.isDeleted());

        return dto;
    }

    // =========================
    // List<Animal> -> List<AnimalSyncDTO>
    // =========================
    public static List<AnimalSyncDTO> toAnimalSyncDTOList(
            List<Animal> animals
    ) {

        return animals.stream()
                .map(AnimalMapper::toAnimalSyncDTO)
                .toList();
    }
}