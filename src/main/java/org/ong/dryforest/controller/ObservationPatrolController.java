package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.CountByTypeDTO;
import org.ong.dryforest.dto.observationPatrol.ObservationPatrolDTO;
import org.ong.dryforest.dto.observationPatrol.ObservationPatrolWebDTO;
import org.ong.dryforest.entity.ObservationPatrol;
import org.ong.dryforest.mapper.ObservationPatrolMapper;
import org.ong.dryforest.service.observationPatrol.ObservationPatrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/observationPatrol")
public class ObservationPatrolController {
    @Autowired
    ObservationPatrolService observationPatrolService;

    @GetMapping
    public ResponseEntity<List<ObservationPatrolWebDTO>> getAll() {
        List<ObservationPatrol> observation_pa = observationPatrolService.findAll();
        List<ObservationPatrolWebDTO> observationPatrolDTO = observation_pa == null ? new ArrayList<>() : observation_pa.stream().map(ObservationPatrolMapper::toObservationPatrolWebDTO).collect(Collectors.toList());
        return ResponseEntity.ok(observationPatrolDTO);
    }

    @GetMapping("/countByType")
    public ResponseEntity<List<CountByTypeDTO>> countByType() {
        return ResponseEntity.ok(observationPatrolService.countByType());
    }


    @PostMapping
    public ResponseEntity<ObservationPatrol> create(ObservationPatrolDTO observationPatrolDTO) {
        ObservationPatrol observationPatrol = observationPatrolService.create(observationPatrolDTO);
        return ResponseEntity.status(201).body(observationPatrol);
    }

}
