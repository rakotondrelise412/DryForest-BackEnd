package org.ong.dryforest.dto.reforestation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ReforestationDTO {
    private int id_reforestation;
    private UUID uuid;
    private LocalDate date_reforesation;
    private int quantity;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean is_synced;
    private int id_zone;
    private List<ReforestationDetailDTO> reforestationDetailsDTO;

    public int getId(){ return this.id_reforestation; }

    public UUID getUUID(){ return this.uuid; }

    public LocalDate getDateReforestation(){ return date_reforesation; }

    public int getQuantity(){ return this.quantity; }

    public LocalDateTime getCreatedAt(){ return this.created_at; }

    public LocalDateTime getUpDateAt(){ return this.updated_at; }

    public boolean getIsSynced(){ return this.is_synced; }

    public int getIdZone(){ return this.id_zone; }

    public List<ReforestationDetailDTO> getReforestationDetail(){ return this.reforestationDetailsDTO; }

    public void setId(int id_reforestation){ this.id_reforestation = id_reforestation; }

    public void setUUID(UUID uuid){ this.uuid = uuid; }

    public void setDateReforestation(LocalDate date_reforestation){ this.date_reforesation = date_reforestation; }

    public void setQuantity(int quantity){ this.quantity = quantity; }

    public void setCreatedAt(LocalDateTime created_at){ this.created_at =created_at; }

    public void setUpdatedAt(LocalDateTime updated_at){ this.updated_at = updated_at; }

    public void setIsSynced(boolean is_synced){ this.is_synced = is_synced; }

    public void setIdZone(int id_zone){ this.id_zone = id_zone; }

    public void setReforestationDetailDTO(List<ReforestationDetailDTO> referorestationDetailsDTO){ this.reforestationDetailsDTO = referorestationDetailsDTO;}
}
