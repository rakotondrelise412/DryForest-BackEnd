package org.ong.dryforest.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ong.dryforest.entity.ZoneNeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneNeedRepository extends JpaRepository<ZoneNeed, Integer> {
    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<ZoneNeed> findByUuidAndIsDeletedFalse(UUID uuid);

    List<ZoneNeed> findAllByIsDeletedFalse();

    Optional<ZoneNeed> findByIdAndIsDeletedFalse(int id);
}
