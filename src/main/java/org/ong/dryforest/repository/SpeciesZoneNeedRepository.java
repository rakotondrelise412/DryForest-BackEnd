package org.ong.dryforest.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ong.dryforest.entity.SpeciesZoneNeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesZoneNeedRepository extends JpaRepository<SpeciesZoneNeed, Integer>{
    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<SpeciesZoneNeed> findByUuidAndIsDeletedFalse(UUID uuid);

    List<SpeciesZoneNeed> findAllByIsDeletedFalse();

    Optional<SpeciesZoneNeed> findByIdAndIsDeletedFalse(int id);
}
