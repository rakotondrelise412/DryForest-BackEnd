package org.ong.dryforest.service.zone;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.entity.ZoneNeed;
import org.ong.dryforest.repository.ZoneNeedRepository;
import org.ong.dryforest.service.species.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ZoneNeedServiceImpl implements ZoneNeedService{
    @Autowired
    ZoneNeedRepository zoneNeedRepository;
    @Autowired
    SpeciesService speciesService;
    @Autowired
    ZoneService zoneService;
    
    @Override
    public ZoneNeed createZoneNeed(ZoneNeed zone_need){
        try {
            zone_need.set_synced(true);
            return zoneNeedRepository.save(zone_need);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Bésoin de la zone déjà existant");
        }
    }

    @Override
    public List<ZoneNeed> findAll(){
        return zoneNeedRepository.findAllByIsDeletedFalse();
    }

    @Override
    public ZoneNeed findById(int id){
        return zoneNeedRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new RuntimeException("Besion de la zone introuvable."));
    }

    @Override
    public ZoneNeed findByUuid(UUID uuid){
        return zoneNeedRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Besion de la zone introuvable."));
    }

    @Override
    public ZoneNeed updatZoneNeed(ZoneNeed zoneNeed){
        findById(zoneNeed.getId());
        return zoneNeedRepository.save(zoneNeed);
    }

    @Override
    public void deleteZoneNeed(ZoneNeed zoneNeed){
        try {
            findById(zoneNeed.getId());
            zoneNeedRepository.delete(zoneNeed);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer ce besoin de la zone");
        }
    }
    
    @Override
    public boolean existsByUuid(UUID uuid){
        return zoneNeedRepository.existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public ZoneNeed mapToEntity(Map<String, Object> zoneNeedMapping){
        ZoneNeed zoneNeed = new ZoneNeed();

        zoneNeed.setUuid(UUID.fromString((String)zoneNeedMapping.get("uuid")));
        zoneNeed.setCreatedAt(LocalDateTime.parse((String)zoneNeedMapping.get("created_at")));
        zoneNeed.setUpdatedAt(LocalDateTime.parse((String)zoneNeedMapping.get("updated_at")));
        zoneNeed.set_synced((boolean)zoneNeedMapping.get("is_synced"));
        zoneNeed.setZone(zoneService.findById(((Number)zoneNeedMapping.get("id_zone")).intValue()));

        return zoneNeed;
    }
}
