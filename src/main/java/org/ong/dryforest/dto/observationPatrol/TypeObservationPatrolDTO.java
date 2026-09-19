package org.ong.dryforest.dto.observationPatrol;

public class TypeObservationPatrolDTO {
    private int id_observation;
    private String name;

    public TypeObservationPatrolDTO() {}

    public TypeObservationPatrolDTO(int id, String name){
        this.id_observation = id;
        this.name = name;
    }
    
    public int getId_observation() {
        return id_observation;
    }
    public void setId_observation(int id_observation) {
        this.id_observation = id_observation;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
