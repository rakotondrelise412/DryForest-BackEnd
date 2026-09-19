package org.ong.dryforest.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ong.dryforest.entity.AnimalTrackingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalTrackingDetailRepository extends JpaRepository<AnimalTrackingDetail, Integer> {
    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<AnimalTrackingDetail> findByUuidAndIsDeletedFalse(UUID uuid);

    List<AnimalTrackingDetail> findAllByIsDeletedFalse();

    Optional<AnimalTrackingDetail> findByIdAndIsDeletedFalse(int id);
}
