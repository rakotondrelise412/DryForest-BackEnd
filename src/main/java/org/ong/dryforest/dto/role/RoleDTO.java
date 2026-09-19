package org.ong.dryforest.dto.role;

public class RoleDTO {
    private int id;
    private String name;

    public RoleDTO() {}

    public RoleDTO(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){ return this.id; }

    public void setID(int id){ this.id = id; }

    public String getName() { return this.name; }

    public void setName(String name){ this.name = name; }
}
