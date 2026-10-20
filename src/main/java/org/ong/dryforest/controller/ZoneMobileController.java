
package org.ong.dryforest.controller;

import org.ong.dryforest.dto.zone.ZoneMobileDTO;
import org.ong.dryforest.entity.Zone;
import org.ong.dryforest.mapper.ZoneMapper;
import org.ong.dryforest.service.zone.ZoneService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mobile/zone")
public class ZoneMobileController {

    private final ZoneService zoneService;

    public ZoneMobileController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping
    public ResponseEntity<Page<ZoneMobileDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<ZoneMobileDTO> zones = zoneService.findAll(pageable)
                .map(ZoneMapper::toZoneMobileDTO);

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
