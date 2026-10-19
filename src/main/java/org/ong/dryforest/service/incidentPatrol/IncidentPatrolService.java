package org.ong.dryforest.service.incidentPatrol;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.CountByTypeDTO;
import org.ong.dryforest.entity.IncidentPatrol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IncidentPatrolService {

    List<IncidentPatrol> findAll();

    Page<IncidentPatrol> findAll(Pageable pageable);

    IncidentPatrol createIncidentPatrol(IncidentPatrol incidentPatrol);

    IncidentPatrol findById(int id);

    IncidentPatrol findByUuid(UUID uuid);

    IncidentPatrol updateIncidentPatrol(IncidentPatrol incidentPatrol);

    void deleteIncidentPatrol(IncidentPatrol incidentPatrol);

    boolean existsByUuid(UUID uuid);

    IncidentPatrol mapToEntity(Map<String, Object> incidentPatrolMapping);

    List<CountByTypeDTO> countByType();
}
