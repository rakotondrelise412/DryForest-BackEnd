package org.ong.dryforest.dto.plantation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class PlantationDTO {
    private int id_plantation;
    private UUID uuid;
    private String plant_number;
    private LocalDate date_plantation;
    private double diameter;
    private double height;
    private String image;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean is_synced;
    private int id_reforestation;
    private int id_species;
    private int id_sub_plot;
    
    public int getId_plantation() {
        return id_plantation;
    }

    public void setId_plantation(int id_plantation) {
        this.id_plantation = id_plantation;
    }
    
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPlant_number() {
        return plant_number;
    }

    public void setPlant_number(String plant_number) {
        this.plant_number = plant_number;
    }

    public LocalDate getDate_plantation() {
        return date_plantation;
    }

    public void setDate_plantation(LocalDate date_plantation) {
        this.date_plantation = date_plantation;
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

    public LocalDateTime getCreatedAt(){ return this.created_at; }

    public LocalDateTime getUpDateAt(){ return this.updated_at; }

    public boolean getIsSynced(){ return this.is_synced; }

    public void setCreatedAt(LocalDateTime created_at){ this.created_at =created_at; }

    public void setUpdatedAt(LocalDateTime updated_at){ this.updated_at = updated_at; }

    public void setIsSynced(boolean is_synced){ this.is_synced = is_synced; }

    public int getId_reforestation() {
        return id_reforestation;
    }
    
    public void setId_reforestation(int id_reforestation) {
        this.id_reforestation = id_reforestation;
    }
    public int getId_species() {
        return id_species;
    }

    public void setId_species(int id_species) {
        this.id_species = id_species;
    }
    public int getId_sub_plot() {
        return id_sub_plot;
    }

    public void setId_sub_plot(int id_sub_plot) {
        this.id_sub_plot = id_sub_plot;
    }
}
