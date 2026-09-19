package org.ong.dryforest.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ong.dryforest.entity.ReforestationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReforestationDetailRepository extends JpaRepository<ReforestationDetail, Integer>{
    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<ReforestationDetail> findByUuidAndIsDeletedFalse(UUID uuid);

    List<ReforestationDetail> findAllByIsDeletedFalse();

    Optional<ReforestationDetail> findByIdAndIsDeletedFalse(int id);
}
