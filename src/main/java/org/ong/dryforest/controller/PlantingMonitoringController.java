package org.ong.dryforest.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.PlantingMonitoringDTO;
import org.ong.dryforest.dto.species.SpeciesStatDTO;
import org.ong.dryforest.entity.PlantingMonitoring;
import org.ong.dryforest.mapper.PlantingMonitoringMapper;
import org.ong.dryforest.service.plantingMonitoring.PlantingMonitoringService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/planting_monitoring")
@RequiredArgsConstructor
public class PlantingMonitoringController {

    private final PlantingMonitoringService plantingMonitoringService;

    @GetMapping
    public ResponseEntity<List<PlantingMonitoringDTO>> getAll() {

        List<PlantingMonitoringDTO> result =
                plantingMonitoringService.findAll()
                        .stream()
                        .map(PlantingMonitoringMapper::toMonitoringDTO)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PlantingMonitoringDTO> getById(
            @PathVariable int id
    ) {

        PlantingMonitoring plantingMonitoring =
                plantingMonitoringService.findById(id);

        PlantingMonitoringDTO result =
                PlantingMonitoringMapper.toMonitoringDTO(
                        plantingMonitoring
                );

        return ResponseEntity.ok(result);
    }


    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<PlantingMonitoringDTO> getByUuid(
            @PathVariable UUID uuid
    ) {

        PlantingMonitoring plantingMonitoring =
                plantingMonitoringService.findByUuid(uuid);

        PlantingMonitoringDTO result =
                PlantingMonitoringMapper.toMonitoringDTO(
                        plantingMonitoring
                );

        return ResponseEntity.ok(result);
    }


    @GetMapping("/speciesStat")
    public ResponseEntity<List<SpeciesStatDTO>>
    getSpeciesStat() {

        return ResponseEntity.ok(
                plantingMonitoringService
                        .statisticSpeciesAverageBySpeciesAndReforestationDate()
        );
    }


    @GetMapping(
            "/species/statistics/by-reforestation-date"
    )
    public ResponseEntity<List<SpeciesStatDTO>>
    getSpeciesStatisticsByReforestationDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {

        return ResponseEntity.ok(
                plantingMonitoringService
                        .statisticSpeciesAverageByReforestationDateRange(
                                startDate,
                                endDate
                        )
        );
    }


    @PostMapping
    public ResponseEntity<PlantingMonitoringDTO> create(
            @RequestBody PlantingMonitoringDTO dto
    ) {

        PlantingMonitoring saved =
                plantingMonitoringService.create(dto);

        PlantingMonitoringDTO result =
                PlantingMonitoringMapper.toMonitoringDTO(
                        saved
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PlantingMonitoringDTO> update(
            @PathVariable int id,
            @RequestBody PlantingMonitoringDTO dto
    ) {

        PlantingMonitoring existing =
                plantingMonitoringService.findById(id);


        if (dto.getUuid() != null) {

            existing.setUuid(
                    dto.getUuid()
            );
        }


        if (dto.getDate_planting_monitoring() != null) {

            existing.setDate_planting_monitoring(
                    dto.getDate_planting_monitoring()
            );
        }


        existing.setDiameter(
                dto.getDiameter()
        );

        existing.setHeight(
                dto.getHeight()
        );


        if (dto.getImage() != null &&
                !dto.getImage().isBlank()) {

            existing.setImage(
                    dto.getImage()
            );
        }


        existing.setAuto_generation(
                dto.isAuto_generation()
        );

        PlantingMonitoring updated =
                plantingMonitoringService
                        .updatePlantingMonitoring(
                                existing
                        );

        PlantingMonitoringDTO result =
                PlantingMonitoringMapper.toMonitoringDTO(
                        updated
                );

        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        PlantingMonitoring existing =
                plantingMonitoringService.findById(id);

        plantingMonitoringService
                .deletePlantingMonitoring(existing);

        return ResponseEntity.noContent().build();
    }
}