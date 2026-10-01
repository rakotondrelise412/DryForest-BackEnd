package org.ong.dryforest.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.zone.TypeZoneDTO;
import org.ong.dryforest.entity.TypeZone;
import org.ong.dryforest.mapper.TypeZoneMapper;
import org.ong.dryforest.service.zone.TypeZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/typeZone")
public class TypeZoneController {

    @Autowired
    private TypeZoneService typeZoneService;


    @GetMapping
    public ResponseEntity<List<TypeZoneDTO>> findAll() {

        List<TypeZone> typeZones = typeZoneService.findAll();

        List<TypeZoneDTO> typeZoneDTO = new ArrayList<>();

        if (typeZones != null && !typeZones.isEmpty()) {

            typeZoneDTO = typeZones.stream()
                    .map(TypeZoneMapper::toTypeZone)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(typeZoneDTO);
    }


    @GetMapping("/sync")
    public ResponseEntity<List<TypeZoneDTO>> sync(
            @RequestParam("last_sync")
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime lastSync) {

        List<TypeZone> typeZones =
                typeZoneService.findAllTypesUpdatedSince(lastSync);

        List<TypeZoneDTO> result = typeZones.stream()
                .map(TypeZoneMapper::toTypeZone)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeZoneDTO> findById(
            @PathVariable int id) {

        TypeZone typeZone = typeZoneService.findById(id);

        return ResponseEntity.ok(
                TypeZoneMapper.toTypeZone(typeZone)
        );
    }

    @PostMapping
    public ResponseEntity<TypeZoneDTO> create(
            @RequestBody TypeZone typeZone) {

        TypeZone savedTypeZone =
                typeZoneService.createTypeZone(typeZone);

        return ResponseEntity.ok(
                TypeZoneMapper.toTypeZone(savedTypeZone)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<TypeZoneDTO> update(
            @PathVariable int id,
            @RequestBody TypeZone typeZone) {

        typeZone.setId(id);

        TypeZone updatedTypeZone =
                typeZoneService.updateTypeZone(typeZone);

        return ResponseEntity.ok(
                TypeZoneMapper.toTypeZone(updatedTypeZone)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        TypeZone typeZone = typeZoneService.findById(id);

        typeZoneService.deleteTypeZone(typeZone);

        return ResponseEntity.noContent().build();
    }

}