package org.ong.dryforest.dto.severity;

public class SeverityDTO {
    private int id;
    private String name;

    public SeverityDTO() { }

    public SeverityDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
