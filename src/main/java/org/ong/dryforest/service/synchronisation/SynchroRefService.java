package org.ong.dryforest.service.synchronisation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.ong.dryforest.dto.synchronization.DynamicSyncPayload;

public interface SynchroRefService {
    
    Map<String, Object> processDynamicPush(DynamicSyncPayload payload);

    List<Map<String, Object>> processZone(List<Map<String, Object>> records);

    List<Map<String, Object>> processPlantation(List<Map<String, Object>> records);

    List<Map<String, Object>> processPlantationBlock(List<Map<String, Object>> records);

    List<Map<String, Object>> processReforestation(List<Map<String, Object>> records);

    List<Map<String, Object>> processReforestationDetail(List<Map<String, Object>> records);

    List<Map<String, Object>> processZoneNeed(List<Map<String, Object>> records);

    List<Map<String, Object>> processSubPlot(List<Map<String, Object>> records);

    List<Map<String, Object>> processPlantingMonitoring(List<Map<String, Object>> records);

    List<Map<String, Object>> processAnimalTracking(List<Map<String, Object>> records);

    List<Map<String, Object>> processAnimalTrackingDetail(List<Map<String, Object>> records);

    Map<String, Object> processDynamicPull(LocalDateTime last_sync);

    List<?> findUpdatedEntities(String table, LocalDateTime last_sync);

    List<Map<String, Object>> processSpeciesZoneNeed(List<Map<String, Object>> records);
    
       

}