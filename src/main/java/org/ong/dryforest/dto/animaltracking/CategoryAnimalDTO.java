package org.ong.dryforest.dto.animaltracking;

public class CategoryAnimalDTO {
    private int id;
    private String name;

    public CategoryAnimalDTO() {}

    public CategoryAnimalDTO(int id, String name){
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
