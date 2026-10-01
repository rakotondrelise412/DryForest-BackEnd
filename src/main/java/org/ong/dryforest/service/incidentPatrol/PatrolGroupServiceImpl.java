package org.ong.dryforest.service.incidentPatrol;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.PatrolGroup;
import org.ong.dryforest.repository.PatrolGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PatrolGroupServiceImpl implements PatrolGroupService {

    @Autowired
    PatrolGroupRepository patrolGroupRepository;

    @Override
    public List<PatrolGroup> findAll() {
        return patrolGroupRepository.findAllByIsDeletedFalse();
    }

    @Override
    public List<PatrolGroup> findAllPatrolGroupUpdatedSince(LocalDateTime last_sync) {
        return patrolGroupRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public PatrolGroup findById(int id_patrol_group) {
        return patrolGroupRepository
                .findByIdAndIsDeletedFalse(id_patrol_group)
                .orElseThrow(() -> new RuntimeException("Patrol group not found"));
    }

    @Override
    public PatrolGroup createPatrolGroup(PatrolGroup patrolGroup) {
        try {
            return patrolGroupRepository.save(patrolGroup);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Groupe de patrouilleur déjà existant"
            );
        }
    }

    @Override
    public PatrolGroup updatePatrolGroup(PatrolGroup patrolGroup) {
        findById(patrolGroup.getId());
        return patrolGroupRepository.save(patrolGroup);
    }

    @Override
    public void deletePatrolGroup(PatrolGroup patrolGroup) {
        try {
            PatrolGroup existing = findById(patrolGroup.getId());

            existing.setDeleted(true);

            patrolGroupRepository.save(existing);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "Impossible de supprimer ce groupe de patrouille"
            );
        }
    }
}