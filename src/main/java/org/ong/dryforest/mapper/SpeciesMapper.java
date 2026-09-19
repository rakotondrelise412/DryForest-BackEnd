package org.ong.dryforest.mapper;

import org.ong.dryforest.entity.Species;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.species.SpeciesDTO;
import org.ong.dryforest.dto.species.SpeciesWebDTO;

public class SpeciesMapper {
    
    public static SpeciesDTO toMobileDTO(Species species) {
        SpeciesDTO speciesDTO = new SpeciesDTO();

        speciesDTO.setId(species.getId());
        speciesDTO.setMg_name(species.getMg_name());
        speciesDTO.setFr_name(species.getFr_name());
        speciesDTO.setEn_name(species.getEn_name());
        speciesDTO.setScientific_name(species.getScientific_name());
        speciesDTO.setDensity(species.getDensity());
        speciesDTO.setId_species_type(species.getType().getId());

        return speciesDTO;
    } 
    
    public static SpeciesWebDTO toWebDTO(Species species){
        SpeciesWebDTO speciesDTO = new SpeciesWebDTO();

        speciesDTO.setId(species.getId());
        speciesDTO.setMg_name(species.getMg_name());
        speciesDTO.setFr_name(species.getFr_name());
        speciesDTO.setEn_name(species.getEn_name());
        speciesDTO.setScientific_name(species.getScientific_name());

        return speciesDTO;
    }

    public static List<SpeciesDTO> toDTOList(List<Species> species) {
        List<SpeciesDTO> speciesDTO = new ArrayList<>();
        
        speciesDTO = species.stream()
                     .map(SpeciesMapper::toMobileDTO)
                     .collect(Collectors.toList());

        return speciesDTO;
    }

}
