package org.ong.dryforest.controller;

import org.ong.dryforest.dto.zone.ZoneMobileDTO;
import org.ong.dryforest.entity.Zone;
import org.ong.dryforest.mapper.ZoneMapper;
import org.ong.dryforest.repository.ZoneRepository;
import org.ong.dryforest.service.zone.ZoneService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mobile/zone")
public class ZoneMobileController {

    private final ZoneService zoneService;
    private final ZoneRepository zoneRepository;

    public ZoneMobileController(
            ZoneService zoneService,
            ZoneRepository zoneRepository) {
        this.zoneService = zoneService;
        this.zoneRepository = zoneRepository;
    }

    @GetMapping
    public ResponseEntity<List<ZoneMobileDTO>> findAll() {

        List<ZoneMobileDTO> zones = zoneRepository.findAll()
                .stream()
                .map(ZoneMapper::toZoneMobileDTO)
                .toList();

        return ResponseEntity.ok(zones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneMobileDTO> findById(
            @PathVariable int id) {

        Zone zone = zoneService.findById(id);

        return ResponseEntity.ok(
                ZoneMapper.toZoneMobileDTO(zone)
        );
    }
}

