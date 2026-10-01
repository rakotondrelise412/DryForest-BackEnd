package org.ong.dryforest.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.zone.ZoneDTO;
import org.ong.dryforest.dto.zone.ZoneWebDTO;
import org.ong.dryforest.entity.Zone;
import org.ong.dryforest.repository.ZoneRepository;
import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/zone")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private ZoneRepository zoneRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public ResponseEntity<List<ZoneWebDTO>> findAll() {

        List<Object[]> rows =
                zoneRepository.findAllWithGeomAsGeoJson();

        List<ZoneWebDTO> zones = rows.stream()
                .map(r -> {

                    ZoneWebDTO dto = new ZoneWebDTO();

                    dto.setId_zone(
                            ((Number) r[0]).intValue()
                    );

                    dto.setName(
                            (String) r[1]
                    );

                    dto.setArea(
                            r[2] != null
                                    ? ((Number) r[2]).doubleValue()
                                    : 0.0
                    );

                    String geoJsonStr =
                            r[3] == null
                                    ? null
                                    : r[3].toString();

                    if (geoJsonStr != null) {

                        try {

                            Map<String, Object> geom =
                                    objectMapper.readValue(
                                            geoJsonStr,
                                            new TypeReference<Map<String, Object>>() {}
                                    );

                            dto.setGeom(geom);

                        } catch (Exception ex) {

                            Map<String, Object> geom =
                                    new HashMap<>();

                            geom.put(
                                    "type",
                                    "Polygon"
                            );

                            geom.put(
                                    "coordinates",
                                    Collections.emptyList()
                            );

                            dto.setGeom(geom);
                        }

                    } else {

                        dto.setGeom(null);
                    }

                    dto.setId_type_zone(
                            ((Number) r[4]).intValue()
                    );

                    return dto;

                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(zones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneWebDTO> findById(
            @PathVariable int id) {

        Zone zone = zoneService.findById(id);

        ZoneWebDTO dto = new ZoneWebDTO();

        dto.setId_zone(zone.getId());
        dto.setName(zone.getName());
        dto.setArea(zone.getArea());

        if (zone.getTypeZone() != null) {
            dto.setId_type_zone(
                    zone.getTypeZone().getId()
            );
        }

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<ZoneWebDTO> findByUuid(
            @PathVariable UUID uuid) {

        Zone zone = zoneService.findByUuid(uuid);

        ZoneWebDTO dto = new ZoneWebDTO();

        dto.setId_zone(zone.getId());
        dto.setName(zone.getName());
        dto.setArea(zone.getArea());

        if (zone.getTypeZone() != null) {
            dto.setId_type_zone(zone.getTypeZone().getId());
        }

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Zone> create(
            @RequestBody ZoneDTO zoneDTO) {

        Zone zone =
                zoneService.mapToEntity(zoneDTO);

        return ResponseEntity.ok(
                zoneService.createZone(zone)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<Zone> update(
            @PathVariable int id,
            @RequestBody ZoneDTO zoneDTO) {

        Zone existing =
                zoneService.findById(id);

        existing.setName(
                zoneDTO.getName()
        );

        existing.setArea(
                zoneDTO.getArea()
        );

        existing.set_synced(
                zoneDTO.isIs_synced()
        );

        if (zoneDTO.getUuid() != null) {
            existing.setUuid(
                    zoneDTO.getUuid()
            );
        }

        if (zoneDTO.getId_type_zone() > 0) {

            existing.setTypeZone(
                    zoneService
                            .mapToEntity(zoneDTO)
                            .getTypeZone()
            );
        }

        return ResponseEntity.ok(
                zoneService.updateZone(existing)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        Zone zone =
                zoneService.findById(id);

        zoneService.deleteZone(zone);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/totalAreaProtected")
    public ResponseEntity<Double> getTotalAreaProtected() {

        return ResponseEntity.ok(
                zoneService.totalAreaProtected()
        );
    }
}