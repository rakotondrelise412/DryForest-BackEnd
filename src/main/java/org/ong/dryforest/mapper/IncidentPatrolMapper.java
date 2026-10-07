package org.ong.dryforest.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.ong.dryforest.dto.incidentPatrol.IncidentPatrolDTO;
import org.ong.dryforest.dto.incidentPatrol.IncidentPatrolWebDTO;
import org.ong.dryforest.entity.IncidentPatrol;
import org.ong.dryforest.service.incidentPatrol.PatrolGroupService;
import org.ong.dryforest.service.incidentPatrol.TypeIncidentPatrolService;
import org.ong.dryforest.service.plantationBlock.PlantationBlockService;
import org.ong.dryforest.service.severity.SeverityService;
import org.ong.dryforest.service.user.UserService;
import org.ong.dryforest.service.zone.ZoneService;

public class IncidentPatrolMapper {


    public static IncidentPatrolWebDTO toIncidentPatrolWebDTO(
            IncidentPatrol incidentPatrol
    ) {

        IncidentPatrolWebDTO incidentPatrolWebDTO =
                new IncidentPatrolWebDTO();

        incidentPatrolWebDTO.setId_incident_patrol(
                incidentPatrol.getId()
        );

        incidentPatrolWebDTO.setUuid(
                incidentPatrol.getUuid() != null
                        ? incidentPatrol.getUuid().toString()
                        : null
        );

        incidentPatrolWebDTO.setDatetime_incident(
                incidentPatrol.getDatetime_incident()
        );

        if (incidentPatrol.getLocation() != null) {

            Point p = incidentPatrol.getLocation();

            Map<String, Object> location =
                    new HashMap<>();

            location.put("type", "Point");

            location.put(
                    "coordinates",
                    List.of(
                            p.getX(),
                            p.getY()
                    )
            );

            incidentPatrolWebDTO.setLocation(location);
        }

        incidentPatrolWebDTO.setDescription(
                incidentPatrol.getDescription()
        );

        incidentPatrolWebDTO.setImage(
                incidentPatrol.getImage()
        );

        if (incidentPatrol.getPatrolGroup() != null) {
            incidentPatrolWebDTO.setPatrolGroup(
                    incidentPatrol.getPatrolGroup().getName()
            );
        }

        if (incidentPatrol.getUsers() != null
                && incidentPatrol.getUsers().getPerson() != null) {

            incidentPatrolWebDTO.setUsers(
                    incidentPatrol
                            .getUsers()
                            .getPerson()
                            .getFirst_name()
            );
        }

        if (incidentPatrol.getZone() != null) {
            incidentPatrolWebDTO.setZone(
                    incidentPatrol.getZone().getName()
            );
        }

        if (incidentPatrol.getPlantationBlock() != null) {
            incidentPatrolWebDTO.setPlantationBlock(
                    incidentPatrol
                            .getPlantationBlock()
                            .getName()
            );
        }

        if (incidentPatrol.getSeverity() != null) {
            incidentPatrolWebDTO.setSeverity(
                    incidentPatrol.getSeverity().getName()
            );
        }

        if (incidentPatrol.getTypeIncidentPatrol() != null) {
            incidentPatrolWebDTO.setType_incident_patrol(
                    incidentPatrol.getTypeIncidentPatrol().getName()
            );
        }

        return incidentPatrolWebDTO;
    }


