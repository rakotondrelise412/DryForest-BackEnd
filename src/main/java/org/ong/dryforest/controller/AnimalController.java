package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.animaltracking.AnimalDTO;
import org.ong.dryforest.entity.Animal;
import org.ong.dryforest.mapper.AnimalMapper;
import org.ong.dryforest.service.animalTracking.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;
    
    @GetMapping
    public ResponseEntity<List<AnimalDTO>> findAll(){
        List<AnimalDTO> animalDTO = new ArrayList<>();
        List<Animal> animals = animalService.findAll();

        if (animals != null && !animals.isEmpty()) {
            animalDTO = animalService.findAll().stream().map(AnimalMapper::toAnimalDTO).collect(Collectors.toList());
        }
        return ResponseEntity.ok(animalDTO);
    }
}
