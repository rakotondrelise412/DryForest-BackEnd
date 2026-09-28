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
    // GET BY IDS  ok exemple sur postman: ttp://localhost:8082/api/species/by-ids sur body [1, 3, 5, 8]
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
    // SYNCHRONISATION ok: exemple: http://localhost:8082/api/species/updated-since?last_sync=2026-09-27T18:00:00
    //Il permet de demander :
    //« Donne-moi uniquement les espèces qui ont été modifiées depuis ma dernière synchronisation. »
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

    // =========================
    // CREATE ok
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
    // DELETE ok
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        speciesService.deleteSpecies(id);

        return ResponseEntity.noContent().build();
    }

}