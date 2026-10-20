package org.ong.dryforest.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.PlantingMonitoringDTO;
import org.ong.dryforest.dto.species.SpeciesStatDTO;
import org.ong.dryforest.entity.PlantingMonitoring;
import org.ong.dryforest.mapper.PlantingMonitoringMapper;
import org.ong.dryforest.service.ImageStorageService;
import org.ong.dryforest.service.plantingMonitoring.PlantingMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/planting_monitoring")
@RequiredArgsConstructor
public class PlantingMonitoringController {

    private final PlantingMonitoringService plantingMonitoringService;

    @Autowired
    private ImageStorageService imageStorageService;
    @GetMapping
    public ResponseEntity<Page<PlantingMonitoringDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<PlantingMonitoringDTO> result =
                plantingMonitoringService.findAll(pageable)
                        .map(PlantingMonitoringMapper::toMonitoringDTO);

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



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlantingMonitoringDTO> create(
            @RequestPart("plantingMonitoring") String plantingMonitoringJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules();

        PlantingMonitoringDTO dto = objectMapper.readValue(
                plantingMonitoringJson,
                PlantingMonitoringDTO.class
        );

        if (image != null && !image.isEmpty()) {
            String imagePath = imageStorageService.store(image);
            dto.setImage(imagePath);
        }

        PlantingMonitoring saved =
                plantingMonitoringService.create(dto);

        PlantingMonitoringDTO result =
                PlantingMonitoringMapper.toMonitoringDTO(saved);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }



    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlantingMonitoringDTO> update(
            @PathVariable int id,
            @RequestPart("plantingMonitoring") String plantingMonitoringJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {

        PlantingMonitoring existing =
                plantingMonitoringService.findById(id);

        ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules();

        PlantingMonitoringDTO dto = objectMapper.readValue(
                plantingMonitoringJson,
                PlantingMonitoringDTO.class
        );

        if (image != null && !image.isEmpty()) {
            String imagePath = imageStorageService.store(image);
            dto.setImage(imagePath);
        } else {
            dto.setImage(existing.getImage());
        }

        if (dto.getUuid() != null) {
            existing.setUuid(dto.getUuid());
        }

        if (dto.getDate_planting_monitoring() != null) {
            existing.setDate_planting_monitoring(
                    dto.getDate_planting_monitoring()
            );
        }

        existing.setDiameter(dto.getDiameter());
        existing.setHeight(dto.getHeight());

        existing.setImage(dto.getImage());

        existing.setAuto_generation(
                dto.isAuto_generation()
        );

        PlantingMonitoring updated =
                plantingMonitoringService.updatePlantingMonitoring(
                        existing
                );

        PlantingMonitoringDTO result =
                PlantingMonitoringMapper.toMonitoringDTO(updated);

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