package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.patrolGroup.TypeIncidentPatrolDTO;
import org.ong.dryforest.entity.TypeIncidentPatrol;
import org.ong.dryforest.mapper.TypeIncidentPatrolMapper;
import org.ong.dryforest.service.incidentPatrol.TypeIncidentPatrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/typeIncidentPatrol")
public class TypeIncidentPatrolController {
    @Autowired
    TypeIncidentPatrolService typeIncidentPatrolService;

    @GetMapping
    public ResponseEntity<List<TypeIncidentPatrolDTO>> findAll(){
        List<TypeIncidentPatrolDTO> typeIncidentPatrolDTO = new ArrayList<>();
        List<TypeIncidentPatrol> typeIncidentPatrols = typeIncidentPatrolService.findAll();

        if (typeIncidentPatrols != null && !typeIncidentPatrols.isEmpty()) {
            typeIncidentPatrolDTO = typeIncidentPatrolService.findAll().stream().map(TypeIncidentPatrolMapper::toTypeIncidentPatrolDTO).collect(Collectors.toList());
        }
        return ResponseEntity.ok(typeIncidentPatrolDTO);
    }
}
