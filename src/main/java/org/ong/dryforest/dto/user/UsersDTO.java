package org.ong.dryforest.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
