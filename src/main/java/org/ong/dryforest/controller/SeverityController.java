package org.ong.dryforest.controller;

import org.ong.dryforest.service.severity.SeverityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/severity")
public class SeverityController {
    @Autowired
    private SeverityService severityService;

    // @GetMapping
    // public ResponseEntity<List<SeverityDTO>> findAll(){
    //     List<SeverityDTO> severityDTO = new ArrayList<>();
    //     List<Severity> severities = severityService.findAll();

    //     if (severities != null && !severities.isEmpty()) {
    //         severityDTO = severityService.findAll().stream().map(SeverityMapper::toSeverityDTO).collect(Collectors.toList());
    //     }

    //     return ResponseEntity.ok(severityDTO);
    // }
}
