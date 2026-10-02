package org.ong.dryforest.dto.reforestation;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReforestationDetailResponseDTO {

    private int id_reforestation_detail;

    private int id_species;

    private double quantity;

    private UUID uuid;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    @JsonProperty("is_synced")
    private boolean synced;
}