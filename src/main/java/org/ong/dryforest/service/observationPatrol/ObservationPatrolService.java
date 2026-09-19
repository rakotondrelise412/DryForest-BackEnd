package org.ong.dryforest.service.observationPatrol;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.CountByTypeDTO;
import org.ong.dryforest.dto.observationPatrol.ObservationPatrolDTO;
import org.ong.dryforest.entity.ObservationPatrol;

public interface ObservationPatrolService {

    ObservationPatrol create(ObservationPatrolDTO observationPatrolDTO);

    void deleteObservationPatrol(ObservationPatrol observationPatrol);

    ObservationPatrol updateObservationPatrol(ObservationPatrol observationPatrol);

    ObservationPatrol createObservationPatrol(ObservationPatrol observationPatrol);

    ObservationPatrol findByUuid(UUID uuid);

    ObservationPatrol findById(int id);

    List<ObservationPatrol> findAll();

    ObservationPatrol mapToEntity(Map<String, Object> observationPatrolMapping);

    boolean existsByUuid(UUID uuid);

    List<CountByTypeDTO> countByType();
    
}
