package org.ong.dryforest.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.subPlot.SubPlotDTO;
import org.ong.dryforest.dto.subPlot.SubPlotFilterWebDTO;
import org.ong.dryforest.dto.subPlot.SubPlotMobileDTO;
import org.ong.dryforest.entity.SubPlot;
import org.ong.dryforest.mapper.SubPlotMapper;
import org.ong.dryforest.service.subPlot.SubPlotService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/subplot")
@RequiredArgsConstructor
public class SubPlotController {

    private final SubPlotService subPlotService;


    @GetMapping
    public ResponseEntity<Page<SubPlotMobileDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<SubPlotMobileDTO> result =
                subPlotService.findAll(pageable)
                        .map(SubPlotMapper::toSubPlotMobileDTO);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<SubPlotDTO>> getAllSubPlot(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<SubPlotDTO> result = subPlotService
                .findAll(pageable)
                .map(SubPlotMapper::toSubPlotDTO);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/subPlotWebFilter")
    public ResponseEntity<Page<SubPlotFilterWebDTO>> getAllWebFilter(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<SubPlotFilterWebDTO> result = subPlotService
                .findAll(pageable)
                .map(SubPlotMapper::toWebFilterDTO);

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SubPlotDTO> findById(
            @PathVariable int id) {

        SubPlot subPlot =
                subPlotService.findById(id);

        return ResponseEntity.ok(
                SubPlotMapper.toSubPlotDTO(
                        subPlot
                )
        );
    }



    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<SubPlotDTO> findByUuid(
            @PathVariable UUID uuid) {

        SubPlot subPlot =
                subPlotService.findByUuid(uuid);

        return ResponseEntity.ok(
                SubPlotMapper.toSubPlotDTO(
                        subPlot
                )
        );
    }


    @PostMapping
    public ResponseEntity<SubPlotDTO> create(
            @RequestBody SubPlotDTO dto) {

        SubPlot created =
                subPlotService.createSubPlot(
                        dto
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        SubPlotMapper.toSubPlotDTO(
                                created
                        )
                );
    }



    @PutMapping("/{id}")
    public ResponseEntity<SubPlotDTO> update(
            @PathVariable int id,
            @RequestBody SubPlotDTO dto) {

        SubPlot updated =
                subPlotService.updateSubPlot(
                        id,
                        dto
                );

        return ResponseEntity.ok(
                SubPlotMapper.toSubPlotDTO(
                        updated
                )
        );
    }


    @PutMapping("/updateSubPloty")
    public ResponseEntity<SubPlotDTO> updateLocation(
            @RequestBody SubPlotDTO subPlotDTO)
            throws Exception {

        SubPlot updated =
                subPlotService.updateSubPlotLocation(
                        subPlotDTO
                );

        return ResponseEntity.ok(
                SubPlotMapper.toSubPlotDTO(
                        updated
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        subPlotService.deleteSubPlot(id);

        return ResponseEntity.noContent()
                .build();
    }
}