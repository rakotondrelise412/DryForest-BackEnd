package org.ong.dryforest.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.zone.TypeZoneDTO;
import org.ong.dryforest.entity.TypeZone;

public class TypeZoneMapper {
    
    public static TypeZoneDTO toTypeZone(TypeZone typeZone){
        TypeZoneDTO typeZoneDTO = new TypeZoneDTO();

        typeZoneDTO.setId_type_zone(typeZone.getId());
        typeZoneDTO.setName(typeZone.getName());

        return typeZoneDTO;
    }

    public static List<TypeZoneDTO> toDTOList(List<TypeZone> typeZone){
        List<TypeZoneDTO> typeZoneDTO = new ArrayList<>();

        typeZoneDTO = typeZone.stream()
                        .map(TypeZoneMapper::toTypeZone)
                        .collect(Collectors.toList());

        return typeZoneDTO;
    }
   
}
