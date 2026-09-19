package org.ong.dryforest.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.patrolGroup.TypeIncidentPatrolDTO;
import org.ong.dryforest.entity.TypeIncidentPatrol;

public class TypeIncidentPatrolMapper {
    public static TypeIncidentPatrolDTO toTypeIncidentPatrolDTO(TypeIncidentPatrol typeIncidentPatrol){
        TypeIncidentPatrolDTO typeIncidentPatrolDTO = new TypeIncidentPatrolDTO();

        typeIncidentPatrolDTO.setId_type_incident_patrol(typeIncidentPatrol.getId());
        typeIncidentPatrolDTO.setName(typeIncidentPatrol.getName());

        return typeIncidentPatrolDTO;
    }

    public static List<TypeIncidentPatrolDTO> toDTOlList(List<TypeIncidentPatrol> typeIncidentPatrol){
        List<TypeIncidentPatrolDTO> typeIncidentPatrolDTO = new ArrayList<>();

        typeIncidentPatrolDTO = typeIncidentPatrol.stream().map(TypeIncidentPatrolMapper::toTypeIncidentPatrolDTO).collect(Collectors.toList());

        return typeIncidentPatrolDTO;
    }
}
