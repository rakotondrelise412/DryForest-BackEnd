package org.ong.dryforest.dto.plantation;

public class PlantationMobileDTO {
    
    private int id_plantation;
    private String plant_number;
    private int id_reforestation;
    private int id_species;
    private int id_sub_plot;

    public int getId_plantation() {
        return id_plantation;
    }

    public void setId_plantation(int id_plantation) {
        this.id_plantation = id_plantation;
    }

    public String getPlant_number() {
        return plant_number;
    }

    public void setPlant_number(String plant_number) {
        this.plant_number = plant_number;
    }

    public int getId_reforestation() {
        return id_reforestation;
    }
    
    public void setId_reforestation(int id_reforestation) {
        this.id_reforestation = id_reforestation;
    }
    public int getId_species() {
        return id_species;
    }

    public void setId_species(int id_species) {
        this.id_species = id_species;
    }
    public int getId_sub_plot() {
        return id_sub_plot;
    }

    public void setId_sub_plot(int id_sub_plot) {
        this.id_sub_plot = id_sub_plot;
    }
}
