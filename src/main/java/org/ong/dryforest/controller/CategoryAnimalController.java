package org.ong.dryforest.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.animaltracking.CategoryAnimalDTO;
import org.ong.dryforest.entity.CategoryAnimal;
import org.ong.dryforest.mapper.CategoryAnimalMapper;
import org.ong.dryforest.service.animalTracking.CategoryAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categoryAnimal")
public class CategoryAnimalController {

    @Autowired
    private CategoryAnimalService categoryAnimalService;


    @GetMapping
    public ResponseEntity<List<CategoryAnimalDTO>> findAll() {

        List<CategoryAnimal> categoryAnimals =
                categoryAnimalService.findAll();

        List<CategoryAnimalDTO> categoryAnimalDTO =
                new ArrayList<>();

        if (categoryAnimals != null && !categoryAnimals.isEmpty()) {

            categoryAnimalDTO = categoryAnimals.stream()
                    .map(CategoryAnimalMapper::toCategoryAnimalDTO)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(categoryAnimalDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryAnimalDTO> findById(
            @PathVariable int id) {

        CategoryAnimal categoryAnimal =
                categoryAnimalService.findById(id);

        return ResponseEntity.ok(
                CategoryAnimalMapper.toCategoryAnimalDTO(categoryAnimal)
        );
    }


    @GetMapping("/sync")
    public ResponseEntity<List<CategoryAnimalDTO>> sync(
            @RequestParam("last_sync")
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime lastSync) {

        List<CategoryAnimal> categoryAnimals =
                categoryAnimalService
                        .findAllCategoryAnimalUpdatedSince(lastSync);

        List<CategoryAnimalDTO> result =
                categoryAnimals.stream()
                        .map(CategoryAnimalMapper::toCategoryAnimalDTO)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<CategoryAnimalDTO> create(
            @RequestBody CategoryAnimal categoryAnimal) {

        CategoryAnimal saved =
                categoryAnimalService
                        .createCategoryAnimal(categoryAnimal);

        return ResponseEntity
                .status(201)
                .body(
                        CategoryAnimalMapper
                                .toCategoryAnimalDTO(saved)
                );
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoryAnimalDTO> update(
            @PathVariable int id,
            @RequestBody CategoryAnimal categoryAnimal) {

        categoryAnimal.setId(id);

        CategoryAnimal updated =
                categoryAnimalService
                        .updateCategoryAnimal(categoryAnimal);

        return ResponseEntity.ok(
                CategoryAnimalMapper
                        .toCategoryAnimalDTO(updated)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        CategoryAnimal categoryAnimal =
                categoryAnimalService.findById(id);

        categoryAnimalService
                .deleteCategoryAnimal(categoryAnimal);

        return ResponseEntity.noContent().build();
    }
}