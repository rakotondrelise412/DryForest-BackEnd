package org.ong.dryforest.dto.incidentPatrol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeIncidentPatrolDTO {
    private int id_incident_patrol_type;
    private String name;
}
