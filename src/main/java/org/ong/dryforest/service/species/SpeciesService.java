package org.ong.dryforest.service.species;

import org.ong.dryforest.dto.species.SpeciesDTO;
import org.ong.dryforest.entity.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface SpeciesService {

    Page<SpeciesDTO> findAllSpecies(Pageable pageable);

    SpeciesDTO findSpeciesById(int id);

    Species findSpeciesEntityById(int id);

    List<SpeciesDTO> findAllSpeciesByType(int id_species_type);

    List<SpeciesDTO> findAllSpeciesById(List<Integer> ids);

    List<SpeciesDTO> findAllSpeciesUpdatedSince(LocalDateTime last_sync);

    SpeciesDTO createSpecies(SpeciesDTO dto);

    SpeciesDTO updateSpecies(int id, SpeciesDTO dto);

    void deleteSpecies(int id);
}