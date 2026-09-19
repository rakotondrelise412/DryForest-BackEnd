package org.ong.dryforest.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ong.dryforest.dto.CountByTypeDTO;
import org.ong.dryforest.entity.ObservationPatrol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationPatrolRepository extends JpaRepository<ObservationPatrol, Integer> {
    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<ObservationPatrol> findByUuidAndIsDeletedFalse(UUID uuid);

    List<ObservationPatrol> findAllByIsDeletedFalse();

    Optional<ObservationPatrol> findByIdAndIsDeletedFalse(int id);

    @Query("""
        SELECT new org.ong.dryforest.dto.CountByTypeDTO(
            opt.name,
            COUNT(op.id)
        )
        FROM ObservationPatrol op
        JOIN op.typeObservationPatrol opt
        WHERE op.isDeleted = false
            AND opt.isDeleted = false
        GROUP BY opt.name
        ORDER BY COUNT(op.id) DESC
    """)
    List<CountByTypeDTO> countByType();
}
