package org.ong.dryforest.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.ong.dryforest.dto.synchronization.DynamicSyncPayload;
import org.ong.dryforest.service.synchronisation.SynchroPatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/syncPat")
public class SynchroPatController {
    @Autowired
    private SynchroPatService synchroPatService;

    @PostMapping("/push")
    public ResponseEntity<Map<String, Object>> push(@RequestBody DynamicSyncPayload payload ){
        Map<String, Object> response = synchroPatService.processDynamicPush(payload);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/pull")
    public ResponseEntity<Map<String, Object>> pull(@RequestParam("last_sync") LocalDateTime last_sync){
        Map<String, Object> response = synchroPatService.processDynamicPull(last_sync);

        return ResponseEntity.ok(response);
    }
}
