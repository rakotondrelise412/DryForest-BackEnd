package org.ong.dryforest.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.PlantingMonitoringDTO;
import org.ong.dryforest.dto.species.SpeciesStatDTO;
import org.ong.dryforest.entity.PlantingMonitoring;
import org.ong.dryforest.mapper.PlantingMonitoringMapper;
import org.ong.dryforest.service.plantingMonitoring.PlantingMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/planting_monitoring")
public class PlantingMonitoringController {
    @Autowired
    private PlantingMonitoringService plantingMonitoringService;

    @GetMapping
    public ResponseEntity<List<PlantingMonitoringDTO>> getAll() {
        List<PlantingMonitoringDTO> plantingMonitoringDTO = new ArrayList<>();
        List<PlantingMonitoring> plantingMonitoring = plantingMonitoringService.findAll();
        if (plantingMonitoring != null && !plantingMonitoring.isEmpty()) {
            plantingMonitoringDTO = plantingMonitoringService.findAll().stream().map(PlantingMonitoringMapper::toMonitoringDTO).collect(Collectors.toList());
        }
        return ResponseEntity.ok(plantingMonitoringDTO);
    }
    
    @GetMapping("/speciesStat")
    public ResponseEntity<List<SpeciesStatDTO>> getSpeciesStat() {
        return ResponseEntity.ok(plantingMonitoringService.statisticSpeciesAverageBySpeciesAndReforestationDate());
    }

    @GetMapping("/species/statistics/by-reforestation-date")
    public ResponseEntity<List<SpeciesStatDTO>> getSpeciesStatisticsByReforestationDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(
                plantingMonitoringService.statisticSpeciesAverageByReforestationDateRange(startDate, endDate)
        );
    }

    @PostMapping
    public ResponseEntity<PlantingMonitoring> create(@RequestBody PlantingMonitoringDTO plantingMonitoringDTO){
        PlantingMonitoring plantingMonitoring = plantingMonitoringService.create(plantingMonitoringDTO);
        return ResponseEntity.status(201).body(plantingMonitoring);
    }

}
