package org.ong.dryforest.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.species.SpeciesTypeDTO;
import org.ong.dryforest.service.species.SpeciesTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/species-types")
@RequiredArgsConstructor
public class SpeciesTypeController {

    private final SpeciesTypeService speciesTypeService;

    // =========================
    // GET ALL Ok
    // =========================
    @GetMapping
    public ResponseEntity<List<SpeciesTypeDTO>> getAll() {

        return ResponseEntity.ok(
                speciesTypeService.findAllSpeciesTypes()
        );
    }

    // =========================
    // GET BY ID OK
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<SpeciesTypeDTO> getById(
            @PathVariable int id
    ) {

        return ResponseEntity.ok(
                speciesTypeService.findSpeciesTypeById(id)
        );
    }

    // =========================
    // CREATE KO
    // =========================
    @PostMapping
    public ResponseEntity<SpeciesTypeDTO> create(
            @RequestBody SpeciesTypeDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        speciesTypeService
                                .createSpeciesType(dto)
                );
    }

    // =========================
    // UPDATE OK
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<SpeciesTypeDTO> update(
            @PathVariable int id,
            @RequestBody SpeciesTypeDTO dto
    ) {

        return ResponseEntity.ok(
                speciesTypeService
                        .updateSpeciesType(id, dto)
        );
    }

    // =========================
    // DELETE KO
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        speciesTypeService.deleteSpeciesType(id);

        return ResponseEntity.noContent().build();
    }

    // =========================
    // SYNCHRONISATION non testé
    // =========================
    @GetMapping("/updated-since")
    public ResponseEntity<List<SpeciesTypeDTO>>
    getUpdatedSince(
            @RequestParam("last_sync")
            LocalDateTime last_sync
    ) {

        return ResponseEntity.ok(
                speciesTypeService
                        .findAllTypesUpdatedSince(
                                last_sync
                        )
        );
    }
}