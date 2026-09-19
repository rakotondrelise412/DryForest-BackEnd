package org.ong.dryforest.dto.observationPatrol;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObservationPatrolWebDTO {
    private int id_observation_patrol;
    private LocalDateTime date_observation;
    private String description;
    private String patrolGroup;
    private String zone;
    private String user;
    private Map<String, Object> location;
}
