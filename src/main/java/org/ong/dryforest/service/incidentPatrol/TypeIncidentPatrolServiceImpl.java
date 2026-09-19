package org.ong.dryforest.service.incidentPatrol;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.TypeIncidentPatrol;
import org.ong.dryforest.repository.TypeIncidentPatrolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TypeIncidentPatrolServiceImpl implements TypeIncidentPatrolService{
    @Autowired
    TypeIncidentPatrolRepository typeIncidentPatrolRepository;

    @Override
    public List<TypeIncidentPatrol> findAll(){ return typeIncidentPatrolRepository.findAllByIsDeletedFalse(); }

    @Override
    public List<TypeIncidentPatrol> findAllTypeIncidentPatrolsUpdatedSince(LocalDateTime last_sync){
        return typeIncidentPatrolRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public TypeIncidentPatrol findById(int id){
        return typeIncidentPatrolRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new RuntimeException("Type d'incident introuvable avec l'id : " + id));
    }

    @Override
    public TypeIncidentPatrol createTypeIncidentPatrol(TypeIncidentPatrol typeIncidentPatrol){
        return typeIncidentPatrolRepository.save(typeIncidentPatrol);
    }

    @Override
    public TypeIncidentPatrol updateTypeIncidentPatrol(TypeIncidentPatrol typeIncidentPatrol){
        findById(typeIncidentPatrol.getId());

        return typeIncidentPatrolRepository.save(typeIncidentPatrol);
    }

    @Override
    public void deleteTypeIncidentPatrol(TypeIncidentPatrol typeIncidentPatrol){
        try {
            findById(typeIncidentPatrol.getId());
            typeIncidentPatrolRepository.delete(typeIncidentPatrol);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cet type d'incident de patrouille");
        }
    }


    // @Override
    // public TypeIncidentPatrol findByUuidIncidentPatrol(UUID uuid){
    //     return typeIncidentPatrolRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Type d'incident introuvable avec l'id : " + uuid));
    // }
}
