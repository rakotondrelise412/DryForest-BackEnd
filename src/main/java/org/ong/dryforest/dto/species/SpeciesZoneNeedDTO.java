package org.ong.dryforest.dto.species;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesZoneNeedDTO {

    private int id;

    private UUID uuid;

    private Integer speciesId;

    private Integer zoneNeedId;

    @JsonProperty("is_synced")
    private boolean is_synced;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}