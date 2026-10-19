package org.ong.dryforest.dto.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonMobileDTO {

    private int id;
    private String last_name;
    private String first_name;
    private String email;
    private String phone_number;
    private String address;
    private Integer id_gender;
    private Integer id_site;
    private Integer id_role;
}