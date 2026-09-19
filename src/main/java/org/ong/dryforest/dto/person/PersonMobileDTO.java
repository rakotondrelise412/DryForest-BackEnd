package org.ong.dryforest.dto.person;

public class PersonMobileDTO {
    private int id;
    private String last_name;
    private String first_name;
    private int id_site;
    private int id_role;

    public PersonMobileDTO() { }

    public PersonMobileDTO(int id, String last_name, String first_name, int id_site, int id_role) {
        this.id = id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.id_site = id_site;
        this.id_role = id_role;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public int getId_site() {
        return id_site;
    }
    public void setId_site(int id_site) {
        this.id_site = id_site;
    }
    public int getId_role() {
        return id_role;
    }
    public void setId_role(int id_role) {
        this.id_role = id_role;
    }
}
