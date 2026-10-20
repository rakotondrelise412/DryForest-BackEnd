package org.ong.dryforest.repository;

import org.ong.dryforest.entity.Severity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeverityRepository extends JpaRepository<Severity, Integer> {

    Optional<Severity> findByIdAndIsDeletedFalse(int id);

    List<Severity> findAllByIsDeletedFalse();

    Page<Severity> findAllByIsDeletedFalse(Pageable pageable);

    boolean existsByNameIgnoreCaseAndIsDeletedFalse(String name);

    /**
     * Synchronisation mobile :
     * récupérer les données modifiées depuis lastSync
     * + les données supprimées.
     */
    @Query("""
        SELECT s
        FROM Severity s
        WHERE s.updatedAt > :lastSync
           OR s.isDeleted = true
        """)
    List<Severity> findAllUpdatedSince(
            @Param("lastSync") LocalDateTime lastSync
    );
}