    public static IncidentPatrol toEntity(
            IncidentPatrolDTO dto,
            PatrolGroupService patrolGroupService,
            UserService userService,
            ZoneService zoneService,
            PlantationBlockService plantationBlockService,
            SeverityService severityService,
            TypeIncidentPatrolService typeIncidentPatrolService
    ) {

        IncidentPatrol entity =
                new IncidentPatrol();

        entity.setUuid(
                dto.getUuid() != null
                        ? UUID.fromString(dto.getUuid())
                        : UUID.randomUUID()
        );

        entity.setDatetime_incident(
                dto.getDatetime_incident()
        );

        entity.setLocation(
                toPoint(dto.getLocation())
        );

        entity.setDescription(
                dto.getDescription()
        );

        entity.setImage(
                dto.getImage()
        );

        entity.setPatrolGroup(
                patrolGroupService.findById(
                        dto.getId_patrol_group()
                )
        );

        entity.setUsers(
                userService.findUsersById(
                        dto.getId_user()
                )
        );

        entity.setZone(
                zoneService.findById(
                        dto.getId_zone()
                )
        );

        entity.setPlantationBlock(
                plantationBlockService.findById(
                        dto.getId_plantation_block()
                )
        );

        entity.setSeverity(
                severityService.findSeverityEntityById(
                        dto.getId_severity()
                )
        );

        entity.setTypeIncidentPatrol(
                typeIncidentPatrolService.findById(
                        dto.getId_type_incident_patrol()
                )
        );

        return entity;
    }


    // ============================================================
    // UPDATE ENTITY
    // ============================================================

    public static void updateEntity(
            IncidentPatrol entity,
            IncidentPatrolDTO dto,
            PatrolGroupService patrolGroupService,
            UserService userService,
            ZoneService zoneService,
            PlantationBlockService plantationBlockService,
            SeverityService severityService,
            TypeIncidentPatrolService typeIncidentPatrolService
    ) {

        if (dto.getUuid() != null) {
            entity.setUuid(
                    UUID.fromString(dto.getUuid())
            );
        }

        if (dto.getDatetime_incident() != null) {
            entity.setDatetime_incident(
                    dto.getDatetime_incident()
            );
        }

        if (dto.getLocation() != null) {
            entity.setLocation(
                    toPoint(dto.getLocation())
            );
        }

        if (dto.getDescription() != null) {
            entity.setDescription(
                    dto.getDescription()
            );
        }

        if (dto.getImage() != null) {
            entity.setImage(
                    dto.getImage()
            );
        }

        if (dto.getId_patrol_group() > 0) {
            entity.setPatrolGroup(
                    patrolGroupService.findById(
                            dto.getId_patrol_group()
                    )
            );
        }

        if (dto.getId_user() > 0) {
            entity.setUsers(
                    userService.findUsersById(
                            dto.getId_user()
                    )
            );
        }

        if (dto.getId_zone() > 0) {
            entity.setZone(
                    zoneService.findById(
                            dto.getId_zone()
                    )
            );
        }

        if (dto.getId_plantation_block() > 0) {
            entity.setPlantationBlock(
                    plantationBlockService.findById(
                            dto.getId_plantation_block()
                    )
            );
        }

        if (dto.getId_severity() > 0) {
            entity.setSeverity(
                    severityService.findSeverityEntityById(
                            dto.getId_severity()
                    )
            );
        }

        if (dto.getId_type_incident_patrol() > 0) {
            entity.setTypeIncidentPatrol(
                    typeIncidentPatrolService.findById(
                            dto.getId_type_incident_patrol()
                    )
            );
        }
    }


    // ============================================================
    // GEOJSON -> POINT
    // ============================================================

    private static Point toPoint(
            Map<String, Object> location
    ) {

        if (location == null) {
            return null;
        }

        Object coordinatesObject =
                location.get("coordinates");

        if (!(coordinatesObject instanceof List<?> coordinates)) {
            throw new IllegalArgumentException(
                    "location.coordinates doit être un tableau [longitude, latitude]"
            );
        }

        if (coordinates.size() < 2) {
            throw new IllegalArgumentException(
                    "location.coordinates doit contenir longitude et latitude"
            );
        }

        double longitude =
                ((Number) coordinates.get(0)).doubleValue();

        double latitude =
                ((Number) coordinates.get(1)).doubleValue();

        GeometryFactory geometryFactory =
                new GeometryFactory(
                        new PrecisionModel(),
                        4326
                );

        return geometryFactory.createPoint(
                new Coordinate(
                        longitude,
                        latitude
                )
        );
    }
}
