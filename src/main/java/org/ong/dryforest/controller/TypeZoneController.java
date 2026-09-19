package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.zone.TypeZoneDTO;
import org.ong.dryforest.entity.TypeZone;
import org.ong.dryforest.mapper.TypeZoneMapper;
import org.ong.dryforest.service.zone.TypeZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/typeZone")
public class TypeZoneController {
    
    @Autowired
    private TypeZoneService typeZoneService;

    @GetMapping
    public ResponseEntity<List<TypeZoneDTO>> findAll(){
        List<TypeZoneDTO> typeZoneDTO = new ArrayList<>();
        List<TypeZone> typeZones = typeZoneService.findAll();
        
        if (typeZones != null && !typeZones.isEmpty()) {
            typeZoneDTO = typeZoneService.findAll().stream().map(TypeZoneMapper::toTypeZone).collect(Collectors.toList());
        }
        return ResponseEntity.ok(typeZoneDTO);
    }
}
