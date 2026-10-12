package org.ong.dryforest.service.animalTracking;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.animaltracking.AnimalObservedByZoneDTO;
import org.ong.dryforest.dto.animaltracking.AnimalTrackingDTO;
import org.ong.dryforest.entity.AnimalTracking;
import org.ong.dryforest.mapper.AnimalTrackingMapper;
import org.ong.dryforest.repository.AnimalTrackingRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnimalTrackingServiceImpl implements AnimalTrackingService {

    private final AnimalTrackingRepository animalTrackingRepository;
    private final AnimalTrackingMapper animalTrackingMapper;


    @Override
    public AnimalTracking create(AnimalTrackingDTO dto) throws Exception {

        AnimalTracking animalTracking =
                animalTrackingMapper.toEntity(dto);

        return animalTrackingRepository.save(animalTracking);
    }


    @Override
    public void createAnimalTracking(
            AnimalTracking animalTracking
    ) {

        if (animalTracking.getUuid() == null) {
            animalTracking.setUuid(UUID.randomUUID());
        }

        if (animalTracking.getCreatedAt() == null) {
            animalTracking.setCreatedAt(LocalDateTime.now());
        }

        animalTracking.setUpdatedAt(LocalDateTime.now());

        animalTracking.set_synced(true);

        animalTrackingRepository.save(animalTracking);
    }


    @Override
    public List<AnimalTracking> findAll() {

        return animalTrackingRepository.findAllByIsDeletedFalse();
    }


    @Override
    public AnimalTracking findById(int id) {

        return animalTrackingRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Animal tracking not found for id: " + id
                        )
                );
    }


    @Override
    public AnimalTracking findByUuid(UUID uuid) {

        return animalTrackingRepository
                .findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Animal tracking not found for uuid: " + uuid
                        )
                );
    }


    @Override
    public void updateAnimalTracking(
            AnimalTracking animalTracking
    ) {

        AnimalTracking existing =
                findById(animalTracking.getId());

        existing.setUuid(animalTracking.getUuid());
        existing.setLocation(animalTracking.getLocation());
        existing.setDate_tracking(
                animalTracking.getDate_tracking()
        );
        existing.setHave_seen(
                animalTracking.isHave_seen()
        );
        existing.setDescription(
                animalTracking.getDescription()
        );

        if (animalTracking.getPlantationBlock() != null) {
            existing.setPlantationBlock(
                    animalTracking.getPlantationBlock()
            );
        }

        existing.setUpdatedAt(LocalDateTime.now());
        existing.set_synced(true);

        animalTrackingRepository.save(existing);
    }



    @Override
    public AnimalTracking update(
            int id,
            AnimalTrackingDTO dto
    ) throws Exception {

        AnimalTracking existing = findById(id);

        animalTrackingMapper.updateEntity(
                existing,
                dto
        );

        return animalTrackingRepository.save(existing);
    }


    @Override
    public void deleteAnimalTracking(
            AnimalTracking animalTracking
    ) {

        try {
            findById(animalTracking.getId());
            animalTracking.setDeleted(true);
            animalTracking.setUpdatedAt(
                    LocalDateTime.now()
            );
            animalTracking.set_synced(true);

            animalTrackingRepository.save(animalTracking);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "Impossible de supprimer cet animal tracking"
            );
        }
    }


    @Override
    public boolean existsByUuid(UUID uuid) {

        return animalTrackingRepository
                .existsByUuidAndIsDeletedFalse(uuid);
    }


    @Override
    public AnimalTracking mapToEntity(
            Map<String, Object> animalTrackingMapping
    ) {
        AnimalTracking animalTracking =
                new AnimalTracking();

        if (animalTrackingMapping.get("uuid") != null) {

            animalTracking.setUuid(
                    UUID.fromString(
                            animalTrackingMapping
                                    .get("uuid")
                                    .toString()
                    )
            );

        } else {

            animalTracking.setUuid(
                    UUID.randomUUID()
            );
        }

        if (animalTrackingMapping.get("date_tracking") != null) {

            animalTracking.setDate_tracking(
                    LocalDateTime.parse(
                            animalTrackingMapping
                                    .get("date_tracking")
                                    .toString()
                    )
            );
        }

        if (animalTrackingMapping.get("description") != null) {

            animalTracking.setDescription(
                    animalTrackingMapping
                            .get("description")
                            .toString()
            );
        }

        if (animalTrackingMapping.get("have_seen") != null) {

            animalTracking.setHave_seen(
                    Boolean.parseBoolean(
                            animalTrackingMapping
                                    .get("have_seen")
                                    .toString()
                    )
            );
        }

        if (animalTrackingMapping.get("is_synced") != null) {

            animalTracking.set_synced(
                    Boolean.parseBoolean(
                            animalTrackingMapping
                                    .get("is_synced")
                                    .toString()
                    )
            );
        }

        if (animalTrackingMapping.get("created_at") != null) {

            animalTracking.setCreatedAt(
                    LocalDateTime.parse(
                            animalTrackingMapping
                                    .get("created_at")
                                    .toString()
                    )
            );
        } else {

            animalTracking.setCreatedAt(
                    LocalDateTime.now()
            );
        }

        if (animalTrackingMapping.get("updated_at") != null) {

            animalTracking.setUpdatedAt(
                    LocalDateTime.parse(
                            animalTrackingMapping
                                    .get("updated_at")
                                    .toString()
                    )
            );
        } else {

            animalTracking.setUpdatedAt(
                    LocalDateTime.now()
            );
        }

        return animalTracking;
    }


    @Override
    public List<AnimalObservedByZoneDTO> getObservedAnimalsByZone() {

        return animalTrackingRepository
                .findObservedAnimalsByZone();
    }
}