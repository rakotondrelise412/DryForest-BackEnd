package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.plantationBlock.PlantationBlockDTO;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockMobileDTO;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockWebDTO;
import org.ong.dryforest.entity.PlantationBlock;
import org.ong.dryforest.mapper.PlantationBlockMapper;
import org.ong.dryforest.service.plantationBlock.PlantationBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plantationBlock")
public class PlantationBlockController {
    @Autowired
    private PlantationBlockService plantationBlockService;


    @GetMapping
    public ResponseEntity<List<PlantationBlockMobileDTO>> findAll() {

        List<PlantationBlockMobileDTO> plantationBlockMobileDTO = new ArrayList<>();
        List<PlantationBlock> plantationBlocks = plantationBlockService.findAll();

        if (plantationBlocks != null && !plantationBlocks.isEmpty()) {
            plantationBlockMobileDTO = plantationBlockService.findAll().stream().map(PlantationBlockMapper::toPlantationBlockMobileDTO).collect(Collectors.toList());
        }

        return ResponseEntity.ok(plantationBlockMobileDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlantationBlockDTO>> getAllPlantationBlock() {
        List<PlantationBlock> plantationBlocks = plantationBlockService.findAll();

        List<PlantationBlockDTO> plantationBlockDTO = plantationBlocks == null
                ? new ArrayList<>()
                : plantationBlocks.stream()
                    .map(PlantationBlockMapper::toPlantationBlockDTO)
                    .collect(Collectors.toList());

        return ResponseEntity.ok(plantationBlockDTO);
    }

    @GetMapping("/filterPlantationBlock")
    public ResponseEntity<List<PlantationBlockWebDTO>> getAll(){
        List<PlantationBlockWebDTO> plantationBlockWebDTO = new ArrayList<>();
        List<PlantationBlock> plantationBlocks = plantationBlockService.findAll();

        if (plantationBlocks != null && !plantationBlocks.isEmpty()) {
            plantationBlockWebDTO = plantationBlockService.findAll().stream().map(PlantationBlockMapper::toPlantationBlockWebDTO).collect(Collectors.toList());
        }

        return ResponseEntity.ok(plantationBlockWebDTO);
    }

    // @PostMapping
    // public ResponseEntity<PlantationBlock> save(@RequestBody PlantationBlockDTO plantationBlockDTO) throws Exception{
    //     PlantationBlock plantationB = plantationBlockService.createPlantationBlock(plantationBlockDTO);
    //     return ResponseEntity.status(201).body(plantationB);
    // }
}
