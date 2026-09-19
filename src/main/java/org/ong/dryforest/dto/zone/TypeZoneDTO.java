package org.ong.dryforest.dto.zone;

public class TypeZoneDTO {
    private int id_type_zone;
    private String name;

    public TypeZoneDTO() {}

    public TypeZoneDTO(int id, String name){
        this.id_type_zone = id;
        this.name = name;
    }

    public int getId_type_zone() {
        return id_type_zone;
    }

    public void setId_type_zone(int id_type_zone) {
        this.id_type_zone = id_type_zone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
