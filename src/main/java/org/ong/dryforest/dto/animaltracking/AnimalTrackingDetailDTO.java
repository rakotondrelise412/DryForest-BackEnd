package org.ong.dryforest.dto.animaltracking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalTrackingDetailDTO {

    private int id_animal_tracking_detail;

    private UUID uuid;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private boolean is_synced;

    private int id_animal;

    private int id_animal_tracking;

    private boolean have_seen;
}