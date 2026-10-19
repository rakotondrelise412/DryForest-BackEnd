package org.ong.dryforest.controller;

import org.ong.dryforest.dto.CountByTypeDTO;
import org.ong.dryforest.dto.observationPatrol.ObservationPatrolDTO;
import org.ong.dryforest.dto.observationPatrol.ObservationPatrolWebDTO;
import org.ong.dryforest.entity.ObservationPatrol;
import org.ong.dryforest.mapper.ObservationPatrolMapper;
import org.ong.dryforest.service.observationPatrol.ObservationPatrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/observationPatrol")
public class ObservationPatrolController {

    @Autowired
    ObservationPatrolService observationPatrolService;

    @GetMapping
    public ResponseEntity<Page<ObservationPatrolWebDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<ObservationPatrolWebDTO> result =
                observationPatrolService.findAll(pageable)
                        .map(ObservationPatrolMapper::toObservationPatrolWebDTO);

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ObservationPatrolWebDTO> getById(
            @PathVariable int id) {

        ObservationPatrol observationPatrol =
                observationPatrolService.findById(id);

        return ResponseEntity.ok(
                ObservationPatrolMapper
                        .toObservationPatrolWebDTO(observationPatrol)
        );
    }


    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<ObservationPatrolWebDTO> getByUuid(
            @PathVariable UUID uuid) {

        ObservationPatrol observationPatrol =
                observationPatrolService.findByUuid(uuid);

        return ResponseEntity.ok(
                ObservationPatrolMapper
                        .toObservationPatrolWebDTO(observationPatrol)
        );
    }


    @GetMapping("/exists/{uuid}")
    public ResponseEntity<Boolean> existsByUuid(
            @PathVariable UUID uuid) {

        return ResponseEntity.ok(
                observationPatrolService.existsByUuid(uuid)
        );
    }


    @GetMapping("/countByType")
    public ResponseEntity<List<CountByTypeDTO>> countByType() {

        return ResponseEntity.ok(
                observationPatrolService.countByType()
        );
    }


    @PostMapping
    public ResponseEntity<ObservationPatrolDTO> create(
            @RequestBody ObservationPatrolDTO observationPatrolDTO) {

        ObservationPatrol observationPatrol =
                observationPatrolService.create(observationPatrolDTO);

        ObservationPatrolDTO response =
                ObservationPatrolMapper.toObservationPatrolDTO(
                        observationPatrol
                );

        return ResponseEntity
                .status(201)
                .body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ObservationPatrolDTO> update(
            @PathVariable int id,
            @RequestBody ObservationPatrolDTO observationPatrolDTO) {

        ObservationPatrol updated =
                observationPatrolService.updateObservationPatrol(
                        id,
                        observationPatrolDTO
                );

        ObservationPatrolDTO response =
                ObservationPatrolMapper.toObservationPatrolDTO(
                        updated
                );

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        ObservationPatrol observationPatrol =
                observationPatrolService.findById(id);

        observationPatrolService
                .deleteObservationPatrol(observationPatrol);

        return ResponseEntity.noContent().build();
    }
}