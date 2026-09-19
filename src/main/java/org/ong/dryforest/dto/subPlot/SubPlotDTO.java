package org.ong.dryforest.dto.subPlot;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


public class SubPlotDTO {
    private int id_sub_plot;
    private UUID uuid;
    private String name;
    private double width;
    private double height;
    private Map<String, Object> location;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean is_synced;
    private boolean is_deleted;
    private int plantation_block_id;

    public int getId_sub_plot() {
        return id_sub_plot;
    }
    public void setId_sub_plot(int id_sub_plot) {
        this.id_sub_plot = id_sub_plot;
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

    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public Map<String, Object> getLocation() {
        return location;
    }
    public void setLocation(Map<String, Object> location) {
        this.location = location;
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

    public boolean isIs_deleted() {
        return is_deleted;
    }
    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int getPlantation_block_id() {
        return plantation_block_id;
    }
    public void setPlantation_block_id(int plantation_block_id) {
        this.plantation_block_id = plantation_block_id;
    }
}
