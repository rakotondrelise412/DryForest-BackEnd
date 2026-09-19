package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.plantation.PlantationDTO;
import org.ong.dryforest.dto.plantation.PlantationMobileDTO;
import org.ong.dryforest.dto.plantation.PlantationStatusByYearDTO;
import org.ong.dryforest.dto.plantation.PlantationViewDTO;
import org.ong.dryforest.dto.plantation.SurvivalRateDTO;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockSurvivalRateDTO;
import org.ong.dryforest.dto.species.SpeciesCarbonDTO;
import org.ong.dryforest.entity.Plantation;
import org.ong.dryforest.mapper.PlantationMapper;
import org.ong.dryforest.service.plantation.PlantationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/plantation")
public class PlantationController {

    @Autowired
    private PlantationService plantationService;

    @GetMapping
    public ResponseEntity<List<PlantationMobileDTO>> findAll(){
        List<PlantationMobileDTO> plantationMobileDTO = new ArrayList<>();
        List<Plantation> plantations = plantationService.findAll();

        if (plantations != null && !plantations.isEmpty()) {
            plantationMobileDTO = plantationService.findAll().stream().map(PlantationMapper::toPlantationMobileDTO).collect(Collectors.toList());
        }

        return ResponseEntity.ok(plantationMobileDTO);
    }

    @GetMapping("/plantationsByPlantationBlock")
    public ResponseEntity<List<PlantationViewDTO>> getAllByPlantationBlock(){
        List<PlantationViewDTO> plantations = plantationService.getAllPlantations();
        return ResponseEntity.ok(plantations);
    }

    @GetMapping("/plantationsByCriteria")
    public ResponseEntity<List<PlantationViewDTO>> getAllByCriteria(
        @RequestParam(name = "id_plantation_block", required = false) Integer id_plantation_block,
        @RequestParam(name = "id_sub_plot", required = false) Integer id_sub_plot,
        @RequestParam(name = "id_species", required = false) Integer id_species,
        @RequestParam(name = "date_plantation", required = false) String date_plantation
    ){
        java.sql.Date sqlDate = null;
        if (date_plantation != null && !date_plantation.isBlank()) {
            try {
                // java.sql.Date.valueOf attend "yyyy-[m]m-[d]d"
                sqlDate = java.sql.Date.valueOf(date_plantation);
            } catch (IllegalArgumentException ex) {
                // format invalide : renvoyer 400 plutôt qu'une 500 incompréhensible
                return ResponseEntity.badRequest()
                    .body(List.of()); // ou ResponseEntity.badRequest().build();
            }
        }
        List<PlantationViewDTO> plantations = plantationService.getPlantationsByCriteria(id_plantation_block, id_sub_plot, id_species, sqlDate);

        return ResponseEntity.ok(plantations);
    }

    @GetMapping("/plantationsByPlantationBlockById/{blockId}")
    public ResponseEntity<List<PlantationViewDTO>> getAllByIdBlock(@PathVariable int blockId) {
        return ResponseEntity.ok(plantationService.getPlantationsByIdPlantationBlock(blockId));
    }

    @GetMapping("/by_year_status")
    public ResponseEntity<List<PlantationStatusByYearDTO>> getByYearStatus() {
        List<PlantationStatusByYearDTO> result = plantationService.plantationStatusByYear();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/totalPlantationPerBlock")
    public ResponseEntity<List<Map<String, Integer>>> getTotalPlantationPerBlock(){
        List<Map<String, Integer>> plantatations = plantationService.getTotalPlantationByBlock();
        return ResponseEntity.ok(plantatations);
    }

    @GetMapping("/carbon_by_species")
    public ResponseEntity<List<SpeciesCarbonDTO>> getCarbonBySpecies() {
        List<SpeciesCarbonDTO> data = plantationService.getCarbonSequesteredBySpeciesNative();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/survival_rate")
    public ResponseEntity<List<SurvivalRateDTO>> getSurvivalRate() {
        List<SurvivalRateDTO> survivalRate = plantationService.survivalRateByYear();
        return ResponseEntity.ok(survivalRate);
    }

    @GetMapping("/survival_global")
    public ResponseEntity<SurvivalRateDTO> getSurvivalGlobal() {
        SurvivalRateDTO dto = plantationService.survivalRateGlobal();
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/survival_rate_by_block_subplot_species")
    public ResponseEntity<List<PlantationBlockSurvivalRateDTO>> getSurvivalRateByBlockSubPlotAndSpecies() {
        List<PlantationBlockSurvivalRateDTO> result = plantationService.getSurvivalRateBySpeciesBySubPlotAndBlock();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Plantation> create(@RequestBody PlantationDTO plantationDTO) {
        Plantation saved = plantationService.create(plantationDTO);
        return ResponseEntity.status(201).body(saved);
    }
}
