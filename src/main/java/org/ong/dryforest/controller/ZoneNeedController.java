package org.ong.dryforest.controller;

import java.util.UUID;

import org.ong.dryforest.dto.zone.ZoneNeedDTO;
import org.ong.dryforest.entity.Zone;
import org.ong.dryforest.entity.ZoneNeed;
import org.ong.dryforest.mapper.ZoneNeedMapper;
import org.ong.dryforest.service.zone.ZoneNeedService;

import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/zone-needs")
@CrossOrigin
public class ZoneNeedController {

    @Autowired
    private ZoneNeedService zoneNeedService;

    @Autowired
    private ZoneNeedMapper zoneNeedMapper;

    @Autowired
    private ZoneService zoneService;

    @PostMapping
    public ResponseEntity<ZoneNeedDTO> create(
            @RequestBody ZoneNeedDTO dto) {

        ZoneNeed zoneNeed = new ZoneNeed();

        zoneNeed.setUuid(
                dto.getUuid() != null
                        ? UUID.fromString(dto.getUuid())
                        : UUID.randomUUID()
        );

        Zone zone = zoneService.findById(dto.getId_zone());

        zoneNeed.setZone(zone);

        ZoneNeed created =
                zoneNeedService.createZoneNeed(zoneNeed);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(zoneNeedMapper.toDTO(created));
    }


    @GetMapping
    public ResponseEntity<Page<ZoneNeedDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<ZoneNeedDTO> result =
                zoneNeedService.findAll(pageable)
                        .map(zoneNeedMapper::toDTO);

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
            @RequestBody ZoneNeedDTO dto) {

        ZoneNeed existing = zoneNeedService.findById(id);
        if (dto.getUuid() != null) {
            existing.setUuid(UUID.fromString(dto.getUuid()));
        }
        Zone zone = zoneService.findById(dto.getId_zone());
        existing.setZone(zone);
        ZoneNeed updated =
                zoneNeedService.updatZoneNeed(existing);

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