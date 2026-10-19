package org.ong.dryforest.repository;

import org.ong.dryforest.entity.PlantationBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PlantationBlockRepository
        extends JpaRepository<PlantationBlock, Integer> {

    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<PlantationBlock> findByUuidAndIsDeletedFalse(UUID uuid);

    List<PlantationBlock> findAllByIsDeletedFalse();

    Optional<PlantationBlock> findByIdAndIsDeletedFalse(int id);

    Page<PlantationBlock> findAllByIsDeletedFalse(Pageable pageable);
}