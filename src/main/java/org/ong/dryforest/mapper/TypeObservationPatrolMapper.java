package org.ong.dryforest.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.observationPatrol.TypeObservationPatrolDTO;
import org.ong.dryforest.entity.TypeObservationPatrol;

public class TypeObservationPatrolMapper {
    public static TypeObservationPatrolDTO toObservationDTO(TypeObservationPatrol typeObservation){
        TypeObservationPatrolDTO typeObservationDTO = new TypeObservationPatrolDTO();

        typeObservationDTO.setId_observation(typeObservation.getId());
        typeObservationDTO.setName(typeObservation.getName());

        return typeObservationDTO;
    }

    public static List<TypeObservationPatrolDTO> toDTOList(List<TypeObservationPatrol> typeObservationPatrols){
        List<TypeObservationPatrolDTO> typeObservationPatrol = new ArrayList<>();

        typeObservationPatrol = typeObservationPatrols.stream().map(TypeObservationPatrolMapper::toObservationDTO).collect(Collectors.toList());

        return typeObservationPatrol;
    }
}
