package org.ong.dryforest.dto.reforestation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReforestationDTO {

    private int id_reforestation;

    private UUID uuid;

    private LocalDate date_reforestation;

    private int quantity;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private boolean is_synced;

    private int id_zone;

    private List<ReforestationDetailDTO> reforestationDetailsDTO;
}