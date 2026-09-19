package org.ong.dryforest.dto.reforestation;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReforestationDetailDTO {
    private int id_reforestationDetail;

    private int id_species;

    private int quantity;

    private UUID uuid;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean is_synced;

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


    public int getId(){ return this.id_reforestationDetail; }
    public int getIdSpecies(){ return this.id_species; }
    public int getQuantity(){ return this.quantity; }

    public void setId(int id_reforestation){ this.id_reforestationDetail = id_reforestation; }
    public void setIdSpecies(int id_species){ this.id_species = id_species; }
    public void setQuantity(int quantity){ this.quantity = quantity; }
}
