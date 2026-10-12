package org.ong.dryforest.dto;

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
public class PlantingMonitoringDTO {

    private int id_planting_monitoring;

    private UUID uuid;

    private LocalDate date_planting_monitoring;

    private double diameter;

    private double height;

    private double density;

    private double carbon_sequestered;

    private String image;

    private boolean auto_generation;

    @JsonProperty("is_synced")
    private boolean synced;

    private int id_plantation;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private boolean deleted_at;
}