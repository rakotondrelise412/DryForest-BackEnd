package org.ong.dryforest.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ong.dryforest.dto.CountByTypeDTO;
import org.ong.dryforest.entity.IncidentPatrol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentPatrolRepository extends JpaRepository<IncidentPatrol, Integer> {
    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<IncidentPatrol> findByUuidAndIsDeletedFalse(UUID uuid);

    List<IncidentPatrol> findAllByIsDeletedFalse();

    Optional<IncidentPatrol> findByIdAndIsDeletedFalse(int id);

    @Query("""
        SELECT new org.ong.dryforest.dto.CountByTypeDTO(
            ipt.name,
            COUNT(ip.id)
        )
        FROM IncidentPatrol ip
        JOIN ip.typeIncidentPatrol ipt
        WHERE ip.isDeleted = false
            AND ipt.isDeleted = false
        GROUP BY ipt.name
        ORDER BY COUNT(ip.id) DESC
    """)
    List<CountByTypeDTO> countByType();
}
