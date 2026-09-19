package org.ong.dryforest.service.animalTracking;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.animaltracking.AnimalObservedByZoneDTO;
import org.ong.dryforest.dto.animaltracking.AnimalTrackingDTO;
import org.ong.dryforest.entity.AnimalTracking;

public interface AnimalTrackingService {

    AnimalTracking create(AnimalTrackingDTO animalTrackingDTO) throws Exception;

    AnimalTracking createAnimalTracking(AnimalTracking animalTracking);

    List<AnimalTracking> findAll();

    AnimalTracking findById(int id);

    AnimalTracking findByUuid(UUID uuid);

    AnimalTracking updateAnimalTracking(AnimalTracking animalTracking);

    void deleteAnimalTracking(AnimalTracking animalTracking);

    boolean existsByUuid(UUID uuid);

    AnimalTracking mapToEntity(Map<String, Object> animalTrackingMapping);

    List<AnimalObservedByZoneDTO> getObservedAnimalsByZone();

}
