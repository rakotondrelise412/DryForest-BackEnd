package org.ong.dryforest.mapper;

import org.ong.dryforest.entity.SpeciesType;
import org.ong.dryforest.dto.species.SpeciesTypeDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SpeciesTypeMapper {

    private SpeciesTypeMapper() {
    }

    public static SpeciesTypeDTO toMobileDTO(
            SpeciesType speciesType
    ) {

        if (speciesType == null) {
            return null;
        }

        SpeciesTypeDTO dto =
                new SpeciesTypeDTO();

        dto.setId(speciesType.getId());
        dto.setName(speciesType.getName());

        return dto;
    }

    public static List<SpeciesTypeDTO> toDTOList(
            List<SpeciesType> speciesTypes
    ) {

        return speciesTypes.stream()
                .map(SpeciesTypeMapper::toMobileDTO)
                .collect(Collectors.toList());
    }

    public static SpeciesType toEntity(
            SpeciesTypeDTO dto
    ) {

        if (dto == null) {
            return null;
        }

        SpeciesType speciesType =
                new SpeciesType();

        speciesType.setName(
                dto.getName()
        );

        return speciesType;
    }

    public static void updateEntity(
            SpeciesType existing,
            SpeciesTypeDTO dto
    ) {

        if (existing == null || dto == null) {
            return;
        }

        existing.setName(
                dto.getName()
        );
    }
}