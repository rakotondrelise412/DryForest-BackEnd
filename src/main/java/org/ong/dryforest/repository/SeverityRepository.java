package org.ong.dryforest.repository;

import org.ong.dryforest.entity.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeverityRepository extends JpaRepository<Severity, Integer> {

    /**
     * Récupérer un severity non supprimé
     */
    Optional<Severity> findByIdAndIsDeletedFalse(int id);

    /**
     * Récupérer tous les severity non supprimés
     */
    List<Severity> findAllByIsDeletedFalse();

    /**
     * Vérifier si un nom existe déjà
     */
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