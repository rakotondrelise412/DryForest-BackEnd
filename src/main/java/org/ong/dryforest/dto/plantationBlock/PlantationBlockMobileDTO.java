package org.ong.dryforest.dto.plantationBlock;

public class PlantationBlockMobileDTO {
    private int id_plantation_block;
    private String name;
    private int id_zone;
    
    public int getId_plantation_block() {
        return id_plantation_block;
    }
    public void setId_plantation_block(int id_plantation_block) {
        this.id_plantation_block = id_plantation_block;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId_zone() {
        return id_zone;
    }
    public void setId_zone(int id_zone) {
        this.id_zone = id_zone;
    }
}
