package org.ong.dryforest.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.animaltracking.AnimalDTO;
import org.ong.dryforest.dto.animaltracking.AnimalRequestDTO;
import org.ong.dryforest.dto.animaltracking.AnimalSyncDTO;
import org.ong.dryforest.entity.Animal;
import org.ong.dryforest.entity.CategoryAnimal;
import org.ong.dryforest.mapper.AnimalMapper;
import org.ong.dryforest.service.animalTracking.AnimalService;
import org.ong.dryforest.service.animalTracking.CategoryAnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final CategoryAnimalService categoryAnimalService;


    @GetMapping
    public ResponseEntity<List<AnimalDTO>> findAll() {

        List<Animal> animals = animalService.findAll();

        return ResponseEntity.ok(
                AnimalMapper.toDTOList(animals)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> findById(
            @PathVariable int id
    ) {

        Animal animal = animalService.findById(id);

        return ResponseEntity.ok(
                AnimalMapper.toAnimalDTO(animal)
        );
    }

    @PostMapping
    public ResponseEntity<AnimalDTO> create(
            @RequestBody AnimalRequestDTO dto
    ) {

        CategoryAnimal categoryAnimal =
                categoryAnimalService.findById(
                        dto.getId_category_animal()
                );

        Animal animal = new Animal();

        animal.setName(dto.getName());
        animal.setCategory_animal(categoryAnimal);

        Animal created = animalService.create(animal);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        AnimalMapper.toAnimalDTO(created)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalDTO> update(
            @PathVariable int id,
            @RequestBody AnimalRequestDTO dto
    ) {

        CategoryAnimal categoryAnimal =
                categoryAnimalService.findById(
                        dto.getId_category_animal()
                );

        Animal animal = animalService.findById(id);

        animal.setName(dto.getName());
        animal.setCategory_animal(categoryAnimal);

        Animal updated =
                animalService.updateAnimal(animal);

        return ResponseEntity.ok(
                AnimalMapper.toAnimalDTO(updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        Animal animal = animalService.findById(id);

        animalService.deleteAnimal(animal);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sync")
    public ResponseEntity<List<AnimalSyncDTO>> sync(
            @RequestParam("last_sync") String lastSync
    ) {

        LocalDateTime dateTime =
                LocalDateTime.parse(lastSync);

        List<Animal> animals =
                animalService.findAllAnimalUpdatedSince(dateTime);

        return ResponseEntity.ok(
                AnimalMapper.toAnimalSyncDTOList(animals)
        );
    }
}