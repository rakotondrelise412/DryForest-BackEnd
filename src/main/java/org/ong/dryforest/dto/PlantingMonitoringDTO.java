package org.ong.dryforest.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class PlantingMonitoringDTO {
    private int id_planting_monitoring;
    private UUID uuid;
    private LocalDate date_planting_monitoring;
    private double diameter;
    private double height;
    private String image;
    private int auto_generation;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean is_synced;
    private boolean deletedAt;
    private int id_plantation;

    public int getId_planting_monitoring(){ return this.id_planting_monitoring; }
    public void setId_planting_monitoring(int id_planting_monitoring){this.id_planting_monitoring = id_planting_monitoring; }

    public UUID getUuid() {
        return uuid;
    }
    
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDate getDate_planting_monitoring() {
        return date_planting_monitoring;
    }

    public void setDate_planting_monitoring(LocalDate date_planting_monitoring) {
        this.date_planting_monitoring = date_planting_monitoring;
    }
    
    public double getDiameter() {
        return diameter;
    }
    
    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getHeight() {
        return height;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAuto_generation() {
        return auto_generation;
    }
    public void setAuto_generation(int auto_generation) {
        this.auto_generation = auto_generation;
    }

    public boolean isDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(boolean deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getCreatedAt(){ return this.created_at; }

    public LocalDateTime getUpDateAt(){ return this.updated_at; }
    
    public boolean getIsSynced(){ return this.is_synced; }

    public void setCreatedAt(LocalDateTime created_at){ this.created_at =created_at; }

    public void setUpdatedAt(LocalDateTime updated_at){ this.updated_at = updated_at; }

    public void setIsSynced(boolean is_synced){ this.is_synced = is_synced; }

    public int getId_plantation(){ return this.id_plantation; }

    public void setId_plantation(int id_plantation){ this.id_plantation = id_plantation; }
}
