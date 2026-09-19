package org.ong.dryforest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.ong.dryforest.entity.Species;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Integer> {
    List<Species> findAllByIsDeletedFalse();
    
    Optional<Species> findByIdAndIsDeletedFalse(int id);
    
    List<Species> findAllByType_IdAndIsDeletedFalse(int id_species_type);

    @Query("""
            SELECT sp FROM Species sp
            WHERE sp.updatedAt > :last_sync OR sp.isDeleted = true
           """)
    List<Species> findAllUpdatedSince(@Param("last_sync") LocalDateTime last_sync);
}
