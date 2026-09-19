package org.ong.dryforest.service.zone;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.entity.SpeciesZoneNeed;
import org.ong.dryforest.repository.SpeciesZoneNeedRepository;
import org.ong.dryforest.service.species.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SpeciesZoneNeedServiceImpl implements SpeciesZoneNeedService{
    @Autowired
    SpeciesZoneNeedRepository speciesZoneNeedRepository;
    @Autowired
    SpeciesService speciesService;
    @Autowired
    ZoneNeedService zoneNeedService;

    @Override
    public SpeciesZoneNeed createSpeciesZoneNeed(SpeciesZoneNeed speciesZoneNeed){
        try {
            speciesZoneNeed.set_synced(true);
            return speciesZoneNeedRepository.save(speciesZoneNeed);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Détail du bésoin de la zone déjà existant");
        }
    }

    @Override
    public List<SpeciesZoneNeed> findAll(){ return speciesZoneNeedRepository.findAllByIsDeletedFalse(); }
    
    @Override
    public SpeciesZoneNeed findById(int id){
        return speciesZoneNeedRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new RuntimeException("Détail du bésoin de la zone introuvable"));
    }

    @Override
    public SpeciesZoneNeed findByUuid(UUID uuid){
        return speciesZoneNeedRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Détail du besoin de la zone introuvable"));
    }

    @Override
    public SpeciesZoneNeed updateSpeciesZoneNeed(SpeciesZoneNeed speciesZoneNeed){
        findById(speciesZoneNeed.getId());
        return speciesZoneNeedRepository.save(speciesZoneNeed);
    }

    @Override
    public void deleteSpeciesZoneNeed(SpeciesZoneNeed speciesZoneNeed){
        try {
            findById(speciesZoneNeed.getId());
            speciesZoneNeedRepository.delete(speciesZoneNeed);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cet détail bésoin de la zone");
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid){
        return speciesZoneNeedRepository.existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public SpeciesZoneNeed mapToEntity(Map<String, Object> speciesZoneNeedMapping){
        SpeciesZoneNeed speciesZoneNeed = new SpeciesZoneNeed();

        speciesZoneNeed.setUuid(UUID.fromString((String)speciesZoneNeedMapping.get("uuid")));
        speciesZoneNeed.setSpecies(speciesService.findSpeciesById(((Number)speciesZoneNeedMapping.get("id_species")).intValue()));
        speciesZoneNeed.setZoneNeed(zoneNeedService.findById(((Number)speciesZoneNeedMapping.get("id_zone_need")).intValue()));
        speciesZoneNeed.setCreatedAt(LocalDateTime.parse((String)speciesZoneNeedMapping.get("created_at")));
        speciesZoneNeed.setUpdatedAt(LocalDateTime.parse((String)speciesZoneNeedMapping.get("updated_at")));
        speciesZoneNeed.set_synced((boolean)speciesZoneNeedMapping.get("is_synced"));

        return speciesZoneNeed;
    }
}
