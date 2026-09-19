package org.ong.dryforest.dto.patrolGroup;

public class TypeIncidentPatrolDTO {
    private int id_type_incident_patrol;
    private String name;

    public TypeIncidentPatrolDTO() {}

    public TypeIncidentPatrolDTO(int id, String name){
        this.id_type_incident_patrol = id;
        this.name = name;
    }
    
    public int getId_type_incident_patrol() {
        return id_type_incident_patrol;
    }
    public void setId_type_incident_patrol(int id_type_incident_patrol) {
        this.id_type_incident_patrol = id_type_incident_patrol;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
