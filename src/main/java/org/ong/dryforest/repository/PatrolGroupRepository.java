package org.ong.dryforest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.ong.dryforest.entity.PatrolGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatrolGroupRepository extends JpaRepository<PatrolGroup, Integer> {
    List<PatrolGroup> findAllByIsDeletedFalse();

    Optional<PatrolGroup> findByIdAndIsDeletedFalse(int id);

    @Query("""
            SELECT pg FROM PatrolGroup pg
            WHERE pg.updatedAt > :last_sync OR pg.isDeleted = true
            """)
    List<PatrolGroup> findAllUpdatedSince(@Param("last_sync") LocalDateTime last_sync);
}
