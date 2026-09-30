package org.ong.dryforest.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.severity.SeverityDTO;
import org.ong.dryforest.service.severity.SeverityService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/severities")
@RequiredArgsConstructor
public class SeverityController {

    private final SeverityService severityService;

    @GetMapping
    public ResponseEntity<List<SeverityDTO>> getAllSeverities() {

        return ResponseEntity.ok(
                severityService.findAllSeverities()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<SeverityDTO> getSeverityById(
            @PathVariable int id
    ) {

        return ResponseEntity.ok(
                severityService.findSeverityById(id)
        );
    }

    @GetMapping("/sync")
    public ResponseEntity<List<SeverityDTO>> sync(
            @RequestParam("last_sync")
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime lastSync) {

        return ResponseEntity.ok(
                severityService.findAllSeveritiesUpdatedSince(
                        lastSync
                )
        );
    }


    @PostMapping
    public ResponseEntity<SeverityDTO> createSeverity(
            @RequestBody SeverityDTO dto
    ) {

        SeverityDTO created =
                severityService.createSeverity(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SeverityDTO> updateSeverity(
            @PathVariable int id,
            @RequestBody SeverityDTO dto
    ) {

        SeverityDTO updated =
                severityService.updateSeverity(
                        id,
                        dto
                );

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeverity(
            @PathVariable int id
    ) {

        severityService.deleteSeverity(id);

        return ResponseEntity.noContent().build();
    }
}