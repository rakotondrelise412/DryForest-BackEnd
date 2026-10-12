package org.ong.dryforest.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {

    private int id;

    private int id_person;

    private String username;

    private String email;

    private String firstName;

    private String lastName;
}
