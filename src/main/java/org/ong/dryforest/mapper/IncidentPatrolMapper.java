package org.ong.dryforest.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Point;
import org.ong.dryforest.dto.incidentPatrol.IncidentPatrolWebDTO;
import org.ong.dryforest.entity.IncidentPatrol;

public class IncidentPatrolMapper {
    public static IncidentPatrolWebDTO toIncidentPatrolWebDTO(IncidentPatrol incidentPatrol){
        IncidentPatrolWebDTO incidentPatrolWebDTO = new IncidentPatrolWebDTO();
        incidentPatrolWebDTO.setId_incident_patrol(incidentPatrol.getId());
        incidentPatrolWebDTO.setDatetime_incident(incidentPatrol.getDatetime_incident());
        if (incidentPatrol.getLocation() != null) {
            Point p = incidentPatrol.getLocation();

            Map<String, Object> location = new HashMap<>();
            location.put("type", "Point");

            // GeoJSON: [longitude, latitude]
            location.put("coordinates", List.of(p.getX(), p.getY()));

            incidentPatrolWebDTO.setLocation(location);
        }
        incidentPatrolWebDTO.setDescription(incidentPatrol.getDescription());
        incidentPatrolWebDTO.setImage(incidentPatrol.getImage());
        incidentPatrolWebDTO.setPatrolGroup(incidentPatrol.getPatrolGroup().getName());
        incidentPatrolWebDTO.setUsers(incidentPatrol.getUsers().getPerson().getFirst_name());
        incidentPatrolWebDTO.setZone(incidentPatrol.getZone().getName());
        incidentPatrolWebDTO.setPlantationBlock(incidentPatrol.getPlantationBlock().getName());
        incidentPatrolWebDTO.setSeverity(incidentPatrol.getSeverity().getName());
        return incidentPatrolWebDTO;
    }
}
