package org.ong.dryforest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.ong.dryforest.entity.TypeIncidentPatrol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeIncidentPatrolRepository extends JpaRepository<TypeIncidentPatrol, Integer>{
    // boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    // Optional<TypeIncidentPatrol> findByUuidAndIsDeletedFalse(UUID uuid);

    List<TypeIncidentPatrol> findAllByIsDeletedFalse();

    Optional<TypeIncidentPatrol> findByIdAndIsDeletedFalse(int id);

    @Query("""
            SELECT typeI FROM TypeIncidentPatrol typeI
            WHERE typeI.updatedAt > :last_sync OR typeI.isDeleted = true
            """)
    List<TypeIncidentPatrol> findAllUpdatedSince(@Param("last_sync") LocalDateTime last_sync);
}
