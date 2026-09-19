package org.ong.dryforest.dto.animaltracking;

public class AnimalDTO {
    private int id_animal;
    private String name;
    private int id_category_animal;

    public AnimalDTO() {}

    public AnimalDTO(int id, String name, int id_category_animal){
        this.id_animal = id;
        this.name = name;
        this.id_category_animal = id_category_animal;
    }
    
    public int getId_animal() {
        return id_animal;
    }
    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId_category_animal() {
        return id_category_animal;
    }
    public void setId_category_animal(int id_category_animal) {
        this.id_category_animal = id_category_animal;
    }
}
