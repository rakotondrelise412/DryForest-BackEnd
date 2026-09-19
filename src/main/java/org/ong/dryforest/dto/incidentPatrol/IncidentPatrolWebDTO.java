package org.ong.dryforest.dto.incidentPatrol;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentPatrolWebDTO {
    private int id_incident_patrol;
    private LocalDateTime datetime_incident;
    private Map<String, Object> location;
    private String description;
    private String image;
    private String patrolGroup;
    private String users;
    private String zone;
    private String plantationBlock;
    private String severity;
}
