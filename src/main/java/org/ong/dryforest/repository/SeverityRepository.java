package org.ong.dryforest.repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.ong.dryforest.entity.Severity;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface SeverityRepository extends JpaRepository<Severity, Integer> {
    
    List<Severity> findAllByIsDeletedFalse();
    
    Optional<Severity> findByIdAndIsDeletedFalse(int id);

    @Query("""
            SELECT sv FROM Severity sv
            WHERE sv.updatedAt > :last_sync OR sv.isDeleted = true
           """)
    List<Severity> findAllUpdatedSince(@Param("last_sync") LocalDateTime last_sync);
}
