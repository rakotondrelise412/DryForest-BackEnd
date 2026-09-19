package org.ong.dryforest.dto.incidentPatrol;

import java.time.LocalDateTime;
import java.util.Map;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentPatrolDTO {
    private int id_incident_patrol;
    private String uuid;
    private LocalDateTime datetime_incident;
    private Map<String, Object> location;
    private String description;
    private String image;
    private int id_patrol_group;
    private int id_user;
    private int id_zone;
    private int id_plantation_block;
    private int id_severity;
    private int id_type_incident_patrol;
}
