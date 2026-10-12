package org.ong.dryforest.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.zone.ZoneDTO;
import org.ong.dryforest.dto.zone.ZoneWebDTO;
import org.ong.dryforest.entity.Zone;
import org.ong.dryforest.mapper.ZoneMapper;
import org.ong.dryforest.repository.ZoneRepository;
import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/zone")
public class ZoneWebController {

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
                    if (r[1] != null) {
                        dto.setUuid(
                                UUID.fromString(r[1].toString())
                        );
                    }
                    dto.setName((String) r[2]);
                    dto.setArea(
                            r[3] != null
                                    ? ((Number) r[3]).doubleValue()
                                    : 0.0
                    );
                    if (r[4] != null) {
                        try {
                            Map<String, Object> geom =
                                    objectMapper.readValue(
                                            r[4].toString(),
                                            new TypeReference<>() {
                                            }
                                    );

                            dto.setGeom(geom);

                        } catch (Exception e) {
                            throw new IllegalStateException(
                                    "Erreur lors de la conversion de la géométrie GeoJSON",
                                    e
                            );
                        }
                    }
                    if (r[5] != null) {
                        dto.setId_type_zone(
                                ((Number) r[5]).intValue()
                        );
                    }
                    dto.set_synced(
                            r[6] != null
                                    && Boolean.parseBoolean(r[6].toString())
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

        return ResponseEntity.ok(
                ZoneMapper.toZoneWebDTO(zone)
        );
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<ZoneWebDTO> findByUuid(
            @PathVariable UUID uuid) {

        Zone zone = zoneService.findByUuid(uuid);

        return ResponseEntity.ok(
                ZoneMapper.toZoneWebDTO(zone)
        );
    }


    @PostMapping
    public ResponseEntity<ZoneWebDTO> create(
            @RequestBody ZoneDTO zoneDTO) {

        Zone zone = zoneService.mapToEntity(zoneDTO);

        Zone savedZone = zoneService.createZone(zone);

        ZoneWebDTO response = ZoneMapper.toZoneWebDTO(savedZone);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneWebDTO> update(
            @PathVariable int id,
            @RequestBody ZoneDTO zoneDTO) {

        Zone existing = zoneService.findById(id);

        existing.setName(zoneDTO.getName());
        existing.setArea(zoneDTO.getArea());
        existing.set_synced(zoneDTO.is_synced());

        if (zoneDTO.getUuid() != null) {
            existing.setUuid(zoneDTO.getUuid());
        }

        if (zoneDTO.getGeom() != null) {
            Zone mappedZone = zoneService.mapToEntity(zoneDTO);

            existing.setGeom(mappedZone.getGeom());
        }

        if (zoneDTO.getId_type_zone() > 0) {
            Zone mappedZone = zoneService.mapToEntity(zoneDTO);
            existing.setTypeZone(mappedZone.getTypeZone());
        }

        Zone updatedZone = zoneService.updateZone(existing);

        ZoneWebDTO response = ZoneMapper.toZoneWebDTO(updatedZone);

        return ResponseEntity.ok(response);
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