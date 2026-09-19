package org.ong.dryforest.service.synchronisation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.ong.dryforest.dto.synchronization.DynamicSyncPayload;

public interface SynchroPatService {

    List<?> findUpdatedEntities(String table, LocalDateTime last_sync);

    Map<String, Object> processDynamicPull(LocalDateTime last_sync);

    Map<String, Object> processDynamicPush(DynamicSyncPayload payload);

    List<Map<String, Object>> processIncidentPatrol(List<Map<String, Object>> records);

    List<Map<String, Object>> processObservationPatrol(List<Map<String, Object>> records);

    List<Map<String, Object>> processAnimalTracking(List<Map<String, Object>> records);

    List<Map<String, Object>> processAnimalTrackingDetail(List<Map<String, Object>> records);
    
}
