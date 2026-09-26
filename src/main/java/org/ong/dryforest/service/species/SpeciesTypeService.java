package org.ong.dryforest.service.species;

import org.ong.dryforest.dto.species.SpeciesTypeDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface SpeciesTypeService {

    List<SpeciesTypeDTO> findAllSpeciesTypes();

    SpeciesTypeDTO findSpeciesTypeById(int id);

    List<SpeciesTypeDTO> findAllTypesUpdatedSince(
            LocalDateTime last_sync
    );

    SpeciesTypeDTO createSpeciesType(
            SpeciesTypeDTO dto
    );

    SpeciesTypeDTO updateSpeciesType(
            int id,
            SpeciesTypeDTO dto
    );

    void deleteSpeciesType(int id);
}