package org.ong.dryforest.controller;

import java.util.List;
import java.util.UUID;

import org.ong.dryforest.dto.species.SpeciesZoneNeedDTO;
import org.ong.dryforest.entity.SpeciesZoneNeed;
import org.ong.dryforest.service.zone.SpeciesZoneNeedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/species-zone-needs")
@RequiredArgsConstructor
public class SpeciesZoneNeedController {

    private final SpeciesZoneNeedService speciesZoneNeedService;


    // =========================================================
    // GET ALL
    // =========================================================

    @GetMapping
    public ResponseEntity<List<SpeciesZoneNeedDTO>> findAll() {

        return ResponseEntity.ok(
                speciesZoneNeedService.findAllDTO()
        );
    }


    // =========================================================
    // GET BY ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<SpeciesZoneNeedDTO> findById(
            @PathVariable int id
    ) {

        return ResponseEntity.ok(
                speciesZoneNeedService.findDTOById(id)
        );
    }


    // =========================================================
    // GET BY UUID
    // =========================================================

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<SpeciesZoneNeedDTO> findByUuid(
            @PathVariable UUID uuid
    ) {

        return ResponseEntity.ok(
                speciesZoneNeedService.findDTOByUuid(uuid)
        );
    }


    // =========================================================
    // POST
    // =========================================================

    @PostMapping
    public ResponseEntity<SpeciesZoneNeedDTO> create(
            @RequestBody SpeciesZoneNeedDTO dto
    ) {

        SpeciesZoneNeedDTO created =
                speciesZoneNeedService
                        .createSpeciesZoneNeedDTO(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }


    // =========================================================
    // PUT
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<SpeciesZoneNeedDTO> update(
            @PathVariable int id,
            @RequestBody SpeciesZoneNeedDTO dto
    ) {

        SpeciesZoneNeedDTO updated =
                speciesZoneNeedService
                        .updateSpeciesZoneNeedDTO(
                                id,
                                dto
                        );

        return ResponseEntity.ok(updated);
    }


    // =========================================================
    // DELETE
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        SpeciesZoneNeed entity =
                speciesZoneNeedService.findById(id);

        speciesZoneNeedService.deleteSpeciesZoneNeed(
                entity
        );

        return ResponseEntity.noContent().build();
    }
}