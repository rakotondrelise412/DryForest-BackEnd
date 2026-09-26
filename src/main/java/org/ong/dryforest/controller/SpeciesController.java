package org.ong.dryforest.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.species.SpeciesDTO;
import org.ong.dryforest.service.species.SpeciesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/species")
@RequiredArgsConstructor
public class SpeciesController {

    private final SpeciesService speciesService;

    // =========================
    // GET ALL OK
    // =========================
    @GetMapping
    public ResponseEntity<List<SpeciesDTO>> getAll() {

        return ResponseEntity.ok(
                speciesService.findAllSpecies()
        );
    }

    // =========================
    // GET BY ID OK
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<SpeciesDTO> getById(
            @PathVariable int id
    ) {

        return ResponseEntity.ok(
                speciesService.findSpeciesById(id)
        );
    }

    // =========================
    // GET BY TYPE OK
    // =========================
    @GetMapping("/type/{id_species_type}")
    public ResponseEntity<List<SpeciesDTO>> getByType(
            @PathVariable int id_species_type
    ) {

        return ResponseEntity.ok(
                speciesService.findAllSpeciesByType(
                        id_species_type
                )
        );
    }

    // =========================
    // GET BY IDS  KO
    // =========================
    @PostMapping("/by-ids")
    public ResponseEntity<List<SpeciesDTO>> getByIds(
            @RequestBody List<Integer> ids
    ) {

        return ResponseEntity.ok(
                speciesService.findAllSpeciesById(ids)
        );
    }

    // =========================
    // CREATE non KO
    // =========================
    @PostMapping
    public ResponseEntity<SpeciesDTO> create(
            @RequestBody SpeciesDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        speciesService.createSpecies(dto)
                );
    }

    // =========================
    // UPDATE OK
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<SpeciesDTO> update(
            @PathVariable int id,
            @RequestBody SpeciesDTO dto
    ) {

        return ResponseEntity.ok(
                speciesService.updateSpecies(id, dto)
        );
    }

    // =========================
    // DELETE KO
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        speciesService.deleteSpecies(id);

        return ResponseEntity.noContent().build();
    }

    // =========================
    // SYNCHRONISATION non testé
    // =========================
    @GetMapping("/updated-since")
    public ResponseEntity<List<SpeciesDTO>> getUpdatedSince(
            @RequestParam("last_sync")
            LocalDateTime last_sync
    ) {

        return ResponseEntity.ok(
                speciesService.findAllSpeciesUpdatedSince(
                        last_sync
                )
        );
    }
}