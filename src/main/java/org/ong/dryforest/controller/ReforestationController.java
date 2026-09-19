package org.ong.dryforest.controller;

import java.util.List;

import org.ong.dryforest.dto.reforestation.LatestReforestationDTO;
import org.ong.dryforest.dto.reforestation.ReforestationCountingDTO;
import org.ong.dryforest.entity.Reforestation;
import org.ong.dryforest.mapper.ReforestationMapper;
import org.ong.dryforest.service.reforestation.ReforestationService;
import org.ong.dryforest.service.reforestationDetail.ReforestationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reforestation")
public class ReforestationController {
    @Autowired
    ReforestationService reforestationService;
    @Autowired
    ReforestationMapper reforestationMapper;
    
    @Autowired
    ReforestationDetailService reforestationDetailService;

    @GetMapping("/total_planted")
    public int getTotalPlanted(){
        return reforestationService.getTotalPlanted();
    }

    @GetMapping("/total_last_planted")
    public ResponseEntity<LatestReforestationDTO> getTotalLastPlanted() {
        Reforestation reforestation = reforestationService.getTotalLastPlanted();
        if (reforestation == null) {
            return ResponseEntity.noContent().build();
        }
        LatestReforestationDTO dto = reforestationMapper.toLatestReforestationDTO(reforestation);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/ref_quantity_by_type_zone")
    public ResponseEntity<List<ReforestationCountingDTO>> reportByTypeZone() {
        List<ReforestationCountingDTO> data = reforestationService.getQuantityByTypeZone();
        return ResponseEntity.ok(data);
    }

    // @PostMapping
    // public ResponseEntity<?> create(@RequestBody ReforestationDTO reforestationDto) {
    //     Reforestation saved = reforestationService.createReforestation(reforestationDto);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    // }
}
