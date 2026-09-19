package org.ong.dryforest.dto.zone;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class ZoneDTO {
    private int id_zone;
    private UUID uuid;
    private String name;
    private double area;
    private Map<String, Object> geom;
    private int id_type_zone;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean is_synced;
    
    public int getId_zone() {
        return id_zone;
    }
    
    public void setId_zone(int id_zone) {
        this.id_zone = id_zone;
    }

    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
    
    public Map<String, Object> getGeom() {
        return geom;
    }
    
    public void setGeom(Map<String, Object> geom) {
        this.geom = geom;
    }
    
    public int getId_type_zone() {
        return id_type_zone;
    }

    public void setId_type_zone(int id_type_zone) {
        this.id_type_zone = id_type_zone;
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
}
