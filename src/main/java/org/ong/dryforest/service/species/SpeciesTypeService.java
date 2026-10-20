package org.ong.dryforest.service.species;

import org.ong.dryforest.dto.species.SpeciesTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface SpeciesTypeService {

    Page<SpeciesTypeDTO> findAllSpeciesTypes(Pageable pageable);

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