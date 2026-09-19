package org.ong.dryforest.service.zone;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.TypeZone;
import org.ong.dryforest.repository.TypeZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TypeZoneServiceImpl implements TypeZoneService {
    @Autowired
    TypeZoneRepository typeZoneRepository;
    
    @Override
    public TypeZone findById(int id_zone){
        return typeZoneRepository.findByIdAndIsDeletedFalse(id_zone).orElseThrow(() -> new RuntimeException("id zone not found"));
    }

    @Override
    public List<TypeZone> findAll(){ return typeZoneRepository.findAllByIsDeletedFalse(); }

    @Override
    public List<TypeZone> findAllTypesUpdatedSince(LocalDateTime last_sync){
        return typeZoneRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public TypeZone createTypeZone(TypeZone typeZone){
        try {
            return typeZoneRepository.save(typeZone);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Type de la zone déjà existant");
        }
    }

    @Override
    public TypeZone updateTypeZone(TypeZone typeZone){
        findById(typeZone.getId());
        return typeZoneRepository.save(typeZone);
    }

    @Override
    public void deleteTypeZone(TypeZone typeZone){
        try {
            findById(typeZone.getId());
            typeZoneRepository.save(typeZone);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer ce type de la zone");
        }
    }

}
