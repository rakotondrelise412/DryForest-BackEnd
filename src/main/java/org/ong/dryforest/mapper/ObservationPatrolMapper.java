package org.ong.dryforest.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Point;
import org.ong.dryforest.dto.observationPatrol.ObservationPatrolDTO;
import org.ong.dryforest.dto.observationPatrol.ObservationPatrolWebDTO;
import org.ong.dryforest.entity.ObservationPatrol;

public class ObservationPatrolMapper {

    public static ObservationPatrolWebDTO toObservationPatrolWebDTO(
            ObservationPatrol observationPatrol) {

        ObservationPatrolWebDTO observationPatrolDTO =
                new ObservationPatrolWebDTO();

        observationPatrolDTO.setId_observation_patrol(
                observationPatrol.getId()
        );

        observationPatrolDTO.setDate_observation(
                observationPatrol.getDate_observation()
        );

        observationPatrolDTO.setUuid(
                observationPatrol.getUuid()
        );

        observationPatrolDTO.setDescription(
                observationPatrol.getDescription()
        );

        if (observationPatrol.getPatrolGroup() != null) {
            observationPatrolDTO.setPatrolGroup(
                    observationPatrol.getPatrolGroup().getName()
            );
        }

        if (observationPatrol.getZone() != null) {
            observationPatrolDTO.setZone(
                    observationPatrol.getZone().getName()
            );
        }

        if (observationPatrol.getUsers() != null
                && observationPatrol.getUsers().getPerson() != null) {

            observationPatrolDTO.setUser(
                    observationPatrol
                            .getUsers()
                            .getPerson()
                            .getFirst_name()
            );
        }

        if (observationPatrol.getLocation() != null) {

            Point point =
                    observationPatrol.getLocation();

            Map<String, Object> location =
                    new HashMap<>();

            location.put("type", "Point");
            location.put(
                    "coordinates",
                    List.of(
                            point.getX(),
                            point.getY()
                    )
            );

            observationPatrolDTO.setLocation(location);
        }

        return observationPatrolDTO;
    }


    public static ObservationPatrolDTO toObservationPatrolDTO(
            ObservationPatrol observationPatrol) {

        ObservationPatrolDTO dto =
                new ObservationPatrolDTO();

        dto.setId_observation_patrol(
                observationPatrol.getId()
        );

        dto.setUuid(
                observationPatrol.getUuid()
        );

        dto.setDate_observation(
                observationPatrol.getDate_observation()
        );

        dto.setDescription(
                observationPatrol.getDescription()
        );

        dto.setCreated_at(
                observationPatrol.getCreatedAt()
        );

        dto.setUpdated_at(
                observationPatrol.getUpdatedAt()
        );

        dto.set_synced(
                observationPatrol.is_synced()
        );
        if (observationPatrol.getTypeObservationPatrol() != null) {

            dto.setId_type_observation_patrol(
                    observationPatrol
                            .getTypeObservationPatrol()
                            .getId()
            );
        }
        if (observationPatrol.getPatrolGroup() != null) {

            dto.setId_patrol_group(
                    observationPatrol
                            .getPatrolGroup()
                            .getId()
            );
        }

        if (observationPatrol.getZone() != null) {

            dto.setId_zone(
                    observationPatrol
                            .getZone()
                            .getId()
            );
        }

        if (observationPatrol.getUsers() != null) {

            dto.setId_user(
                    observationPatrol
                            .getUsers()
                            .getId()
            );
        }

        if (observationPatrol.getLocation() != null) {

            Point point =
                    observationPatrol.getLocation();

            Map<String, Object> location =
                    new HashMap<>();

            location.put("type", "Point");

            location.put(
                    "coordinates",
                    List.of(
                            point.getX(),
                            point.getY()
                    )
            );

            dto.setLocation(location);
        }

        return dto;
    }
}