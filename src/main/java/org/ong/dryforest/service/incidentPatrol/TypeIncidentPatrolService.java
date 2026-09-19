package org.ong.dryforest.service.incidentPatrol;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.TypeIncidentPatrol;

public interface TypeIncidentPatrolService {

    List<TypeIncidentPatrol> findAll();

    TypeIncidentPatrol findById(int id);

    TypeIncidentPatrol createTypeIncidentPatrol(TypeIncidentPatrol typeIncidentPatrol);

    TypeIncidentPatrol updateTypeIncidentPatrol(TypeIncidentPatrol typeIncidentPatrol);

    void deleteTypeIncidentPatrol(TypeIncidentPatrol typeIncidentPatrol);

    List<TypeIncidentPatrol> findAllTypeIncidentPatrolsUpdatedSince(LocalDateTime last_sync);
    
}
