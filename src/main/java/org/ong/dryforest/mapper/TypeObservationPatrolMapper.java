package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.observationPatrol.TypeObservationPatrolDTO;
import org.ong.dryforest.entity.TypeObservationPatrol;

import java.util.List;
import java.util.stream.Collectors;

public class TypeObservationPatrolMapper {

    public static TypeObservationPatrolDTO toObservationDTO(
            TypeObservationPatrol typeObservation
    ) {

        TypeObservationPatrolDTO dto =
                new TypeObservationPatrolDTO();

        dto.setId_observation(typeObservation.getId());
        dto.setName(typeObservation.getName());

        return dto;
    }

    public static List<TypeObservationPatrolDTO> toDTOList(
            List<TypeObservationPatrol> typeObservationPatrols
    ) {

        return typeObservationPatrols.stream()
                .map(TypeObservationPatrolMapper::toObservationDTO)
                .collect(Collectors.toList());
    }
}