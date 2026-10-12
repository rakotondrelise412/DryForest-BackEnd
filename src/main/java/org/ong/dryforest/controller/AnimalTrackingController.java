package org.ong.dryforest.controller;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.animaltracking.AnimalObservedByZoneDTO;
import org.ong.dryforest.dto.animaltracking.AnimalTrackingDTO;
import org.ong.dryforest.entity.AnimalTracking;
import org.ong.dryforest.mapper.AnimalTrackingMapper;
import org.ong.dryforest.service.animalTracking.AnimalTrackingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/animal_tracking")
@RequiredArgsConstructor
public class AnimalTrackingController {

    private final AnimalTrackingService animalTrackingService;
    private final AnimalTrackingMapper animalTrackingMapper;

    @GetMapping("/observed-animals-by-zone")
    public ResponseEntity<List<AnimalObservedByZoneDTO>>
    getObservedAnimalsByZone() {

        return ResponseEntity.ok(
                animalTrackingService
                        .getObservedAnimalsByZone()
        );
    }

    @GetMapping
    public ResponseEntity<List<AnimalTrackingDTO>>
    findAll() {

        return ResponseEntity.ok(
                animalTrackingMapper.toDTOList(
                        animalTrackingService.findAll()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalTrackingDTO>
    findById(@PathVariable int id) {

        return ResponseEntity.ok(
                animalTrackingMapper.toDTO(
                        animalTrackingService.findById(id)
                )
        );
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<AnimalTrackingDTO>
    findByUuid(@PathVariable UUID uuid) {

        return ResponseEntity.ok(
                animalTrackingMapper.toDTO(
                        animalTrackingService.findByUuid(uuid)
                )
        );
    }

    @PostMapping
    public ResponseEntity<AnimalTrackingDTO>
    create(
            @RequestBody AnimalTrackingDTO dto
    ) throws Exception {

        AnimalTracking animalTracking =
                animalTrackingService.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        animalTrackingMapper.toDTO(
                                animalTracking
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalTrackingDTO>
    update(
            @PathVariable int id,
            @RequestBody AnimalTrackingDTO dto
    ) throws Exception {

        AnimalTracking updated =
                animalTrackingService.update(id, dto);

        return ResponseEntity.ok(
                animalTrackingMapper.toDTO(updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        AnimalTracking animalTracking =
                animalTrackingService.findById(id);

        animalTrackingService.deleteAnimalTracking(
                animalTracking
        );

        return ResponseEntity.noContent().build();
    }
}