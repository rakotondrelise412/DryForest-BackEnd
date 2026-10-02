package org.ong.dryforest.dto.reforestation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
public class ReforestationResponseDTO {

    private int id_reforestation;

    private UUID uuid;

    private LocalDate date_reforestation;

    private int quantity;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    @JsonProperty("is_synced")
    private boolean synced;

    private int id_zone;

    private List<ReforestationDetailResponseDTO>
            reforestationDetails;
}
