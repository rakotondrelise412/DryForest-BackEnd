package org.ong.dryforest.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.plantation.*;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockSurvivalRateDTO;
import org.ong.dryforest.dto.species.SpeciesCarbonDTO;
import org.ong.dryforest.entity.Plantation;
import org.ong.dryforest.mapper.PlantationMapper;
import org.ong.dryforest.service.plantation.PlantationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping
    public ResponseEntity<List<PlantationMobileDTO>> findAll() {

        List<PlantationMobileDTO> result =
                plantationService.findAll()
                        .stream()
                        .map(
                                PlantationMapper
                                        ::toPlantationMobileDTO
                        )
                        .toList();

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

    // ============================================================
    // VIEWS
    // ============================================================

    @GetMapping("/plantationsByPlantationBlock")
    public ResponseEntity<List<PlantationViewDTO>>
    getAllByPlantationBlock() {

        return ResponseEntity.ok(
                plantationService.getAllPlantations()
        );
    }

    @GetMapping("/plantationsByCriteria")
    public ResponseEntity<List<PlantationViewDTO>>
    getAllByCriteria(

            @RequestParam(
                    name = "id_plantation_block",
                    required = false
            )
            Integer idPlantationBlock,

            @RequestParam(
                    name = "id_sub_plot",
                    required = false
            )
            Integer idSubPlot,

            @RequestParam(
                    name = "id_species",
                    required = false
            )
            Integer idSpecies,

            @RequestParam(
                    name = "date_plantation",
                    required = false
            )
            String datePlantation
    ) {

        Date sqlDate = null;

        if (datePlantation != null &&
                !datePlantation.isBlank()) {

            try {

                sqlDate =
                        Date.valueOf(datePlantation);

            } catch (IllegalArgumentException e) {

                return ResponseEntity
                        .badRequest()
                        .body(List.of());
            }
        }

        return ResponseEntity.ok(
                plantationService.getPlantationsByCriteria(
                        idPlantationBlock,
                        idSubPlot,
                        idSpecies,
                        sqlDate
                )
        );
    }

    @GetMapping("/plantationsByPlantationBlockById/{blockId}")
    public ResponseEntity<List<PlantationViewDTO>>
    getAllByIdBlock(
            @PathVariable int blockId
    ) {

        return ResponseEntity.ok(
                plantationService
                        .getPlantationsByIdPlantationBlock(
                                blockId
                        )
        );
    }

    @GetMapping("/plantationsByPlantationBlock/{blockId}/subPlot/{subPlotId}")
    public ResponseEntity<List<PlantationViewDTO>>
    getByBlockAndSubPlot(
            @PathVariable int blockId,
            @PathVariable int subPlotId
    ) {

        return ResponseEntity.ok(
                plantationService
                        .getPlantationsByBlockAndSubPlot(
                                blockId,
                                subPlotId
                        )
        );
    }


    @PostMapping
    public ResponseEntity<PlantationMobileDTO> create(
            @RequestBody PlantationDTO plantationDTO
    ) {

        Plantation saved =
                plantationService.create(
                        plantationDTO
                );

        PlantationMobileDTO result =
                PlantationMapper.toPlantationMobileDTO(
                        saved
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PlantationMobileDTO> update(
            @PathVariable int id,
            @RequestBody PlantationDTO plantationDTO
    ) {

        Plantation updated =
                plantationService.update(
                        id,
                        plantationDTO
                );

        PlantationMobileDTO result =
                PlantationMapper.toPlantationMobileDTO(
                        updated
                );

        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        plantationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // STATISTICS
    // ============================================================

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
    public ResponseEntity<
            List<PlantationBlockSurvivalRateDTO>
            >
    getSurvivalRateByBlockSubPlotAndSpecies() {

        return ResponseEntity.ok(
                plantationService
                        .getSurvivalRateBySpeciesBySubPlotAndBlock()
        );
    }
}