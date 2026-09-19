package org.ong.dryforest.dto.auth;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LoginResponseMobileDTO {
    private int id_user;
    private String username;
    private int id_person;
    private String role;
    private String token;
    private LocalDateTime expires_at;
}