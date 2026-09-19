package org.ong.dryforest.dto.animaltracking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AnimalTrackingDTO {
    private int id_animal_tracking;
    private UUID uuid;
    private Map<String, Object> location;
    private LocalDateTime date_tracking;
    private boolean have_seen;
    private String description;
    private LocalDateTime created_at; 
    private LocalDateTime updated_at;
    private boolean is_synced;
    private int id_plantation_block;
    private List<AnimalTrackingDetailDTO> animal_tracking_details;

    public int getId_animal_tracking() {
        return id_animal_tracking;
    }
    public void setId_animal_tracking(int id_animal_tracking) {
        this.id_animal_tracking = id_animal_tracking;
    }
    
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Map<String, Object> getLocation() {
        return location;
    }
    public void setLocation(Map<String, Object> location) {
        this.location = location;
    }

    public LocalDateTime getDate_tracking() {
        return date_tracking;
    }
    public void setDate_tracking(LocalDateTime date_tracking) {
        this.date_tracking = date_tracking;
    }

    public boolean isHave_seen() {
        return have_seen;
    }
    public void setHave_seen(boolean have_seen) {
        this.have_seen = have_seen;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }
    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isIs_synced() {
        return is_synced;
    }
    public void setIs_synced(boolean is_synced) {
        this.is_synced = is_synced;
    }

    public int getId_plantation_block() {
        return id_plantation_block;
    }
    public void setId_plantation_block(int id_plantation_block) {
        this.id_plantation_block = id_plantation_block;
    }

    public List<AnimalTrackingDetailDTO> getAnimal_tracking_details() {
        return animal_tracking_details;
    }
    public void setAnimal_tracking_details(List<AnimalTrackingDetailDTO> animal_tracking_details) {
        this.animal_tracking_details = animal_tracking_details;
    }

}
