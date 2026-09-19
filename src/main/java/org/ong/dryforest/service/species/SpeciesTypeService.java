package org.ong.dryforest.service.species;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.SpeciesType;

public interface SpeciesTypeService {
    
    SpeciesType findSpeciesTypeById(int id_species_type);

    List<SpeciesType> findAllSpeciesTypes();

    List<SpeciesType> findAllTypesUpdatedSince(LocalDateTime last_sync);

    SpeciesType CreateSpeciesType(SpeciesType speciesType);

    SpeciesType UpdateSpeciesType(SpeciesType speciesType);

    void deleteSpeciesType(SpeciesType speciesType);

}
