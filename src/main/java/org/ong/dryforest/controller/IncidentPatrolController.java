package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.CountByTypeDTO;
import org.ong.dryforest.dto.incidentPatrol.IncidentPatrolDTO;
import org.ong.dryforest.dto.incidentPatrol.IncidentPatrolWebDTO;
import org.ong.dryforest.entity.IncidentPatrol;
import org.ong.dryforest.mapper.IncidentPatrolMapper;
import org.ong.dryforest.service.incidentPatrol.IncidentPatrolService;
import org.ong.dryforest.service.incidentPatrol.PatrolGroupService;
import org.ong.dryforest.service.incidentPatrol.TypeIncidentPatrolService;
import org.ong.dryforest.service.plantationBlock.PlantationBlockService;
import org.ong.dryforest.service.severity.SeverityService;
import org.ong.dryforest.service.user.UserService;
import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/incidentPatrol")
public class IncidentPatrolController {

    @Autowired
    private IncidentPatrolService incidentPatrolService;

    @Autowired
    private PatrolGroupService patrolGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private PlantationBlockService plantationBlockService;

    @Autowired
    private SeverityService severityService;

    @Autowired
    private TypeIncidentPatrolService typeIncidentPatrolService;


    @GetMapping
    public ResponseEntity<List<IncidentPatrolWebDTO>> getAll() {

        List<IncidentPatrol> incidentPatrol =
                incidentPatrolService.findAll();

        List<IncidentPatrolWebDTO> incidentPatrolDTO =
                incidentPatrol == null
                        ? new ArrayList<>()
                        : incidentPatrol.stream()
                        .map(IncidentPatrolMapper::toIncidentPatrolWebDTO)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(incidentPatrolDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<IncidentPatrolWebDTO> getById(
            @PathVariable int id
    ) {

        IncidentPatrol incidentPatrol =
                incidentPatrolService.findById(id);

        return ResponseEntity.ok(
                IncidentPatrolMapper.toIncidentPatrolWebDTO(incidentPatrol)
        );
    }



    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<IncidentPatrolWebDTO> getByUuid(
            @PathVariable UUID uuid
    ) {

        IncidentPatrol incidentPatrol =
                incidentPatrolService.findByUuid(uuid);

        return ResponseEntity.ok(
                IncidentPatrolMapper.toIncidentPatrolWebDTO(incidentPatrol)
        );
    }



    @PostMapping
    public ResponseEntity<IncidentPatrolWebDTO> create(
            @RequestBody IncidentPatrolDTO dto
    ) {

        IncidentPatrol incidentPatrol =
                IncidentPatrolMapper.toEntity(
                        dto,
                        patrolGroupService,
                        userService,
                        zoneService,
                        plantationBlockService,
                        severityService,
                        typeIncidentPatrolService
                );

        IncidentPatrol saved =
                incidentPatrolService.createIncidentPatrol(incidentPatrol);

        return ResponseEntity.ok(
                IncidentPatrolMapper.toIncidentPatrolWebDTO(saved)
        );
    }



    @PutMapping("/{id}")
    public ResponseEntity<IncidentPatrolWebDTO> update(
            @PathVariable int id,
            @RequestBody IncidentPatrolDTO dto
    ) {

        IncidentPatrol existing =
                incidentPatrolService.findById(id);

        IncidentPatrolMapper.updateEntity(
                existing,
                dto,
                patrolGroupService,
                userService,
                zoneService,
                plantationBlockService,
                severityService,
                typeIncidentPatrolService
        );

        IncidentPatrol updated =
                incidentPatrolService.updateIncidentPatrol(existing);

        return ResponseEntity.ok(
                IncidentPatrolMapper.toIncidentPatrolWebDTO(updated)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        IncidentPatrol incidentPatrol =
                incidentPatrolService.findById(id);

        incidentPatrolService.deleteIncidentPatrol(incidentPatrol);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/countByType")
    public ResponseEntity<List<CountByTypeDTO>> countByType() {

        return ResponseEntity.ok(
                incidentPatrolService.countByType()
        );
    }
}