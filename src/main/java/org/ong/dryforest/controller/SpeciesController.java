package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.species.SpeciesStatDTO;
import org.ong.dryforest.dto.species.SpeciesWebDTO;
import org.ong.dryforest.entity.Species;
import org.ong.dryforest.mapper.SpeciesMapper;
import org.ong.dryforest.service.species.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/species")
public class SpeciesController {
    @Autowired
    private SpeciesService speciesService;

    @GetMapping
    public ResponseEntity<List<SpeciesWebDTO>> getAll(){
        List<SpeciesWebDTO> speciesWebDTO = new ArrayList<>();
        List<Species> species = speciesService.findAllSpecies();

        if (species != null && !species.isEmpty()) {
            speciesWebDTO = speciesService.findAllSpecies().stream().map(SpeciesMapper::toWebDTO).collect(Collectors.toList());
        }

        return ResponseEntity.ok(speciesWebDTO);
    }

}
