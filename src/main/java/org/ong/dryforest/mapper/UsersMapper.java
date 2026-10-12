package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.user.UsersDTO;
import org.ong.dryforest.entity.Users;

public class UsersMapper {

    public static UsersDTO toDTO(Users user) {

        if (user == null) {
            return null;
        }

        UsersDTO dto = new UsersDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        if (user.getPerson() != null) {
            dto.setId_person(user.getPerson().getId());
            dto.setFirstName(user.getPerson().getFirst_name());
            dto.setLastName(user.getPerson().getLast_name());
        }

        if (user.getPerson() != null) {
            dto.setEmail(user.getPerson().getEmail());
        }

        return dto;
    }
}
