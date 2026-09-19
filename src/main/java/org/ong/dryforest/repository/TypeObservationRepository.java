package org.ong.dryforest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.ong.dryforest.entity.TypeObservationPatrol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeObservationRepository extends JpaRepository<TypeObservationPatrol, Integer> {

    List<TypeObservationPatrol> findAllByIsDeletedFalse();

    Optional<TypeObservationPatrol> findByIdAndIsDeletedFalse(int id);

    @Query("""
            SELECT typeO FROM TypeObservationPatrol typeO
            WHERE typeO.updatedAt > :last_sync OR typeO.isDeleted = true
            """)
    List<TypeObservationPatrol> findAllUpdatedSince(@Param("last_sync") LocalDateTime last_sync);
}
