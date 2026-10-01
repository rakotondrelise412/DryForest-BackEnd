package org.ong.dryforest.controller;

import org.ong.dryforest.dto.patrolGroup.TypeIncidentPatrolDTO;
import org.ong.dryforest.entity.TypeIncidentPatrol;
import org.ong.dryforest.mapper.TypeIncidentPatrolMapper;
import org.ong.dryforest.service.incidentPatrol.TypeIncidentPatrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/typeIncidentPatrol")
public class TypeIncidentPatrolController {

    @Autowired
    TypeIncidentPatrolService typeIncidentPatrolService;


    @GetMapping
    public ResponseEntity<List<TypeIncidentPatrolDTO>> findAll() {

        List<TypeIncidentPatrolDTO> typeIncidentPatrolDTO =
                new ArrayList<>();

        List<TypeIncidentPatrol> typeIncidentPatrols =
                typeIncidentPatrolService.findAll();

        if (typeIncidentPatrols != null && !typeIncidentPatrols.isEmpty()) {

            typeIncidentPatrolDTO = typeIncidentPatrols.stream()
                    .map(TypeIncidentPatrolMapper::toTypeIncidentPatrolDTO)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(typeIncidentPatrolDTO);
    }


    @GetMapping("/sync")
    public ResponseEntity<List<TypeIncidentPatrolDTO>> sync(
            @RequestParam("last_sync")
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime lastSync) {

        List<TypeIncidentPatrol> typeIncidentPatrols =
                typeIncidentPatrolService
                        .findAllTypeIncidentPatrolsUpdatedSince(lastSync);

        List<TypeIncidentPatrolDTO> result =
                typeIncidentPatrols.stream()
                        .map(TypeIncidentPatrolMapper::toTypeIncidentPatrolDTO)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TypeIncidentPatrolDTO> findById(
            @PathVariable int id) {

        TypeIncidentPatrol typeIncidentPatrol =
                typeIncidentPatrolService.findById(id);

        return ResponseEntity.ok(
                TypeIncidentPatrolMapper
                        .toTypeIncidentPatrolDTO(typeIncidentPatrol)
        );
    }


    @PostMapping
    public ResponseEntity<TypeIncidentPatrolDTO> create(
            @RequestBody TypeIncidentPatrol typeIncidentPatrol) {

        TypeIncidentPatrol saved =
                typeIncidentPatrolService
                        .createTypeIncidentPatrol(typeIncidentPatrol);

        return ResponseEntity.ok(
                TypeIncidentPatrolMapper
                        .toTypeIncidentPatrolDTO(saved)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<TypeIncidentPatrolDTO> update(
            @PathVariable int id,
            @RequestBody TypeIncidentPatrol typeIncidentPatrol) {

        typeIncidentPatrol.setId(id);

        TypeIncidentPatrol updated =
                typeIncidentPatrolService
                        .updateTypeIncidentPatrol(typeIncidentPatrol);

        return ResponseEntity.ok(
                TypeIncidentPatrolMapper
                        .toTypeIncidentPatrolDTO(updated)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        TypeIncidentPatrol typeIncidentPatrol =
                typeIncidentPatrolService.findById(id);

        typeIncidentPatrolService
                .deleteTypeIncidentPatrol(typeIncidentPatrol);

        return ResponseEntity.noContent().build();
    }
}