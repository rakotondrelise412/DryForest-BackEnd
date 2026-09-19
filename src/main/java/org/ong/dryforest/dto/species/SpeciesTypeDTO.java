package org.ong.dryforest.dto.species;

public class SpeciesTypeDTO {
    private int id;
    private String name;
    
    public SpeciesTypeDTO() { }
    
    public SpeciesTypeDTO(int id, String name) {
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
