package org.ong.dryforest.service.incidentPatrol;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.PatrolGroup;

public interface PatrolGroupService {

    List<PatrolGroup> findAll();

    PatrolGroup findById(int id_patrol_group);

    PatrolGroup createPatrolGroup(PatrolGroup patrolGroup);

    PatrolGroup updatePatrolGroup(PatrolGroup patrolGroup);

    void deletePatrolGroup(PatrolGroup patrolGroup);

    List<PatrolGroup> findAllPatrolGroupUpdatedSince(LocalDateTime last_sync);
    
}
