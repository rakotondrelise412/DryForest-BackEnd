package org.ong.dryforest.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ong.dryforest.entity.SubPlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubPlotRepository extends JpaRepository<SubPlot, Integer>{

    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<SubPlot> findByUuidAndIsDeletedFalse(UUID uuid);

    List<SubPlot> findAllByIsDeletedFalse();

    Optional<SubPlot> findByIdAndIsDeletedFalse(int id);
}
