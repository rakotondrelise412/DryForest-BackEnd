package org.ong.dryforest.service.incidentPatrol;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.TypeIncidentPatrol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TypeIncidentPatrolService {

    List<TypeIncidentPatrol> findAll();

    Page<TypeIncidentPatrol> findAll(Pageable pageable);

    TypeIncidentPatrol findById(int id);

    TypeIncidentPatrol createTypeIncidentPatrol(TypeIncidentPatrol typeIncidentPatrol);

    TypeIncidentPatrol updateTypeIncidentPatrol(TypeIncidentPatrol typeIncidentPatrol);

    void deleteTypeIncidentPatrol(TypeIncidentPatrol typeIncidentPatrol);

    List<TypeIncidentPatrol> findAllTypeIncidentPatrolsUpdatedSince(LocalDateTime last_sync);
    
}
