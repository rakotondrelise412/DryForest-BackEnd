package org.ong.dryforest.controller;

import org.ong.dryforest.dto.plantationBlock.PlantationBlockDTO;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockMobileDTO;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockWebDTO;
import org.ong.dryforest.entity.PlantationBlock;
import org.ong.dryforest.mapper.PlantationBlockMapper;
import org.ong.dryforest.service.plantationBlock.PlantationBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plantationBlock")
public class PlantationBlockController {

    @Autowired
    private PlantationBlockService plantationBlockService;


    @GetMapping
    public ResponseEntity<List<PlantationBlockMobileDTO>>
    findAll() {

        List<PlantationBlock> plantationBlocks =
                plantationBlockService.findAll();

        List<PlantationBlockMobileDTO> result =
                plantationBlocks
                        .stream()
                        .map(
                                PlantationBlockMapper
                                        ::toPlantationBlockMobileDTO
                        )
                        .collect(
                                Collectors.toList()
                        );

        return ResponseEntity.ok(result);
    }


    @GetMapping("/all")
    public ResponseEntity<List<PlantationBlockDTO>>
    getAllPlantationBlock() {

        List<PlantationBlock> plantationBlocks =
                plantationBlockService.findAll();

        List<PlantationBlockDTO> result =
                plantationBlocks
                        .stream()
                        .map(
                                PlantationBlockMapper
                                        ::toPlantationBlockDTO
                        )
                        .collect(
                                Collectors.toList()
                        );

        return ResponseEntity.ok(result);
    }


    @GetMapping("/filterPlantationBlock")
    public ResponseEntity<List<PlantationBlockWebDTO>>
    getAll() {

        List<PlantationBlock> plantationBlocks =
                plantationBlockService.findAll();

        List<PlantationBlockWebDTO> result =
                plantationBlocks
                        .stream()
                        .map(
                                PlantationBlockMapper
                                        ::toPlantationBlockWebDTO
                        )
                        .collect(
                                Collectors.toList()
                        );

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PlantationBlockDTO>
    getById(
            @PathVariable int id) {

        PlantationBlock plantationBlock =
                plantationBlockService.findById(id);

        return ResponseEntity.ok(
                PlantationBlockMapper
                        .toPlantationBlockDTO(
                                plantationBlock
                        )
        );
    }


    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<PlantationBlockDTO>
    getByUuid(
            @PathVariable UUID uuid) {

        PlantationBlock plantationBlock =
                plantationBlockService.findByUuid(uuid);

        return ResponseEntity.ok(
                PlantationBlockMapper
                        .toPlantationBlockDTO(
                                plantationBlock
                        )
        );
    }


    @PostMapping
    public ResponseEntity<PlantationBlockDTO>
    create(
            @RequestBody PlantationBlockDTO dto) {

        PlantationBlock plantationBlock =
                plantationBlockService
                        .createPlantationBlock(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        PlantationBlockMapper
                                .toPlantationBlockDTO(
                                        plantationBlock
                                )
                );
    }


    @PutMapping("/{id}")
    public ResponseEntity<PlantationBlockDTO>
    update(
            @PathVariable int id,
            @RequestBody PlantationBlockDTO dto) {

        PlantationBlock plantationBlock =
                plantationBlockService
                        .updatePlantationBlock(
                                id,
                                dto
                        );

        return ResponseEntity.ok(
                PlantationBlockMapper
                        .toPlantationBlockDTO(
                                plantationBlock
                        )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    delete(
            @PathVariable int id) {

        plantationBlockService
                .deletePlantationBlock(id);

        return ResponseEntity.noContent().build();
    }
}