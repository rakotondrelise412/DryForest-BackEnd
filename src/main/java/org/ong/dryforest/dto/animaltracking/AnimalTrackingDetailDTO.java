package org.ong.dryforest.dto.animaltracking;

import java.time.LocalDateTime;
import java.util.UUID;

public class AnimalTrackingDetailDTO {
    private int id_animal_tracking_detail;
    private UUID uuid;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean is_synced;
    private int id_animal;
    private boolean have_seen;
    
    public int getId_animal_tracking_detail() {
        return id_animal_tracking_detail;
    }
    public void setId_animal_tracking_detail(int id_animal_tracking_detail) {
        this.id_animal_tracking_detail = id_animal_tracking_detail;
    }
    
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
    
    public int getId_animal() {
        return id_animal;
    }
    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }

    public boolean isHave_seen() {
        return have_seen;
    }

    public void setHave_seen(boolean have_seen) {
        this.have_seen = have_seen;
    }
}
