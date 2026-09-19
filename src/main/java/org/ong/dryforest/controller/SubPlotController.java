package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.subPlot.SubPlotDTO;
import org.ong.dryforest.dto.subPlot.SubPlotFilterWebDTO;
import org.ong.dryforest.dto.subPlot.SubPlotMobileDTO;
import org.ong.dryforest.entity.SubPlot;
import org.ong.dryforest.mapper.SubPlotMapper;
import org.ong.dryforest.service.subPlot.SubPlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/subplot")
public class SubPlotController {
    
    @Autowired
    private SubPlotService subPlotService;

    @GetMapping
    public ResponseEntity<List<SubPlotMobileDTO>> findAll() {
        List<SubPlotMobileDTO> subPlotMobileDTO = new ArrayList<>();
        List<SubPlot> subPlots = subPlotService.findAll();

        if (subPlots != null && !subPlots.isEmpty()) {
            subPlotMobileDTO = subPlotService.findAll().stream().map(SubPlotMapper::toSubPlotMobileDTO).collect(Collectors.toList());
        }

        return ResponseEntity.ok(subPlotMobileDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubPlotDTO>> getAllSubPlot(){
        List<SubPlot> subPlots = subPlotService.findAll();
        List<SubPlotDTO> subPlotDTO = subPlots == null ? new ArrayList<>() : subPlots.stream().map(SubPlotMapper::toSubPlotDTO).collect(Collectors.toList());

        return ResponseEntity.ok(subPlotDTO);
    }

    @GetMapping("/subPlotWebFilter")
    public ResponseEntity<List<SubPlotFilterWebDTO>> getAll(){
        List<SubPlotFilterWebDTO> subPlotFilterWebDTOs = new ArrayList<>();
        List<SubPlot> subPlots = subPlotService.findAll();

        if (subPlots != null && ! subPlots.isEmpty()) {
            subPlotFilterWebDTOs = subPlotService.findAll().stream().map(SubPlotMapper::toWebFilterDTO).collect(Collectors.toList());
        }

        return ResponseEntity.ok(subPlotFilterWebDTOs);
    }

    @PutMapping("/updateSubPloty")
    public ResponseEntity<SubPlot> updateLocation(@RequestBody SubPlotDTO subPlotDTO) throws Exception {
        SubPlot updated = subPlotService.updateSubPlotLocation(subPlotDTO);
        return ResponseEntity.ok(updated);
    }
}
