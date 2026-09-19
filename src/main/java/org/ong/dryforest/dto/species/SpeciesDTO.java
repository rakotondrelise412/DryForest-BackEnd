package org.ong.dryforest.dto.species;

public class SpeciesDTO {
    private int id;
    private String mg_name;
    private String fr_name;
    private String en_name;
    private String scientific_name;
    private double density;
    private int id_species_type;
    
    public SpeciesDTO() { }
    
    public SpeciesDTO(int id, String mg_name, String fr_name, String en_name, String scientific_name, double density,
            int id_species_type) {
        this.id = id;
        this.mg_name = mg_name;
        this.fr_name = fr_name;
        this.en_name = en_name;
        this.scientific_name = scientific_name;
        this.density = density;
        this.id_species_type = id_species_type;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMg_name() {
        return mg_name;
    }
    public void setMg_name(String mg_name) {
        this.mg_name = mg_name;
    }
    public String getFr_name() {
        return fr_name;
    }
    public void setFr_name(String fr_name) {
        this.fr_name = fr_name;
    }
    public String getEn_name() {
        return en_name;
    }
    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }
    public String getScientific_name() {
        return scientific_name;
    }
    public void setScientific_name(String scientific_name) {
        this.scientific_name = scientific_name;
    }
    public double getDensity() {
        return density;
    }
    public void setDensity(double density) {
        this.density = density;
    }
    public int getId_species_type() {
        return id_species_type;
    }
    public void setId_species_type(int id_species_type) {
        this.id_species_type = id_species_type;
    }
}
