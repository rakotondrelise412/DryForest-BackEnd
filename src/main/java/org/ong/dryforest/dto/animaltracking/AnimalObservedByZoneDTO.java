package org.ong.dryforest.dto.animaltracking;

public class AnimalObservedByZoneDTO {

    private Integer idZone;
    private String zoneName;
    private Integer idAnimal;
    private String animalName;
    private Long totalObserved;

    public AnimalObservedByZoneDTO(Integer idZone, String zoneName, Integer idAnimal, String animalName, Long totalObserved) {
        this.idZone = idZone;
        this.zoneName = zoneName;
        this.idAnimal = idAnimal;
        this.animalName = animalName;
        this.totalObserved = totalObserved;
    }

    public Integer getIdZone() {
        return idZone;
    }

    public void setIdZone(Integer idZone) {
        this.idZone = idZone;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Integer getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(Integer idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public Long getTotalObserved() {
        return totalObserved;
    }

    public void setTotalObserved(Long totalObserved) {
        this.totalObserved = totalObserved;
    }
}