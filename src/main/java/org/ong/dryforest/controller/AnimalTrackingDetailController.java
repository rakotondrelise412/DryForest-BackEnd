package org.ong.dryforest.controller;

import org.ong.dryforest.dto.animaltracking.AnimalTrackingDetailDTO;
import org.ong.dryforest.entity.AnimalTrackingDetail;
import org.ong.dryforest.mapper.AnimalTrackingDetailMapper;
import org.ong.dryforest.service.animalTracking.AnimalTrackingDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/animal_tracking_detail")
public class AnimalTrackingDetailController {

    private final AnimalTrackingDetailService
            animalTrackingDetailService;

    private final AnimalTrackingDetailMapper
            animalTrackingDetailMapper;

    public AnimalTrackingDetailController(
            AnimalTrackingDetailService animalTrackingDetailService,
            AnimalTrackingDetailMapper animalTrackingDetailMapper
    ) {
        this.animalTrackingDetailService =
                animalTrackingDetailService;

        this.animalTrackingDetailMapper =
                animalTrackingDetailMapper;
    }

    @GetMapping
    public ResponseEntity<List<AnimalTrackingDetailDTO>> findAll() {

        List<AnimalTrackingDetailDTO> result =
                animalTrackingDetailMapper.toDTOList(
                        animalTrackingDetailService.findAll()
                );

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalTrackingDetailDTO> findById(
            @PathVariable int id
    ) {

        AnimalTrackingDetail detail =
                animalTrackingDetailService.findById(id);

        return ResponseEntity.ok(
                animalTrackingDetailMapper.toDTO(detail)
        );
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<AnimalTrackingDetailDTO> findByUuid(
            @PathVariable UUID uuid
    ) {

        AnimalTrackingDetail detail =
                animalTrackingDetailService.findByUuid(uuid);

        return ResponseEntity.ok(
                animalTrackingDetailMapper.toDTO(detail)
        );
    }

    @PostMapping
    public ResponseEntity<AnimalTrackingDetailDTO> create(
            @RequestBody AnimalTrackingDetailDTO dto
    ) {

        AnimalTrackingDetail detail =
                animalTrackingDetailService.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        animalTrackingDetailMapper.toDTO(detail)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalTrackingDetailDTO> update(
            @PathVariable int id,
            @RequestBody AnimalTrackingDetailDTO dto
    ) {

        AnimalTrackingDetail detail =
                animalTrackingDetailService.update(
                        id,
                        dto
                );

        return ResponseEntity.ok(
                animalTrackingDetailMapper.toDTO(detail)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        AnimalTrackingDetail detail =
                animalTrackingDetailService.findById(id);

        animalTrackingDetailService
                .deleteAnimalTrackingDetail(detail);

        return ResponseEntity.noContent().build();
    }
}