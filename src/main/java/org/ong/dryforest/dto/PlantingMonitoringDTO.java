package org.ong.dryforest.dto;

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

    private String image;

    private boolean auto_generation;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private boolean is_synced;

    private boolean deletedAt;

    private int id_plantation;

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.created_at = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updated_at = updatedAt;
    }

    public LocalDateTime getUpDateAt() {
        return updated_at;
    }

    public void setUpDateAt(LocalDateTime updatedAt) {
        this.updated_at = updatedAt;
    }

    public boolean getIsSynced() {
        return is_synced;
    }

    public void setIsSynced(boolean synced) {
        this.is_synced = synced;
    }
}

