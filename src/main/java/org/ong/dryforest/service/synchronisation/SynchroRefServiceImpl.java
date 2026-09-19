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
import org.ong.dryforest.entity.Plantation;
import org.ong.dryforest.entity.PlantationBlock;
import org.ong.dryforest.entity.PlantingMonitoring;
import org.ong.dryforest.entity.Reforestation;
import org.ong.dryforest.entity.ReforestationDetail;
import org.ong.dryforest.entity.SpeciesZoneNeed;
import org.ong.dryforest.entity.SubPlot;
import org.ong.dryforest.entity.Zone;
import org.ong.dryforest.entity.ZoneNeed;
import org.ong.dryforest.mapper.AnimalMapper;
import org.ong.dryforest.mapper.CategoryAnimalMapper;
import org.ong.dryforest.mapper.PatrolGroupMapper;
import org.ong.dryforest.mapper.PersonMapper;
import org.ong.dryforest.mapper.RoleMapper;
import org.ong.dryforest.mapper.SeverityMapper;
import org.ong.dryforest.mapper.SpeciesMapper;
import org.ong.dryforest.mapper.SpeciesTypeMapper;
import org.ong.dryforest.mapper.TypeZoneMapper;
import org.ong.dryforest.service.animalTracking.AnimalService;
import org.ong.dryforest.service.animalTracking.AnimalTrackingDetailService;
import org.ong.dryforest.service.animalTracking.AnimalTrackingService;
import org.ong.dryforest.service.animalTracking.CategoryAnimalService;
import org.ong.dryforest.service.incidentPatrol.PatrolGroupService;
import org.ong.dryforest.service.person.PersonService;
import org.ong.dryforest.service.plantation.PlantationService;
import org.ong.dryforest.service.plantationBlock.PlantationBlockService;
import org.ong.dryforest.service.plantingMonitoring.PlantingMonitoringService;
import org.ong.dryforest.service.reforestation.ReforestationService;
import org.ong.dryforest.service.reforestationDetail.ReforestationDetailService;
import org.ong.dryforest.service.role.RoleService;
import org.ong.dryforest.service.severity.SeverityService;
import org.ong.dryforest.service.species.SpeciesService;
import org.ong.dryforest.service.species.SpeciesTypeService;
import org.ong.dryforest.service.subPlot.SubPlotService;
import org.ong.dryforest.service.zone.SpeciesZoneNeedService;
import org.ong.dryforest.service.zone.TypeZoneService;
import org.ong.dryforest.service.zone.ZoneNeedService;
import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SynchroRefServiceImpl implements SynchroRefService{

    @Autowired
    private SyncProperties syncProperties;

    @Autowired
    private ZoneService zoneService;
    @Autowired
    private ZoneNeedService zoneNeedService;
    @Autowired
    private SpeciesZoneNeedService speciesZoneNeedService;
    @Autowired
    private PlantationService plantationService;
    @Autowired
    private PlantationBlockService plantationBlockService;
    @Autowired
    private ReforestationService reforestationService;
    @Autowired
    private ReforestationDetailService reforestationDetailService;
    @Autowired
    private SubPlotService subPlotService;
    @Autowired
    private PlantingMonitoringService plantingMonitoringService;
    @Autowired
    private AnimalTrackingService animalTrackingService;
    @Autowired
    private AnimalTrackingDetailService animalTrackingDetailService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private PersonService personService;
    @Autowired
    private SpeciesTypeService speciesTypeService;
    @Autowired
    private SpeciesService speciesService;
    @Autowired
    private SeverityService severityService;
    @Autowired
    private TypeZoneService typeZoneService;
    @Autowired
    private PatrolGroupService patrolGroupService;
    @Autowired
    private CategoryAnimalService categoryAnimalService;
    @Autowired
    private AnimalService animalService;

    @Override
    public Map<String, Object> processDynamicPush(DynamicSyncPayload payload){
        Map<String, Object> results = new HashMap<>();

        payload.getTables().forEach((tableName, records) -> {
            switch (tableName) {
                case "zone":
                    results.put("zone", processZone(records));
                    break;

                case "zone_need":
                    results.put("zone_need", processZoneNeed(records));
                    break;

                case "species_zone_need":
                    results.put("species_zone_need", processSpeciesZoneNeed(records));
                    break;
                    
                case "reforestation":
                    results.put("reforestation", processReforestation(records));
                    break;

                case "reforestation_detail":
                    results.put("reforestation_detail", processReforestation(records));
                    break;

                    
                case "plantation_block":
                    results.put("plantation_block", processPlantationBlock(records));
                    break;

                case "sub_plot":
                    results.put("sub_plot", processSubPlot(records));
                    break;
                    
                case "plantation":
                    results.put("plantation", processPlantation(records));
                    break;

                case "plantingMonitoring":
                    results.put("plantingMonitoring", processPlantingMonitoring(records));
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
    public List<Map<String, Object>> processZone(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();

        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                Zone zone = zoneService.mapToEntity(data);

                if (zoneService.existsByUuid(zone.getUuid())) {
                    Zone lastZone = zoneService.findByUuid(zone.getUuid());
                    if (zone.getUpdatedAt().isAfter(lastZone.getUpdatedAt())) {
                        zoneService.updateZone(zone);
                        status = "updated";
                    }
                } else {
                    zoneService.createZone(zone);
                    status = "created";
                }
                response.add(Map.of("uuid", zone.getUuid().toString(), "status", status));
            } catch (Exception e) {
                response.add(Map.of("error", e.getMessage()));
            }
        }

        return response;
    }

    @Override
    public List<Map<String, Object>> processPlantation(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();
        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                Plantation plantation = plantationService.mapToEntity(data);

                if (plantationService.existsByUuid(plantation.getUuid())) {
                    Plantation lastplantation = plantationService.findByUuid(plantation.getUuid());
                    if (plantation.getUpdatedAt().isAfter(lastplantation.getUpdatedAt())) {
                        plantationService.updatePlantation(plantation);
                        status = "updated";
                    }
                } else {
                    plantationService.createPlantation(plantation);
                    status = "created";
                }
                response.add(Map.of("uuid", plantation.getUuid().toString(), "status", status));
            } catch (Exception e) {
                response.add(Map.of("error", e.getMessage()));
            }
        }

        return response;
    }

    @Override
    public List<Map<String, Object>> processPlantationBlock(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();
        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                PlantationBlock plantation = plantationBlockService.mapToEntity(data);

                if (plantationBlockService.existsByUuid(plantation.getUuid())) {
                    PlantationBlock lastplantation = plantationBlockService.findByUuid(plantation.getUuid());
                    if (plantation.getUpdatedAt().isAfter(lastplantation.getUpdatedAt())) {
                        plantationBlockService.updatePlantationBlock(plantation);
                        status = "updated";
                    }
                } else {
                    plantationBlockService.createPlantationBlock(plantation);
                    status = "created";
                }
                response.add(Map.of("uuid", plantation.getUuid().toString(), "status", status));
            } catch (Exception e) {
                response.add(Map.of("error", e.getMessage()));
            }
        }

        return response;
    }

    @Override
    public List<Map<String, Object>> processReforestation(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();
        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                Reforestation reforestation = reforestationService.mapToEntity(data);

                if (reforestationService.existsByUuid(reforestation.getUuid())) {
                    Reforestation lastreforestation = reforestationService.findByUuid(reforestation.getUuid());
                    if (reforestation.getUpdatedAt().isAfter(lastreforestation.getUpdatedAt())) {
                        reforestationService.updateReforestation(reforestation);
                        status = "updated";
                    }
                } else {
                    reforestationService.createReforestation(reforestation);
                    status = "created";
                }
                response.add(Map.of("uuid", reforestation.getUuid().toString(), "status", status));
            } catch (Exception e) {
                response.add(Map.of("error", e.getMessage()));
            }
        }

        return response;
    }

    @Override
    public List<Map<String, Object>> processReforestationDetail(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();
        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                ReforestationDetail reforestationDetail = reforestationDetailService.mapToEntity(data);

                if (reforestationDetailService.existsByUuid(reforestationDetail.getUuid())) {
                    ReforestationDetail lastreforestationDetail = reforestationDetailService.findByUuid(reforestationDetail.getUuid());
                    if (reforestationDetail.getUpdatedAt().isAfter(lastreforestationDetail.getUpdatedAt())) {
                        reforestationDetailService.updateReforestationDetail(reforestationDetail);
                        status = "updated";
                    }
                } else {
                    reforestationDetailService.createReforestationDetail(reforestationDetail);
                    status = "created";
                }
                response.add(Map.of("uuid", reforestationDetail.getUuid().toString(), "status", status));
            } catch (Exception e) {
                response.add(Map.of("error", e.getMessage()));
            }
        }

        return response;
    }

    @Override
    public List<Map<String, Object>> processZoneNeed(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();

        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                ZoneNeed zone = zoneNeedService.mapToEntity(data);

                if (zoneNeedService.existsByUuid(zone.getUuid())) {
                    ZoneNeed lastZone = zoneNeedService.findByUuid(zone.getUuid());
                    if (zone.getUpdatedAt().isAfter(lastZone.getUpdatedAt())) {
                        zoneNeedService.updatZoneNeed(zone);
                        status = "updated";
                    }
                } else {
                    zoneNeedService.createZoneNeed(zone);
                    status = "created";
                }
                response.add(Map.of("uuid", zone.getUuid().toString(), "status", status));
            } catch (Exception e) {
                response.add(Map.of("error", e.getMessage()));
            }
        }

        return response;
    }

    @Override
    public List<Map<String, Object>> processSpeciesZoneNeed(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();

        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                SpeciesZoneNeed zone = speciesZoneNeedService.mapToEntity(data);

                if (speciesZoneNeedService.existsByUuid(zone.getUuid())) {
                    SpeciesZoneNeed lastZone = speciesZoneNeedService.findByUuid(zone.getUuid());
                    if (zone.getUpdatedAt().isAfter(lastZone.getUpdatedAt())) {
                        speciesZoneNeedService.updateSpeciesZoneNeed(zone);
                        status = "updated";
                    }
                } else {
                    speciesZoneNeedService.createSpeciesZoneNeed(zone);
                    status = "created";
                }
                response.add(Map.of("uuid", zone.getUuid().toString(), "status", status));
            } catch (Exception e) {
                response.add(Map.of("error", e.getMessage()));
            }
        }

        return response;
    }

    @Override
    public List<Map<String, Object>> processSubPlot(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();

        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                SubPlot zone = subPlotService.mapToEntity(data);

                if (subPlotService.existsByUuid(zone.getUuid())) {
                    SubPlot lastZone = subPlotService.findByUuid(zone.getUuid());
                    if (zone.getUpdatedAt().isAfter(lastZone.getUpdatedAt())) {
                        subPlotService.updateSubPlot(zone);
                        status = "updated";
                    }
                } else {
                    subPlotService.createSubPlot(zone);
                    status = "created";
                }
                response.add(Map.of("uuid", zone.getUuid().toString(), "status", status));
            } catch (Exception e) {
                response.add(Map.of("error", e.getMessage()));
            }
        }

        return response;
    }

    @Override
    public List<Map<String, Object>> processPlantingMonitoring(List<Map<String, Object>> records){
        List<Map<String, Object>> response = new ArrayList<>();

        for (Map<String,Object> data : records) {
            try {
                String status = "ignored";
                PlantingMonitoring zone = plantingMonitoringService.mapToEntity(data);

                if (plantingMonitoringService.existsByUuid(zone.getUuid())) {
                    PlantingMonitoring lastZone = plantingMonitoringService.findByUuid(zone.getUuid());
                    if (zone.getUpdatedAt().isAfter(lastZone.getUpdatedAt())) {
                        plantingMonitoringService.updatePlantingMonitoring(zone);
                        status = "updated";
                    }
                } else {
                    plantingMonitoringService.createPlantingMonitoring(zone);
                    status = "created";
                }
                response.add(Map.of("uuid", zone.getUuid().toString(), "status", status));
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

        for (String table : syncProperties.getPullableTablesReforestation()) {
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
            case "role"
                -> RoleMapper.toDTOList(roleService.findAllRoleUpdatedSince(last_sync));
            case "person"
                -> PersonMapper.toDTOList(personService.findAllPersonsUpdatedSince(last_sync));
            case "species_type"
                -> SpeciesTypeMapper.toDTOList(speciesTypeService.findAllTypesUpdatedSince(last_sync));
            case "species" 
                -> SpeciesMapper.toDTOList(speciesService.findAllSpeciesUpdatedSince(last_sync));
            case "severity" 
                -> SeverityMapper.toDTOList(severityService.findAllSeveritiesUpdatedSince(last_sync));
            case "type_zone"
                -> TypeZoneMapper.toDTOList(typeZoneService.findAllTypesUpdatedSince(last_sync));
            case "patrol_group"
                -> PatrolGroupMapper.toDTOList(patrolGroupService.findAllPatrolGroupUpdatedSince(last_sync));
            case "category_animal"
                -> CategoryAnimalMapper.toDTOList(categoryAnimalService.findAllCategoryAnimalUpdatedSince(last_sync));
            case "animal"
                -> AnimalMapper.toDTOList(animalService.findAllAnimalUpdatedSince(last_sync));

                default
                -> List.of();
        };
    }
}
