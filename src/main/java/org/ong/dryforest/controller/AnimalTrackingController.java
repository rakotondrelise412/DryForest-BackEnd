package org.ong.dryforest.controller;

import java.util.List;

import org.ong.dryforest.dto.animaltracking.AnimalObservedByZoneDTO;
import org.ong.dryforest.dto.animaltracking.AnimalTrackingDTO;
import org.ong.dryforest.entity.AnimalTracking;
import org.ong.dryforest.service.animalTracking.AnimalTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/animal_tracking")
public class AnimalTrackingController {
    
    @Autowired
    private AnimalTrackingService animalTrackingService;

    @GetMapping("/observed-animals-by-zone")
    public ResponseEntity<List<AnimalObservedByZoneDTO>> getObservedAnimalsByZone() {
        return ResponseEntity.ok(animalTrackingService.getObservedAnimalsByZone());
    }

    @PostMapping
    public ResponseEntity<AnimalTracking> create(@RequestBody AnimalTrackingDTO animalTrackingDTO) throws Exception{
        AnimalTracking animalTracking = animalTrackingService.create(animalTrackingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalTracking);
    }
}
