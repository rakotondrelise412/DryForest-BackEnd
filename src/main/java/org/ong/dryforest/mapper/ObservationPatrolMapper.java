package org.ong.dryforest.mapper;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Point;
import org.ong.dryforest.dto.observationPatrol.ObservationPatrolWebDTO;
import org.ong.dryforest.entity.ObservationPatrol;

public class ObservationPatrolMapper {
    public static ObservationPatrolWebDTO toObservationPatrolWebDTO(ObservationPatrol observationPatrol){
        ObservationPatrolWebDTO observationPatrolDTO = new ObservationPatrolWebDTO();
        observationPatrolDTO.setId_observation_patrol(observationPatrol.getId());
        observationPatrolDTO.setDate_observation(observationPatrol.getDate_observation());
        observationPatrolDTO.setDescription(observationPatrol.getDescription());
        observationPatrolDTO.setPatrolGroup(observationPatrol.getPatrolGroup().getName());
        observationPatrolDTO.setZone(observationPatrol.getZone().getName());
        observationPatrolDTO.setUser(observationPatrol.getUsers().getPerson().getFirst_name());

        if (observationPatrol.getLocation() != null) {
            Point p = observationPatrol.getLocation();

            Map<String, Object> location = new HashMap<>();
            location.put("type", "Point");

            // GeoJSON: [longitude, latitude]
            location.put("coordinates", List.of(p.getX(), p.getY()));

            observationPatrolDTO.setLocation(location);
        }

        return observationPatrolDTO;
    }
}
