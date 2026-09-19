package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.patrolGroup.PatrolGroupDTO;
import org.ong.dryforest.entity.PatrolGroup;
import org.ong.dryforest.mapper.PatrolGroupMapper;
import org.ong.dryforest.service.incidentPatrol.PatrolGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patrolGroup")
public class PatrolGroupController {
    
    @Autowired
    PatrolGroupService patrolGroupService;

    @GetMapping
    public ResponseEntity<List<PatrolGroupDTO>> findAll(){
        List<PatrolGroupDTO> patrolGroupDTO = new ArrayList<>();
        List<PatrolGroup> patrolGoup = patrolGroupService.findAll();

        if (patrolGoup != null && !patrolGoup.isEmpty()) {
            patrolGroupDTO = patrolGroupService.findAll().stream().map(PatrolGroupMapper::toPatrolGroupDTO).collect(Collectors.toList());
        }

        return ResponseEntity.ok(patrolGroupDTO);
    }
}
