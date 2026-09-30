package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.role.RoleDTO;
import org.ong.dryforest.entity.Role;

import java.util.List;

public class RoleMapper {

    private RoleMapper() {
    }

    public static RoleDTO toDTO(Role role) {

        if (role == null) {
            return null;
        }

        return new RoleDTO(
                role.getId(),
                role.getName()
        );
    }

    public static List<RoleDTO> toDTOList(List<Role> roles) {

        return roles.stream()
                .map(RoleMapper::toDTO)
                .toList();
    }

    public static Role toEntity(RoleDTO dto) {

        if (dto == null) {
            return null;
        }

        Role role = new Role();

        role.setId(dto.getId());
        role.setName(dto.getName());

        return role;
    }
}