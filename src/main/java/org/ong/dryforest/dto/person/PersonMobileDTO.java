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
    private int id_site;
    private int id_role;
}
