package org.ong.dryforest.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.species.SpeciesTypeDTO;
import org.ong.dryforest.service.species.SpeciesTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
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
    @GetMapping
    public ResponseEntity<PagedModel<SpeciesTypeDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<SpeciesTypeDTO> result =
                speciesTypeService.findAllSpeciesTypes(pageable);

        return ResponseEntity.ok(new PagedModel<>(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpeciesTypeDTO> getById(
            @PathVariable int id
    ) {

        return ResponseEntity.ok(
                speciesTypeService.findSpeciesTypeById(id)
        );
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        speciesTypeService.deleteSpeciesType(id);

        return ResponseEntity.noContent().build();
    }

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