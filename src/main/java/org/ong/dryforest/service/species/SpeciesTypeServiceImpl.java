package org.ong.dryforest.service.species;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.SpeciesType;
import org.ong.dryforest.repository.SpeciesTypeRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class SpeciesTypeServiceImpl implements SpeciesTypeService {
    @Autowired
    private SpeciesTypeRepository speciesTypeRepository;

    @Override
    public SpeciesType findSpeciesTypeById(int id_species_type) {
        return speciesTypeRepository.findByIdAndIsDeletedFalse(id_species_type)
                .orElseThrow(() -> new RuntimeException("Type d'espèce '" + id_species_type + "' introuvable"));
    }

    @Override
    public List<SpeciesType> findAllSpeciesTypes() {
        return speciesTypeRepository.findAllByIsDeletedFalse();
    }

    @Override
    public List<SpeciesType> findAllTypesUpdatedSince(LocalDateTime last_sync) {
        return speciesTypeRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public SpeciesType CreateSpeciesType(SpeciesType speciesType) {
        try {
            return speciesTypeRepository.save(speciesType);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Type d'espèce déjà existant");
        }
    }

    @Override
    public SpeciesType UpdateSpeciesType(SpeciesType speciesType) {
        findSpeciesTypeById(speciesType.getId());

        return speciesTypeRepository.save(speciesType);
    }

    @Override
    public void deleteSpeciesType(SpeciesType speciesType) {
        findSpeciesTypeById(speciesType.getId());

        try {
            speciesTypeRepository.delete(speciesType);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer ce type d'espèces");
        }
    }
    
}
