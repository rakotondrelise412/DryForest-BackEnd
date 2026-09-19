package org.ong.dryforest.service.observationPatrol;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.TypeObservationPatrol;
import org.ong.dryforest.repository.TypeObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TypeObservationPatrolServiceImpl implements TypeObservationPatrolService{
    
    @Autowired
    private TypeObservationRepository typeObservationRepository;

    @Override
    public TypeObservationPatrol createObservationPatrol(TypeObservationPatrol typeObservation){
        try {
            return typeObservationRepository.save(typeObservation);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Type d'observation patrouille déjà existant");
        }
    }

    @Override
    public List<TypeObservationPatrol> findAll(){
        return typeObservationRepository.findAllByIsDeletedFalse();
    }

    @Override
    public List<TypeObservationPatrol> findAllTypeObservationPatrolUpdatedSince(LocalDateTime last_sync){
        return typeObservationRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public TypeObservationPatrol findById(int id_type_observation){
        return typeObservationRepository.findByIdAndIsDeletedFalse(id_type_observation).orElseThrow(() -> new RuntimeException("Type observation not found"));
    }

    @Override
    public TypeObservationPatrol updateTypeObservationPatrol(TypeObservationPatrol typeObservationPatrol){
        findById(typeObservationPatrol.getId());
        return typeObservationRepository.save(typeObservationPatrol);
    }

    @Override
    public void deleteTypeObservationPatrol(TypeObservationPatrol typeObservationPatrol){
        try {
            findById(typeObservationPatrol.getId());
            typeObservationRepository.delete(typeObservationPatrol);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cet type d'observation de patrouille");
        }
    }

}
