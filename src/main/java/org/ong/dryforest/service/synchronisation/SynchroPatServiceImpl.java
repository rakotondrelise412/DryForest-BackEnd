package org.ong.dryforest.service.synchronisation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ong.dryforest.config.SyncProperties;
import org.ong.dryforest.dto.synchronization.DynamicSyncPayload;
import org.ong.dryforest.entity.AnimalTracking;
import org.ong.dryforest.entity.AnimalTrackingDetail;
import org.ong.dryforest.entity.IncidentPatrol;
import org.ong.dryforest.entity.ObservationPatrol;
import org.ong.dryforest.mapper.AnimalMapper;
import org.ong.dryforest.mapper.CategoryAnimalMapper;
import org.ong.dryforest.mapper.PatrolGroupMapper;
import org.ong.dryforest.mapper.TypeIncidentPatrolMapper;
import org.ong.dryforest.mapper.TypeObservationPatrolMapper;
import org.ong.dryforest.service.animalTracking.AnimalService;
import org.ong.dryforest.service.animalTracking.AnimalTrackingDetailService;
import org.ong.dryforest.service.animalTracking.AnimalTrackingService;
import org.ong.dryforest.service.animalTracking.CategoryAnimalService;
import org.ong.dryforest.service.incidentPatrol.IncidentPatrolService;
import org.ong.dryforest.service.incidentPatrol.PatrolGroupService;
import org.ong.dryforest.service.incidentPatrol.TypeIncidentPatrolService;
import org.ong.dryforest.service.observationPatrol.ObservationPatrolService;
import org.ong.dryforest.service.observationPatrol.TypeObservationPatrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SynchroPatServiceImpl implements SynchroPatService{
    @Autowired
    private SyncProperties syncProperties;

    @Autowired
    private IncidentPatrolService incidentPatrolService;
    @Autowired
    private ObservationPatrolService observationPatrolService;
    @Autowired
    private AnimalTrackingService animalTrackingService;
    @Autowired
    private AnimalTrackingDetailService animalTrackingDetailService;

    @Autowired
    private PatrolGroupService patrolGroupService;
    @Autowired
    private TypeIncidentPatrolService typeIncidentPatrolService;
    @Autowired
    private TypeObservationPatrolService typeObservationPatrolService;
    @Autowired
    private CategoryAnimalService categoryAnimalService;
    @Autowired
    private AnimalService animalService;

    @Override
    public Map<String, Object> processDynamicPush(DynamicSyncPayload payload){
        Map<String, Object> results = new HashMap<>();

        payload.getTables().forEach((tableName, records) -> {
            switch (tableName) {
                case "incident_patrol":
                    results.put("incident_patrol", processIncidentPatrol(records));
                    break;

                case "observation_pat":
                    results.put("observation_pat", processObservationPatrol(records));
                    break;

                case "animal_tracking":
                    results.put("animal_tracking", processAnimalTracking(records));
                    break;

                case "animal_tracking_detail":
                    results.put("animal_tracking_detail", processAnimalTrackingDetail(records));
                    break;

                default:
                    results.put(tableName, List.of(Map.of("error", "Table non reconnu")));
            }
        });
        return results;
    }

    @Override
    public List<Map<String, Object>> processIncidentPatrol(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();

        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                IncidentPatrol incidentPatrol = incidentPatrolService.mapToEntity(data);

                if (incidentPatrolService.existsByUuid(incidentPatrol.getUuid())) {
                    IncidentPatrol lastincidentPatrol = incidentPatrolService.findByUuid(incidentPatrol.getUuid());
                    if (incidentPatrol.getUpdatedAt().isAfter(lastincidentPatrol.getUpdatedAt())) {
                        incidentPatrolService.updateIncidentPatrol(incidentPatrol);
                        status = "updated";
                    }
                } else {
                    incidentPatrolService.createIncidentPatrol(incidentPatrol);
                    status = "created";
                }
                response.add(Map.of("uuid", incidentPatrol.getUuid().toString(), "status", status));
            } catch (Exception e) {
                response.add(Map.of("error", e.getMessage()));
            }
        }

        return response;
    }

    @Override
    public List<Map<String, Object>> processObservationPatrol(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();

        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                ObservationPatrol incidentPatrol = observationPatrolService.mapToEntity(data);

                if (observationPatrolService.existsByUuid(incidentPatrol.getUuid())) {
                    ObservationPatrol lastincidentPatrol = observationPatrolService.findByUuid(incidentPatrol.getUuid());
                    if (incidentPatrol.getUpdatedAt().isAfter(lastincidentPatrol.getUpdatedAt())) {
                        observationPatrolService.updateObservationPatrol(incidentPatrol);
                        status = "updated";
                    }
                } else {
                    observationPatrolService.createObservationPatrol(incidentPatrol);
                    status = "created";
                }
                response.add(Map.of("uuid", incidentPatrol.getUuid().toString(), "status", status));
            } catch (Exception e) {
                response.add(Map.of("error", e.getMessage()));
            }
        }

        return response;
    }


        @Override
    public List<Map<String, Object>> processAnimalTracking(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();

        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                AnimalTracking animalTracking = animalTrackingService.mapToEntity(data);

                if (animalTrackingService.existsByUuid(animalTracking.getUuid())) {
                    AnimalTracking lastAnimalTracking = animalTrackingService.findByUuid(animalTracking.getUuid());
                    if (animalTracking.getUpdatedAt().isAfter(lastAnimalTracking.getUpdatedAt())) {
                        animalTrackingService.updateAnimalTracking(animalTracking);
                        status = "updated";
                    }
                } else {
                    animalTrackingService.createAnimalTracking(animalTracking);
                    status = "created";
                }
                response.add(Map.of("uuid", animalTracking.getUuid().toString(), "status", status));
            } catch (Exception e) {
                response.add(Map.of("error", e.getMessage()));
            }
        }

        return response;
    }

    @Override
    public List<Map<String, Object>> processAnimalTrackingDetail(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();

        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                AnimalTrackingDetail animalTrackingDetail = animalTrackingDetailService.mapToEntity(data);

                if (animalTrackingDetailService.existsByUuid(animalTrackingDetail.getUuid())) {
                    AnimalTrackingDetail lastAnimalTracking = animalTrackingDetailService.findByUuid(animalTrackingDetail.getUuid());
                    if (animalTrackingDetail.getUpdatedAt().isAfter(lastAnimalTracking.getUpdatedAt())) {
                        animalTrackingDetailService.updateAnimalTrackingDetail(animalTrackingDetail);
                        status = "updated";
                    }
                } else {
                    animalTrackingDetailService.createAnimalTrackingDetail(animalTrackingDetail);
                    status = "created";
                }
                response.add(Map.of("uuid", animalTrackingDetail.getUuid().toString(), "status", status));
            } catch (Exception e) {
                response.add(Map.of("error", e.getMessage()));
            }
        }

        return response;
    }

    @Override
    public Map<String, Object> processDynamicPull(LocalDateTime last_sync) {
        Map<String, Object> results = new HashMap<>();

        for (String table : syncProperties.getPullableTablesPatrol()) {
            List<?> data = findUpdatedEntities(table, last_sync);
            if (!data.isEmpty()) {
                results.put(table, data);
            }
        }

        return results;
    }

    @Override
    public List<?> findUpdatedEntities(String table, LocalDateTime last_sync) {
        return switch (table) {
            case "patrol_group"
                -> PatrolGroupMapper.toDTOList(patrolGroupService.findAllPatrolGroupUpdatedSince(last_sync));
            case "incident_patrol_type"
                -> TypeIncidentPatrolMapper.toDTOlList(typeIncidentPatrolService.findAllTypeIncidentPatrolsUpdatedSince(last_sync));
            case "observation_patrol_type"
                -> TypeObservationPatrolMapper.toDTOList(typeObservationPatrolService.findAllTypeObservationPatrolUpdatedSince(last_sync));
            case "category_animal"
                -> CategoryAnimalMapper.toDTOList(categoryAnimalService.findAllCategoryAnimalUpdatedSince(last_sync));
            case "animal"
                -> AnimalMapper.toDTOList(animalService.findAllAnimalUpdatedSince(last_sync));

                default
                -> List.of();
        };
    } 
}
