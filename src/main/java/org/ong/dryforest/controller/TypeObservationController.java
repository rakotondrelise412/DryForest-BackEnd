package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.observationPatrol.TypeObservationPatrolDTO;
import org.ong.dryforest.entity.TypeObservationPatrol;
import org.ong.dryforest.mapper.TypeObservationPatrolMapper;
import org.ong.dryforest.service.observationPatrol.TypeObservationPatrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/typeObservationPatrol")
public class TypeObservationController {
    @Autowired
    private TypeObservationPatrolService typeObservationService;

    @GetMapping
    public ResponseEntity<List<TypeObservationPatrolDTO>> findAll(){
        List<TypeObservationPatrolDTO> typeObservationDTO = new ArrayList<>();
        List<TypeObservationPatrol> typeObservation = typeObservationService.findAll();

        if (typeObservation != null && !typeObservation.isEmpty()) {
            typeObservationDTO = typeObservationService.findAll().stream().map(TypeObservationPatrolMapper::toObservationDTO).collect(Collectors.toList());
        }

        return ResponseEntity.ok(typeObservationDTO);
    }
}
