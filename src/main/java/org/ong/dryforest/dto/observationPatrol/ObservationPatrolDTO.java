package org.ong.dryforest.dto.observationPatrol;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class ObservationPatrolDTO {
    private int id_observation_patrol;
    private UUID uuid;
    private LocalDateTime date_observation;
    private String description;
    private int id_type_observation_patrol;
    private int id_patrol_group;
    private int id_zone;
    private int id_user;
    private LocalDateTime created_at; 
    private LocalDateTime updated_at;
    private boolean is_synced;
    private Map<String, Object> location;

    public int getId_observation_patrol() {
        return id_observation_patrol;
    }
    public void setId_observation_patrol(int id_observation_patrol) {
        this.id_observation_patrol = id_observation_patrol;
    }
    
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    
    public LocalDateTime getDate_observation() {
        return date_observation;
    }
    public void setDate_observation(LocalDateTime date_observation) {
        this.date_observation = date_observation;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_type_observation_patrol() {
        return id_type_observation_patrol;
    }
    public void setId_type_observation_patrol(int id_type_observation_patrol) {
        this.id_type_observation_patrol = id_type_observation_patrol;
    }
    
    public int getId_patrol_group() {
        return id_patrol_group;
    }
    public void setId_patrol_group(int id_patrol_group) {
        this.id_patrol_group = id_patrol_group;
    }
    public int getId_zone() {
        return id_zone;
    }
    public void setId_zone(int id_zone) {
        this.id_zone = id_zone;
    }
    public int getId_user() {
        return id_user;
    }
    public void setId_user(int id_user) {
        this.id_user = id_user;
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

    public Map<String, Object> getLocation() {
        return location;
    }
    public void setLocation(Map<String, Object> location) {
        this.location = location;
    }
}
