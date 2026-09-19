package org.ong.dryforest.service.incidentPatrol;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.locationtech.jts.geom.Point;
import org.ong.dryforest.dto.CountByTypeDTO;
import org.ong.dryforest.entity.IncidentPatrol;
import org.ong.dryforest.repository.IncidentPatrolRepository;
import org.ong.dryforest.service.plantationBlock.PlantationBlockService;
import org.ong.dryforest.service.severity.SeverityService;
import org.ong.dryforest.service.user.UserService;
import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class IncidentPatrolServiceImpl implements IncidentPatrolService{
    @Autowired
    IncidentPatrolRepository incidentPatrolRepository;
    @Autowired
    PatrolGroupService patrolGroupService;
    @Autowired
    UserService userService;
    @Autowired
    ZoneService zoneService;
    @Autowired
    PlantationBlockService plantationBlockService;
    @Autowired
    SeverityService severityService;
    @Autowired
    TypeIncidentPatrolService typeIncidentPatrolService;
    
    @Override
    public List<IncidentPatrol> findAll(){
        return incidentPatrolRepository.findAllByIsDeletedFalse();
    }

    @Override
    public IncidentPatrol createIncidentPatrol(IncidentPatrol incidentPatrol){
        incidentPatrol.set_synced(true);
        return incidentPatrolRepository.save(incidentPatrol);
    }

    @Override
    public IncidentPatrol findById(int id){
        return incidentPatrolRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new RuntimeException("Incident pendant le patrouille introuvable avec l'id : " + id));
    }

    @Override
    public IncidentPatrol findByUuid(UUID uuid){
        return incidentPatrolRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Incident pendant le patrouille introuvable avec uuid : " + uuid));
    }

    @Override
    public IncidentPatrol updateIncidentPatrol(IncidentPatrol incidentPatrol){
        findById(incidentPatrol.getId());
        return incidentPatrolRepository.save(incidentPatrol);
    }

    @Override
    public void deleteIncidentPatrol(IncidentPatrol incidentPatrol){
        try {
            findById(incidentPatrol.getId());
            incidentPatrolRepository.delete(incidentPatrol);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer ce placeau");
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid){
        return incidentPatrolRepository.existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public IncidentPatrol mapToEntity(Map<String, Object> incidentPatrolMapping){
        IncidentPatrol incidentPatrol = new IncidentPatrol();

        incidentPatrol.setUuid(UUID.fromString((String)incidentPatrolMapping.get("uuid")));
        incidentPatrol.setDatetime_incident(LocalDateTime.parse((String)incidentPatrolMapping.get("datetime_incident")));
        incidentPatrol.setLocation((Point)incidentPatrolMapping.get("location"));
        incidentPatrol.setDescription((String)incidentPatrolMapping.get("description"));
        incidentPatrol.setImage((String)incidentPatrolMapping.get("image"));
        incidentPatrol.setCreatedAt(LocalDateTime.parse((String)incidentPatrolMapping.get("created_at")));
        incidentPatrol.setUpdatedAt(LocalDateTime.parse((String)incidentPatrolMapping.get("updated_at")));
        incidentPatrol.set_synced((boolean)incidentPatrolMapping.get("is_synced"));
        incidentPatrol.setPlantationBlock(plantationBlockService.findById(((Number)incidentPatrolMapping.get("id_plantation_block")).intValue()));
        incidentPatrol.setPatrolGroup(patrolGroupService.findById(((Number)incidentPatrolMapping.get("id_patrol_group")).intValue()));
        incidentPatrol.setUsers(userService.findUsersById(((Number)incidentPatrolMapping.get("id_user")).intValue()));
        incidentPatrol.setZone(zoneService.findById(((Number)incidentPatrolMapping.get("id_zone")).intValue()));
        incidentPatrol.setSeverity(severityService.findSeverityById(((Number)incidentPatrolMapping.get("id_severity")).intValue()));
        incidentPatrol.setTypeIncidentPatrol(typeIncidentPatrolService.findById(((Number)incidentPatrolMapping.get("id_incident_patrol_type")).intValue()));

        return incidentPatrol;
    }

    @Override
    public List<CountByTypeDTO> countByType() {
        return incidentPatrolRepository.countByType();
    }
}
