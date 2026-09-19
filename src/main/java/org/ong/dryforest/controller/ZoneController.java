package org.ong.dryforest.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.zone.ZoneWebDTO;
import org.ong.dryforest.repository.ZoneRepository;
import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/api/zone")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;
    
    @Autowired
    private ZoneRepository zoneRepository;

    // @GetMapping
    // public ResponseEntity<List<ZoneMobileDTO>> findAll(){
    //     List<ZoneMobileDTO> zoneMobileDTO = new ArrayList<>();
    //     List<Zone> zones = zoneService.findAll();

    //     if (zones != null && !zones.isEmpty()) {
    //         zoneMobileDTO = zoneService.findAll().stream().map(ZoneMapper::toZoneMobileDTO).collect(Collectors.toList());
    //     }

    //     return ResponseEntity.ok(zoneMobileDTO);
    // }


    @GetMapping
    public ResponseEntity<List<ZoneWebDTO>> findAll() {
        List<Object[]> rows = zoneRepository.findAllWithGeomAsGeoJson();
        List<ZoneWebDTO> zones = rows.stream().map(r -> {
            ZoneWebDTO dto = new ZoneWebDTO();
            dto.setId_zone(((Number) r[0]).intValue());
            dto.setName((String) r[1]);
            dto.setArea(r[2] != null ? ((Number) r[2]).doubleValue() : 0.0);
            dto.setId_type_zone(((Number) r[4]).intValue());

            String geoJsonStr = r[3] == null ? null : r[3].toString();
            if (geoJsonStr != null) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    // parse GeoJSON string to Map
                    Map<String,Object> geom = mapper.readValue(geoJsonStr, new TypeReference<Map<String,Object>>() {});
                    dto.setGeom(geom);
                } catch (Exception ex) {
                    // fallback minimal
                    Map<String,Object> geom = new HashMap<>();
                    geom.put("type", "Polygon");
                    geom.put("coordinates", Collections.emptyList());
                    dto.setGeom(geom);
                }
            } else {
                dto.setGeom(null);
            }

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(zones);
    }

    @GetMapping("/totalAreaProtected")
    public double getTotalAreaProtected() {
        return zoneService.totalAreaProtected();
    }
    

    

    // @PostMapping
    // public ResponseEntity<Zone> save(@RequestBody ZoneDTO zone){
    //     Zone newZone = zoneService.createZone(zone);
    //     return ResponseEntity.status(201).body(newZone);
    // }

}
