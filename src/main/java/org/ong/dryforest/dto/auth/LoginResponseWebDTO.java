package org.ong.dryforest.dto.auth;

import java.time.LocalDateTime;

import lombok.*;
import org.ong.dryforest.dto.person.PersonWebDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseWebDTO {
    private int id_user;
    private String username;
    private String token;
    private LocalDateTime expires_at;
    private PersonWebDTO person;
}