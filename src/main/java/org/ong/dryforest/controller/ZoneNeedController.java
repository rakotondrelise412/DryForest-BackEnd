package org.ong.dryforest.controller;

import java.util.List;
import java.util.UUID;

import org.ong.dryforest.dto.zone.ZoneNeedDTO;
import org.ong.dryforest.entity.ZoneNeed;
import org.ong.dryforest.mapper.ZoneNeedMapper;
import org.ong.dryforest.service.zone.ZoneNeedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zone-needs")
@CrossOrigin
public class ZoneNeedController {

    @Autowired
    private ZoneNeedService zoneNeedService;

    @Autowired
    private ZoneNeedMapper zoneNeedMapper;

    @PostMapping
    public ResponseEntity<ZoneNeedDTO> create(
            @RequestBody ZoneNeed zoneNeed) {

        ZoneNeed created =
                zoneNeedService.createZoneNeed(zoneNeed);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(zoneNeedMapper.toDTO(created));
    }

    @GetMapping
    public ResponseEntity<List<ZoneNeedDTO>> findAll() {

        List<ZoneNeedDTO> result =
                zoneNeedService.findAll()
                        .stream()
                        .map(zoneNeedMapper::toDTO)
                        .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneNeedDTO> findById(
            @PathVariable int id) {

        ZoneNeed zoneNeed =
                zoneNeedService.findById(id);

        return ResponseEntity.ok(
                zoneNeedMapper.toDTO(zoneNeed)
        );
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<ZoneNeedDTO> findByUuid(
            @PathVariable UUID uuid) {

        ZoneNeed zoneNeed =
                zoneNeedService.findByUuid(uuid);

        return ResponseEntity.ok(
                zoneNeedMapper.toDTO(zoneNeed)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneNeedDTO> update(
            @PathVariable int id,
            @RequestBody ZoneNeed zoneNeed) {

        zoneNeed.setId(id);

        ZoneNeed updated =
                zoneNeedService.updatZoneNeed(zoneNeed);

        return ResponseEntity.ok(
                zoneNeedMapper.toDTO(updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        ZoneNeed zoneNeed =
                zoneNeedService.findById(id);

        zoneNeedService.deleteZoneNeed(zoneNeed);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{uuid}")
    public ResponseEntity<Boolean> existsByUuid(
            @PathVariable UUID uuid) {

        return ResponseEntity.ok(
                zoneNeedService.existsByUuid(uuid)
        );
    }
}