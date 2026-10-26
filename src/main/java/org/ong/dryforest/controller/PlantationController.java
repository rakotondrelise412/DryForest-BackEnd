package org.ong.dryforest.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.ong.dryforest.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.plantation.*;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockSurvivalRateDTO;
import org.ong.dryforest.dto.species.SpeciesCarbonDTO;
import org.ong.dryforest.entity.Plantation;
import org.ong.dryforest.mapper.PlantationMapper;
import org.ong.dryforest.service.plantation.PlantationService;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@RestController
@RequestMapping("/api/plantation")
@RequiredArgsConstructor
public class PlantationController {

    private final PlantationService plantationService;

    @Autowired
    private ImageStorageService imageStorageService;


    private Pageable createPageable(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException(
                    "Le numéro de page doit être supérieur ou égal à 0."
            );
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "La taille de page doit être comprise entre 1 et 100."
            );
        }

        return PageRequest.of(page, size);
    }


    @GetMapping
    public ResponseEntity<Page<PlantationMobileDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<PlantationMobileDTO> result =
                plantationService.findAll(pageable)
                        .map(PlantationMapper::toPlantationMobileDTO);

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PlantationMobileDTO> findById(
            @PathVariable int id
    ) {

        Plantation plantation =
                plantationService.findById(id);

        PlantationMobileDTO result =
                PlantationMapper.toPlantationMobileDTO(
                        plantation
                );

        return ResponseEntity.ok(result);
    }


    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<PlantationMobileDTO> findByUuid(
            @PathVariable UUID uuid
    ) {

        Plantation plantation =
                plantationService.findByUuid(uuid);

        PlantationMobileDTO result =
                PlantationMapper.toPlantationMobileDTO(
                        plantation
                );

        return ResponseEntity.ok(result);
    }


    @GetMapping("/plantationsByPlantationBlock")
    public ResponseEntity<Page<PlantationViewDTO>> getAllByPlantationBlock(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                plantationService.getAllPlantations(
                        createPageable(page, size)
                )
        );
    }

    @GetMapping("/plantationsByCriteria")
    public ResponseEntity<Page<PlantationViewDTO>> getAllByCriteria(
            @RequestParam(name = "id_plantation_block", required = false)
            Integer idPlantationBlock,

            @RequestParam(name = "id_sub_plot", required = false)
            Integer idSubPlot,

            @RequestParam(name = "id_species", required = false)
            Integer idSpecies,

            @RequestParam(name = "date_plantation", required = false)
            String datePlantation,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Date sqlDate = null;

        if (datePlantation != null && !datePlantation.isBlank()) {
            try {
                sqlDate = Date.valueOf(datePlantation);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        return ResponseEntity.ok(
                plantationService.getPlantationsByCriteria(
                        idPlantationBlock,
                        idSubPlot,
                        idSpecies,
                        sqlDate,
                        createPageable(page, size)
                )
        );
    }

    @GetMapping("/plantationsByPlantationBlockById/{blockId}")
    public ResponseEntity<Page<PlantationViewDTO>> getAllByIdBlock(
            @PathVariable int blockId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                plantationService.getPlantationsByIdPlantationBlock(
                        blockId,
                        createPageable(page, size)
                )
        );
    }

    @GetMapping("/plantationsByPlantationBlock/{blockId}/subPlot/{subPlotId}")
    public ResponseEntity<Page<PlantationViewDTO>> getByBlockAndSubPlot(
            @PathVariable int blockId,
            @PathVariable int subPlotId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                plantationService.getPlantationsByBlockAndSubPlot(
                        blockId,
                        subPlotId,
                        createPageable(page, size)
                )
        );
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlantationMobileDTO> create(
            @RequestPart("plantation") String plantationJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules();

        PlantationDTO dto = objectMapper.readValue(
                plantationJson,
                PlantationDTO.class
        );

        if (image != null && !image.isEmpty()) {
            String imagePath = imageStorageService.store(image);
            dto.setImage(imagePath);
        }

        Plantation saved = plantationService.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PlantationMapper.toPlantationMobileDTO(saved));
    }



    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlantationMobileDTO> update(
            @PathVariable int id,
            @RequestPart("plantation") String plantationJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {

        Plantation existing = plantationService.findById(id);

        ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules();

        PlantationDTO dto = objectMapper.readValue(
                plantationJson,
                PlantationDTO.class
        );

        if (image != null && !image.isEmpty()) {
            String imagePath = imageStorageService.store(image);
            dto.setImage(imagePath);
        } else {
            dto.setImage(existing.getImage());
        }

        Plantation updated = plantationService.update(id, dto);

        return ResponseEntity.ok(
                PlantationMapper.toPlantationMobileDTO(updated)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        plantationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/by_year_status")
    public ResponseEntity<List<PlantationStatusByYearDTO>>
    getByYearStatus() {

        return ResponseEntity.ok(
                plantationService.plantationStatusByYear()
        );
    }

    @GetMapping("/totalPlantationPerBlock")
    public ResponseEntity<List<Map<String, Integer>>>
    getTotalPlantationPerBlock() {

        return ResponseEntity.ok(
                plantationService
                        .getTotalPlantationByBlock()
        );
    }

    @GetMapping("/carbon_by_species")
    public ResponseEntity<List<SpeciesCarbonDTO>>
    getCarbonBySpecies() {

        return ResponseEntity.ok(
                plantationService
                        .getCarbonSequesteredBySpeciesNative()
        );
    }

    @GetMapping("/survival_rate")
    public ResponseEntity<List<SurvivalRateDTO>>
    getSurvivalRate() {

        return ResponseEntity.ok(
                plantationService
                        .survivalRateByYear()
        );
    }

    @GetMapping("/survival_global")
    public ResponseEntity<SurvivalRateDTO>
    getSurvivalGlobal() {

        return ResponseEntity.ok(
                plantationService
                        .survivalRateGlobal()
        );
    }

    @GetMapping("/survival_rate_by_block_subplot_species")
    public ResponseEntity<Page<PlantationBlockSurvivalRateDTO>>
    getSurvivalRateByBlockSubPlotAndSpecies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "La page doit être positive et la taille comprise entre 1 et 100."
            );
        }

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                plantationService
                        .getSurvivalRateBySpeciesBySubPlotAndBlock(pageable)
        );
    }
}