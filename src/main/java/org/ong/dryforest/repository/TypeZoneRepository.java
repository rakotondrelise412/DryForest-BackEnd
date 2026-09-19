package org.ong.dryforest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.ong.dryforest.entity.TypeZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeZoneRepository extends JpaRepository<TypeZone, Integer> {
    List<TypeZone> findAllByIsDeletedFalse();
    
    Optional<TypeZone> findByIdAndIsDeletedFalse(int id);

    @Query("""
            SELECT tz FROM TypeZone tz WHERE tz.updatedAt > :last_sync OR tz.isDeleted = true
            """)
    List<TypeZone> findAllUpdatedSince(@Param("last_sync") LocalDateTime last_sync);
}
