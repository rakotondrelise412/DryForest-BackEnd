package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.species.SpeciesDTO;
import org.ong.dryforest.dto.species.SpeciesWebDTO;
import org.ong.dryforest.entity.Species;
import org.ong.dryforest.entity.SpeciesType;

import java.util.List;

public class SpeciesMapper {

    private SpeciesMapper() {
    }

    public static SpeciesDTO toMobileDTO(Species species) {

        if (species == null) {
            return null;
        }

        SpeciesDTO dto = new SpeciesDTO();

        dto.setId(species.getId());
        dto.setMg_name(species.getMg_name());
        dto.setFr_name(species.getFr_name());
        dto.setEn_name(species.getEn_name());
        dto.setScientific_name(species.getScientific_name());
        dto.setDensity(species.getDensity());

        if (species.getType() != null) {
            dto.setId_species_type(
                    species.getType().getId()
            );
        }

        return dto;
    }

    public static SpeciesWebDTO toWebDTO(Species species) {

        if (species == null) {
            return null;
        }

        SpeciesWebDTO dto = new SpeciesWebDTO();

        dto.setId(species.getId());
        dto.setMg_name(species.getMg_name());
        dto.setFr_name(species.getFr_name());
        dto.setEn_name(species.getEn_name());
        dto.setScientific_name(species.getScientific_name());

        return dto;
    }

    public static Species toEntity(
            SpeciesDTO dto,
            SpeciesType speciesType
    ) {

        if (dto == null) {
            return null;
        }

        Species species = new Species();

        species.setMg_name(dto.getMg_name());
        species.setFr_name(dto.getFr_name());
        species.setEn_name(dto.getEn_name());
        species.setScientific_name(dto.getScientific_name());
        species.setDensity(dto.getDensity());
        species.setType(speciesType);

        return species;
    }

    public static void updateEntity(
            Species species,
            SpeciesDTO dto,
            SpeciesType speciesType
    ) {

        species.setMg_name(dto.getMg_name());
        species.setFr_name(dto.getFr_name());
        species.setEn_name(dto.getEn_name());
        species.setScientific_name(dto.getScientific_name());
        species.setDensity(dto.getDensity());
        species.setType(speciesType);
    }

    public static List<SpeciesDTO> toMobileDTOList(
            List<Species> species
    ) {

        return species.stream()
                .map(SpeciesMapper::toMobileDTO)
                .toList();
    }

    public static List<SpeciesWebDTO> toWebDTOList(
            List<Species> species
    ) {

        return species.stream()
                .map(SpeciesMapper::toWebDTO)
                .toList();
    }
}