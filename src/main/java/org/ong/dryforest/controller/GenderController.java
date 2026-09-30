package org.ong.dryforest.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.gender.GenderDTO;
import org.ong.dryforest.service.gender.GenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genders")
@RequiredArgsConstructor
@CrossOrigin
public class GenderController {

    private final GenderService genderService;

    @PostMapping
    public ResponseEntity<GenderDTO> create(
            @RequestBody GenderDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(genderService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<GenderDTO>> findAll() {

        return ResponseEntity.ok(
                genderService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenderDTO> findById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                genderService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenderDTO> update(
            @PathVariable Integer id,
            @RequestBody GenderDTO dto) {

        return ResponseEntity.ok(
                genderService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) {

        genderService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

