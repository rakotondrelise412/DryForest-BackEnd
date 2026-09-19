package org.ong.dryforest.dto.auth;

public class RegisterRequestDTO {
    private int id_person;
    private String password;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getId_person() {
        return id_person;
    }
    public void setId_person(int id_person) {
        this.id_person = id_person;
    }
}
