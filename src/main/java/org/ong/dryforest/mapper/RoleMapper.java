package org.ong.dryforest.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.role.RoleDTO;
import org.ong.dryforest.entity.Role;

public class RoleMapper {
    
    public static RoleDTO toMobileDTO(Role role){
        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setID(role.getId());
        roleDTO.setName(role.getName());

        return roleDTO;
    }

    public static List<RoleDTO> toDTOList(List<Role> roles){
        List<RoleDTO> roleDTO = new ArrayList<>();

        roleDTO = roles.stream()
                    .map(RoleMapper::toMobileDTO)
                    .collect(Collectors.toList());
        
        return roleDTO;
    }
}
