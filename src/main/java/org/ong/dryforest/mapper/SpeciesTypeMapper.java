package org.ong.dryforest.mapper;

import org.ong.dryforest.entity.SpeciesType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.species.SpeciesTypeDTO;

public class SpeciesTypeMapper {
    
    public static SpeciesTypeDTO toMobileDTO(SpeciesType speciesType) {
        SpeciesTypeDTO speciesTypeDTO = new SpeciesTypeDTO();

        speciesTypeDTO.setId(speciesType.getId());
        speciesTypeDTO.setName(speciesType.getName());

        return speciesTypeDTO;
    }

    public static List<SpeciesTypeDTO> toDTOList(List<SpeciesType> speciesTypes) {
        List<SpeciesTypeDTO> typesDTO = new ArrayList<>();
        
        typesDTO = speciesTypes.stream()
                   .map(SpeciesTypeMapper::toMobileDTO)
                   .collect(Collectors.toList());

        return typesDTO;
    }

}
