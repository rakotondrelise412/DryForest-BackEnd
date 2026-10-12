package org.ong.dryforest.dto.observationPatrol;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObservationPatrolWebDTO {
    private int id_observation_patrol;
    private UUID uuid;
    private LocalDateTime date_observation;
    private String description;
    private String patrolGroup;
    private String zone;
    private String user;
    private Map<String, Object> location;
}
