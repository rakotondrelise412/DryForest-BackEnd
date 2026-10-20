package org.ong.dryforest.controller;

import org.ong.dryforest.dto.observationPatrol.TypeObservationPatrolDTO;
import org.ong.dryforest.entity.TypeObservationPatrol;
import org.ong.dryforest.mapper.TypeObservationPatrolMapper;
import org.ong.dryforest.service.observationPatrol.TypeObservationPatrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/typeObservationPatrol")
public class TypeObservationController {

    @Autowired
    private TypeObservationPatrolService typeObservationService;


    @GetMapping
    public ResponseEntity<Page<TypeObservationPatrolDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<TypeObservationPatrolDTO> result =
                typeObservationService.findAll(pageable)
                        .map(TypeObservationPatrolMapper::toObservationDTO);

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TypeObservationPatrolDTO> findById(
            @PathVariable int id
    ) {

        TypeObservationPatrol typeObservation =
                typeObservationService.findById(id);

        return ResponseEntity.ok(
                TypeObservationPatrolMapper.toObservationDTO(typeObservation)
        );
    }

    @PostMapping
    public ResponseEntity<TypeObservationPatrolDTO> create(
            @RequestBody TypeObservationPatrol typeObservation
    ) {

        TypeObservationPatrol created =
                typeObservationService.createObservationPatrol(typeObservation);

        return ResponseEntity.ok(
                TypeObservationPatrolMapper.toObservationDTO(created)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeObservationPatrolDTO> update(
            @PathVariable int id,
            @RequestBody TypeObservationPatrol typeObservation
    ) {

        typeObservation.setId(id);

        TypeObservationPatrol updated =
                typeObservationService.updateTypeObservationPatrol(typeObservation);

        return ResponseEntity.ok(
                TypeObservationPatrolMapper.toObservationDTO(updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        TypeObservationPatrol typeObservation =
                typeObservationService.findById(id);

        typeObservationService.deleteTypeObservationPatrol(typeObservation);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/sync")
    public ResponseEntity<List<TypeObservationPatrolDTO>> sync(
            @RequestParam("last_sync") LocalDateTime lastSync
    ) {

        List<TypeObservationPatrol> typeObservation =
                typeObservationService.findAllTypeObservationPatrolUpdatedSince(lastSync);

        return ResponseEntity.ok(
                TypeObservationPatrolMapper.toDTOList(typeObservation)
        );
    }
}