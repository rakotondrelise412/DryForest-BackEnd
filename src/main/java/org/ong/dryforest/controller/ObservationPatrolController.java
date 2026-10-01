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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/observationPatrol")
public class ObservationPatrolController {

    @Autowired
    ObservationPatrolService observationPatrolService;


    @GetMapping
    public ResponseEntity<List<ObservationPatrolWebDTO>> getAll() {

        List<ObservationPatrol> observation_pa =
                observationPatrolService.findAll();

        List<ObservationPatrolWebDTO> observationPatrolDTO =
                observation_pa == null
                        ? new ArrayList<>()
                        : observation_pa.stream()
                        .map(ObservationPatrolMapper::toObservationPatrolWebDTO)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(observationPatrolDTO);
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
    public ResponseEntity<ObservationPatrol> create(
            @RequestBody ObservationPatrolDTO observationPatrolDTO) {

        ObservationPatrol observationPatrol =
                observationPatrolService.create(observationPatrolDTO);

        return ResponseEntity
                .status(201)
                .body(observationPatrol);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ObservationPatrol> update(
            @PathVariable int id,
            @RequestBody ObservationPatrol observationPatrol) {

        observationPatrol.setId(id);

        ObservationPatrol updated =
                observationPatrolService
                        .updateObservationPatrol(observationPatrol);

        return ResponseEntity.ok(updated);
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