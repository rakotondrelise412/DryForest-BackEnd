package org.ong.dryforest.service.animalTracking;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.entity.AnimalTrackingDetail;

public interface AnimalTrackingDetailService {
    public AnimalTrackingDetail create(AnimalTrackingDetail animalTrackingDetail);

    AnimalTrackingDetail createAnimalTrackingDetail(AnimalTrackingDetail animalTrackingDetail);

    List<AnimalTrackingDetail> findAll();

    AnimalTrackingDetail findById(int id);

    AnimalTrackingDetail findByUuid(UUID uuid);

    AnimalTrackingDetail updateAnimalTrackingDetail(AnimalTrackingDetail animalTracking);

    void deleteAnimalTrackingDetail(AnimalTrackingDetail animalTrackingDetail);

    boolean existsByUuid(UUID uuid);

    AnimalTrackingDetail mapToEntity(Map<String, Object> animalTrackingDetailMapping);
}
