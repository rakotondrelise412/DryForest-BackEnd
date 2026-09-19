package org.ong.dryforest.service.species;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.Species;

public interface SpeciesService {

    Species findSpeciesById(int id_species);

    List<Species> findAllSpecies();

    List<Species> findAllSpeciesUpdatedSince(LocalDateTime last_sync);

    List<Species> findAllSpeciesById(List<Integer> id_species);

    List<Species> findAllSpeciesByType(int id_species_type);

    Species createSpecies(Species species);

    Species updateSpecies(Species species);

    void deleteSpecies(Species species);

    
}
