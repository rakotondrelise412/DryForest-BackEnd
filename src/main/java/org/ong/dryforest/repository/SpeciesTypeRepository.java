package org.ong.dryforest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.ong.dryforest.entity.SpeciesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesTypeRepository extends JpaRepository<SpeciesType, Integer> {

    List<SpeciesType> findAllByIsDeletedFalse();
    
    Optional<SpeciesType> findByIdAndIsDeletedFalse(int id);
    
    @Query("""
            SELECT st FROM SpeciesType st
            WHERE st.updatedAt > :last_sync OR st.isDeleted = true
            """)
    List<SpeciesType> findAllUpdatedSince(@Param("last_sync") LocalDateTime last_sync);
}
