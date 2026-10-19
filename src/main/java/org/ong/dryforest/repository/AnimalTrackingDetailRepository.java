package org.ong.dryforest.repository;

import org.ong.dryforest.entity.AnimalTrackingDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnimalTrackingDetailRepository
        extends JpaRepository<AnimalTrackingDetail, Integer> {

    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<AnimalTrackingDetail> findByUuidAndIsDeletedFalse(
            UUID uuid
    );

    Optional<AnimalTrackingDetail> findByIdAndIsDeletedFalse(int id);

    Page<AnimalTrackingDetail> findAllByIsDeletedFalse(
            Pageable pageable
    );

    List<AnimalTrackingDetail> findAllByIsDeletedFalse();
}