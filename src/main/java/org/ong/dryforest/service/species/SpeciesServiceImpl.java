package org.ong.dryforest.service.species;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.Species;
import org.ong.dryforest.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SpeciesServiceImpl implements SpeciesService{
    @Autowired
    private SpeciesRepository speciesRepository;

    @Override
    public Species findSpeciesById(int id_species) {
        return speciesRepository.findByIdAndIsDeletedFalse(id_species)
                .orElseThrow(() -> new RuntimeException("Espèce '" + id_species + "' introuvable"));
    }

    @Override
    public List<Species> findAllSpecies() {
        return speciesRepository.findAllByIsDeletedFalse();
    }

    @Override
    public List<Species> findAllSpeciesUpdatedSince(LocalDateTime last_sync) {
        return speciesRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public List<Species> findAllSpeciesById(List<Integer> id_species) {
        return speciesRepository.findAllById(id_species); 
    }

    @Override
    public List<Species> findAllSpeciesByType(int id_species_type) {
        return speciesRepository.findAllByType_IdAndIsDeletedFalse(id_species_type);
    }

    @Override
    public Species createSpecies(Species species) {
        try {
            return speciesRepository.save(species);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Espèce déjà existant");
        }
    }

    @Override
    public Species updateSpecies(Species species) {
        findSpeciesById(species.getId());

        return speciesRepository.save(species);
    }

    @Override
    public void deleteSpecies(Species species) {
        findSpeciesById(species.getId());

        try {
            speciesRepository.delete(species);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Impossible de supprimer cette espèce");
        }
    }

    
}
