package org.ong.dryforest.dto.reforestation;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReforestationDetailDTO {

    private int id_reforestation_detail;

    private int id_reforestation;

    private int id_species;

    private double quantity;

    private UUID uuid;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private boolean is_synced;
}
