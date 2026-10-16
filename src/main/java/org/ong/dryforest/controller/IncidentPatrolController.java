package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import org.ong.dryforest.dto.CountByTypeDTO;
import org.ong.dryforest.dto.incidentPatrol.IncidentPatrolDTO;
import org.ong.dryforest.dto.incidentPatrol.IncidentPatrolWebDTO;
import org.ong.dryforest.entity.IncidentPatrol;
import org.ong.dryforest.mapper.IncidentPatrolMapper;
import org.ong.dryforest.service.incidentPatrol.ImageStorageService;
import org.ong.dryforest.service.incidentPatrol.IncidentPatrolService;
import org.ong.dryforest.service.incidentPatrol.PatrolGroupService;
import org.ong.dryforest.service.incidentPatrol.TypeIncidentPatrolService;
import org.ong.dryforest.service.plantationBlock.PlantationBlockService;
import org.ong.dryforest.service.severity.SeverityService;
import org.ong.dryforest.service.user.UserService;
import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestPart;


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

    @Autowired
    private ImageStorageService imageStorageService;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<IncidentPatrolWebDTO> create(
            @RequestPart("incidentPatrol") String incidentPatrolJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules();

        IncidentPatrolDTO dto = objectMapper.readValue(
                incidentPatrolJson,
                IncidentPatrolDTO.class
        );

        String imagePath = imageStorageService.store(image);

        dto.setImage(imagePath);

        IncidentPatrol incident = IncidentPatrolMapper.toEntity(
                dto,
                patrolGroupService,
                userService,
                zoneService,
                plantationBlockService,
                severityService,
                typeIncidentPatrolService
        );

        IncidentPatrol saved =
                incidentPatrolService.createIncidentPatrol(incident);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(IncidentPatrolMapper.toIncidentPatrolWebDTO(saved));
    }


    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<IncidentPatrolWebDTO> update(
            @PathVariable int id,
            @RequestPart("incidentPatrol") String incidentPatrolJson,
            @RequestPart(value = "image", required = false)
            MultipartFile image
    ) throws IOException {

        IncidentPatrol existing =
                incidentPatrolService.findById(id);
        ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules();

        IncidentPatrolDTO dto = objectMapper.readValue(
                incidentPatrolJson,
                IncidentPatrolDTO.class
        );

        if (image != null && !image.isEmpty()) {
            String imagePath = imageStorageService.store(image);
            dto.setImage(imagePath);
        } else {
            dto.setImage(existing.getImage());
        }

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

        existing.setImage(dto.getImage());

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