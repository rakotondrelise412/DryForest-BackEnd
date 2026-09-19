package org.ong.dryforest.dto.patrolGroup;

public class PatrolGroupDTO {
    
    private int id_patrol_group;
    private String name;

    public PatrolGroupDTO(){}

    public PatrolGroupDTO(int id, String name){
        this.id_patrol_group = id;
        this.name = name;
    }
    
    public int getId_patrol_group() {
        return id_patrol_group;
    }
    public void setId_patrol_group(int id_patrol_group) {
        this.id_patrol_group = id_patrol_group;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
