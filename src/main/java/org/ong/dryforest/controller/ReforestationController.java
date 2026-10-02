package org.ong.dryforest.controller;

import java.util.List;
import java.util.UUID;

import org.ong.dryforest.dto.reforestation.*;
import org.ong.dryforest.entity.Reforestation;
import org.ong.dryforest.mapper.ReforestationMapper;
import org.ong.dryforest.service.reforestation.ReforestationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reforestation")
public class ReforestationController {

    @Autowired
    private ReforestationService reforestationService;

    @Autowired
    private ReforestationMapper reforestationMapper;


    @GetMapping
    public ResponseEntity<List<ReforestationResponseDTO>> getAll() {

        List<ReforestationResponseDTO> response =
                reforestationService.findAll()
                        .stream()
                        .map(reforestationMapper::toResponseDTO)
                        .toList();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReforestationResponseDTO> getById(
            @PathVariable int id) {

        Reforestation reforestation =
                reforestationService.findById(id);

        return ResponseEntity.ok(
                reforestationMapper.toResponseDTO(
                        reforestation
                )
        );
    }


    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<ReforestationResponseDTO> getByUuid(
            @PathVariable UUID uuid) {

        Reforestation reforestation =
                reforestationService.findByUuid(uuid);

        return ResponseEntity.ok(
                reforestationMapper.toResponseDTO(
                        reforestation
                )
        );
    }


    @PostMapping("/with-details")
    public ResponseEntity<ReforestationResponseDTO>
    createWithDetails(
            @RequestBody ReforestationDTO dto) {

        Reforestation created =
                reforestationService
                        .createReforestationWithDetails(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        reforestationMapper.toResponseDTO(
                                created
                        )
                );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReforestationResponseDTO> update(
            @PathVariable int id,
            @RequestBody Reforestation reforestation) {

        reforestation.setId(id);

        Reforestation updated =
                reforestationService.updateReforestation(
                        reforestation
                );

        return ResponseEntity.ok(
                reforestationMapper.toResponseDTO(
                        updated
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        Reforestation reforestation =
                reforestationService.findById(id);

        reforestationService.deleteReforestation(
                reforestation
        );

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/total_planted")
    public ResponseEntity<Integer> getTotalPlanted() {

        return ResponseEntity.ok(
                reforestationService.getTotalPlanted()
        );
    }


    @GetMapping("/total_last_planted")
    public ResponseEntity<LatestReforestationDTO>
    getTotalLastPlanted() {

        Reforestation reforestation =
                reforestationService
                        .getTotalLastPlanted();

        if (reforestation == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                reforestationMapper
                        .toLatestReforestationDTO(
                                reforestation
                        )
        );
    }


    @GetMapping("/ref_quantity_by_type_zone")
    public ResponseEntity<
            List<ReforestationCountingDTO>>
    getQuantityByTypeZone() {

        return ResponseEntity.ok(
                reforestationService
                        .getQuantityByTypeZone()
        );
    }
}

