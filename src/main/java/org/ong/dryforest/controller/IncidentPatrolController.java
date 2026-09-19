package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.CountByTypeDTO;
import org.ong.dryforest.dto.incidentPatrol.IncidentPatrolWebDTO;
import org.ong.dryforest.entity.IncidentPatrol;
import org.ong.dryforest.mapper.IncidentPatrolMapper;
import org.ong.dryforest.service.incidentPatrol.IncidentPatrolService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/incidentPatrol")
public class IncidentPatrolController {

    @Autowired
    IncidentPatrolService incidentPatrolService;

    @GetMapping
    public ResponseEntity<List<IncidentPatrolWebDTO>> getAll() {
        List<IncidentPatrol> incidentPatrol = incidentPatrolService.findAll();
        List<IncidentPatrolWebDTO> incidentPatrolDTO = incidentPatrol == null ? new ArrayList<>() : incidentPatrol.stream().map(IncidentPatrolMapper::toIncidentPatrolWebDTO).collect(Collectors.toList());
        return ResponseEntity.ok(incidentPatrolDTO);
    }
    
    @GetMapping("/countByType")
    public ResponseEntity<List<CountByTypeDTO>> countByType() {
        return ResponseEntity.ok(incidentPatrolService.countByType());
    }
}
