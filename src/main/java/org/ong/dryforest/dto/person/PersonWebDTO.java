package org.ong.dryforest.dto.person;

import java.time.LocalDateTime;

import org.ong.dryforest.dto.site.SiteWebDTO;
import org.ong.dryforest.entity.Gender;
import org.ong.dryforest.entity.Role;

import lombok.Data;

@Data
public class PersonWebDTO {
    private int id;
    private String last_name;
    private String first_name;
    private String email;
    private String phone_number;
    private String address;
    private boolean is_deleted;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Gender gender;
    private Role role;
    private SiteWebDTO site;
}