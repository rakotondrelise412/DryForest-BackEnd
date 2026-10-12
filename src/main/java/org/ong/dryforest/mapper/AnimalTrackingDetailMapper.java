package org.ong.dryforest.mapper;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.animaltracking.AnimalTrackingDetailDTO;
import org.ong.dryforest.entity.Animal;
import org.ong.dryforest.entity.AnimalTracking;
import org.ong.dryforest.entity.AnimalTrackingDetail;
import org.ong.dryforest.service.animalTracking.AnimalService;
import org.ong.dryforest.service.animalTracking.AnimalTrackingService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AnimalTrackingDetailMapper {

    private final AnimalService animalService;
    private final AnimalTrackingService animalTrackingService;

    public AnimalTrackingDetail toEntity(
            AnimalTrackingDetailDTO dto
    ) {

        AnimalTrackingDetail detail =
                new AnimalTrackingDetail();

        detail.setUuid(
                dto.getUuid() != null
                        ? dto.getUuid()
                        : UUID.randomUUID()
        );

        detail.setHave_seen(
                dto.isHave_seen()
        );

        detail.setCreatedAt(
                dto.getCreated_at() != null
                        ? dto.getCreated_at()
                        : LocalDateTime.now()
        );

        detail.setUpdatedAt(
                dto.getUpdated_at() != null
                        ? dto.getUpdated_at()
                        : LocalDateTime.now()
        );

        detail.set_synced(true);

        if (dto.getId_animal() == 0) {
            throw new IllegalArgumentException(
                    "Animal is required!"
            );
        }

        Animal animal =
                animalService.findById(
                        dto.getId_animal()
                );

        detail.setAnimal(animal);

        if (dto.getId_animal_tracking() == 0) {
            throw new IllegalArgumentException(
                    "Animal tracking is required!"
            );
        }

        AnimalTracking animalTracking =
                animalTrackingService.findById(
                        dto.getId_animal_tracking()
                );

        detail.setAnimalTracking(animalTracking);

        return detail;
    }

    public AnimalTrackingDetailDTO toDTO(
            AnimalTrackingDetail entity
    ) {

        AnimalTrackingDetailDTO dto =
                new AnimalTrackingDetailDTO();

        dto.setId_animal_tracking_detail(
                entity.getId()
        );

        dto.setUuid(
                entity.getUuid()
        );

        dto.setCreated_at(
                entity.getCreatedAt()
        );

        dto.setUpdated_at(
                entity.getUpdatedAt()
        );

        dto.set_synced(
                entity.is_synced()
        );

        dto.setHave_seen(
                entity.isHave_seen()
        );

        if (entity.getAnimal() != null) {

            dto.setId_animal(
                    entity.getAnimal().getId()
            );
        }

        if (entity.getAnimalTracking() != null) {

            dto.setId_animal_tracking(
                    entity.getAnimalTracking().getId()
            );
        }

        return dto;
    }

    public List<AnimalTrackingDetailDTO> toDTOList(
            List<AnimalTrackingDetail> entities
    ) {

        return entities.stream()
                .map(this::toDTO)
                .toList();
    }

    public void updateEntity(
            AnimalTrackingDetail entity,
            AnimalTrackingDetailDTO dto
    ) {

        entity.setHave_seen(
                dto.isHave_seen()
        );

        if (dto.getId_animal() != 0) {

            Animal animal =
                    animalService.findById(
                            dto.getId_animal()
                    );

            entity.setAnimal(animal);
        }

        if (dto.getId_animal_tracking() != 0) {

            AnimalTracking animalTracking =
                    animalTrackingService.findById(
                            dto.getId_animal_tracking()
                    );

            entity.setAnimalTracking(
                    animalTracking
            );
        }

        entity.setUpdatedAt(
                LocalDateTime.now()
        );

        entity.set_synced(true);
    }
}
