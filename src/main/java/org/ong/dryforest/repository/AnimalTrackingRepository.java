package org.ong.dryforest.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ong.dryforest.dto.animaltracking.AnimalObservedByZoneDTO;
import org.ong.dryforest.entity.AnimalTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalTrackingRepository extends JpaRepository<AnimalTracking, Integer> {
    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<AnimalTracking> findByUuidAndIsDeletedFalse(UUID uuid);

    List<AnimalTracking> findAllByIsDeletedFalse();

    Optional<AnimalTracking> findByIdAndIsDeletedFalse(int id);

    @Query("""
        SELECT new org.ong.dryforest.dto.animaltracking.AnimalObservedByZoneDTO(
            z.id,
            z.name,
            a.id,
            a.name,
            COUNT(atd.id)
        )
        FROM AnimalTrackingDetail atd
        JOIN atd.animalTracking at
        JOIN at.plantationBlock pb
        JOIN pb.zone z
        JOIN atd.animal a
        WHERE atd.isDeleted = false
            AND at.isDeleted = false
            AND pb.isDeleted = false
            AND z.isDeleted = false
            AND a.isDeleted = false
            AND atd.have_seen = true
        GROUP BY z.id, z.name, a.id, a.name
        ORDER BY z.name, a.name
    """)
    List<AnimalObservedByZoneDTO> findObservedAnimalsByZone();
}
