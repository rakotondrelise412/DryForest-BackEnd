package org.ong.dryforest.service.observationPatrol;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.TypeObservationPatrol;

public interface TypeObservationPatrolService {

    List<TypeObservationPatrol> findAll();

    TypeObservationPatrol findById(int id_type_observation);

    List<TypeObservationPatrol> findAllTypeObservationPatrolUpdatedSince(LocalDateTime last_sync);

    TypeObservationPatrol updateTypeObservationPatrol(TypeObservationPatrol typeObservationPatrol);

    void deleteTypeObservationPatrol(TypeObservationPatrol typeObservationPatrol);

    TypeObservationPatrol createObservationPatrol(TypeObservationPatrol typeObservation);
    
}
