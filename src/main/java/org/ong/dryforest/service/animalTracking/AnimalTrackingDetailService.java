package org.ong.dryforest.service.animalTracking;

import org.ong.dryforest.dto.animaltracking.AnimalTrackingDetailDTO;
import org.ong.dryforest.entity.AnimalTrackingDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AnimalTrackingDetailService {

    AnimalTrackingDetail create(
            AnimalTrackingDetail animalTrackingDetail
    );

    AnimalTrackingDetail create(
            AnimalTrackingDetailDTO dto
    );

    void createAnimalTrackingDetail(
            AnimalTrackingDetail animalTrackingDetail
    );

    List<AnimalTrackingDetail> findAll();

    AnimalTrackingDetail findById(int id);

    AnimalTrackingDetail findByUuid(UUID uuid);

    void updateAnimalTrackingDetail(
            AnimalTrackingDetail animalTrackingDetail
    );

    AnimalTrackingDetail update(
            int id,
            AnimalTrackingDetailDTO dto
    );

    void deleteAnimalTrackingDetail(
            AnimalTrackingDetail animalTrackingDetail
    );

    boolean existsByUuid(UUID uuid);

    AnimalTrackingDetail mapToEntity(
            Map<String, Object> animalTrackingDetailMapping
    );

    Page<AnimalTrackingDetail> findAll(Pageable pageable);

}