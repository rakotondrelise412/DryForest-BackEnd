package org.ong.dryforest.dto.animaltracking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalTrackingDTO {

    private int id_animal_tracking;

    private UUID uuid;

    private Map<String, Object> location;

    private LocalDateTime date_tracking;

    private boolean have_seen;

    private String description;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private boolean is_synced;

    private int id_plantation_block;

    private List<AnimalTrackingDetailDTO>
            animal_tracking_details;
}