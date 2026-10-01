package org.ong.dryforest.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.patrolGroup.PatrolGroupDTO;
import org.ong.dryforest.entity.PatrolGroup;
import org.ong.dryforest.mapper.PatrolGroupMapper;
import org.ong.dryforest.service.incidentPatrol.PatrolGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patrolGroup")
public class PatrolGroupController {

    @Autowired
    PatrolGroupService patrolGroupService;


    @GetMapping
    public ResponseEntity<List<PatrolGroupDTO>> findAll() {

        List<PatrolGroupDTO> patrolGroupDTO = new ArrayList<>();

        List<PatrolGroup> patrolGroup =
                patrolGroupService.findAll();

        if (patrolGroup != null && !patrolGroup.isEmpty()) {

            patrolGroupDTO = patrolGroup.stream()
                    .map(PatrolGroupMapper::toPatrolGroupDTO)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(patrolGroupDTO);
    }


    @GetMapping("/sync")
    public ResponseEntity<List<PatrolGroupDTO>> sync(
            @RequestParam("last_sync")
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime lastSync) {

        List<PatrolGroup> patrolGroups =
                patrolGroupService.findAllPatrolGroupUpdatedSince(
                        lastSync
                );

        List<PatrolGroupDTO> result = patrolGroups.stream()
                .map(PatrolGroupMapper::toPatrolGroupDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PatrolGroupDTO> findById(
            @PathVariable int id) {

        PatrolGroup patrolGroup =
                patrolGroupService.findById(id);

        return ResponseEntity.ok(
                PatrolGroupMapper.toPatrolGroupDTO(patrolGroup)
        );
    }


    @PostMapping
    public ResponseEntity<PatrolGroupDTO> create(
            @RequestBody PatrolGroup patrolGroup) {

        PatrolGroup savedPatrolGroup =
                patrolGroupService.createPatrolGroup(patrolGroup);

        return ResponseEntity.ok(
                PatrolGroupMapper.toPatrolGroupDTO(savedPatrolGroup)
        );
    }



    @PutMapping("/{id}")
    public ResponseEntity<PatrolGroupDTO> update(
            @PathVariable int id,
            @RequestBody PatrolGroup patrolGroup) {

        patrolGroup.setId(id);

        PatrolGroup updatedPatrolGroup =
                patrolGroupService.updatePatrolGroup(patrolGroup);

        return ResponseEntity.ok(
                PatrolGroupMapper.toPatrolGroupDTO(updatedPatrolGroup)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        PatrolGroup patrolGroup =
                patrolGroupService.findById(id);

        patrolGroupService.deletePatrolGroup(patrolGroup);

        return ResponseEntity.noContent().build();
    }
}