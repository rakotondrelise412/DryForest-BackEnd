package org.ong.dryforest.dto.plantation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantationDTO {

    private int id_plantation;

    private UUID uuid;

    private String plant_number;

    private LocalDate date_plantation;

    private double diameter;

    private double height;

    private String image;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("is_synced")
    private boolean isSynced;

    private int id_reforestation;

    private int id_species;

    private int id_sub_plot;
}