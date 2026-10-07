package org.ong.dryforest.dto.observationPatrol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObservationPatrolDTO {
    private int id_observation_patrol;
    private UUID uuid;
    private LocalDateTime date_observation;
    private String description;
    private int id_type_observation_patrol;
    private int id_patrol_group;
    private int id_zone;
    private int id_user;
    private LocalDateTime created_at; 
    private LocalDateTime updated_at;

    @JsonProperty("is_synced")
    private boolean is_synced;
    private Map<String, Object> location;

}
