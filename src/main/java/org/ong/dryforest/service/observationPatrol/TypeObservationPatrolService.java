package org.ong.dryforest.service.observationPatrol;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.TypeObservationPatrol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TypeObservationPatrolService {

    List<TypeObservationPatrol> findAll();

    Page<TypeObservationPatrol> findAll(Pageable pageable);

    TypeObservationPatrol findById(int id_type_observation);

    List<TypeObservationPatrol> findAllTypeObservationPatrolUpdatedSince(LocalDateTime last_sync);

    TypeObservationPatrol updateTypeObservationPatrol(TypeObservationPatrol typeObservationPatrol);

    void deleteTypeObservationPatrol(TypeObservationPatrol typeObservationPatrol);

    TypeObservationPatrol createObservationPatrol(TypeObservationPatrol typeObservation);
    
}
