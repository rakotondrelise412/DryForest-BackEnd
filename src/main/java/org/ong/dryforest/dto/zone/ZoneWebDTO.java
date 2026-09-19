package org.ong.dryforest.dto.zone;

import java.util.Map;

public class ZoneWebDTO {
    private int id_zone;
    private String name;
    private double area;
    private Map<String, Object> geom;
    private int id_type_zone;
    
    public int getId_zone() {
        return id_zone;
    }
    
    public void setId_zone(int id_zone) {
        this.id_zone = id_zone;
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
}
