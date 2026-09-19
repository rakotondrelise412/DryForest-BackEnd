package org.ong.dryforest.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ong.dryforest.entity.PlantingMonitoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantingMonitoringRepository extends JpaRepository<PlantingMonitoring, Integer>{
    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<PlantingMonitoring> findByUuidAndIsDeletedFalse(UUID uuid);

    List<PlantingMonitoring> findAllByIsDeletedFalse();

    Optional<PlantingMonitoring> findByIdAndIsDeletedFalse(int id);
}
