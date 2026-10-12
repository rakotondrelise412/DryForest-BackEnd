package org.ong.dryforest.mapper;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.ong.dryforest.dto.animaltracking.AnimalTrackingDTO;
import org.ong.dryforest.dto.animaltracking.AnimalTrackingDetailDTO;
import org.ong.dryforest.entity.Animal;
import org.ong.dryforest.entity.AnimalTracking;
import org.ong.dryforest.entity.AnimalTrackingDetail;
import org.ong.dryforest.service.animalTracking.AnimalService;
import org.ong.dryforest.service.geometryService.GeometryService;
import org.ong.dryforest.service.plantationBlock.PlantationBlockService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AnimalTrackingMapper {

    private final GeometryService geometryService;
    private final AnimalService animalService;
    private final PlantationBlockService plantationBlockService;

    public AnimalTracking toEntity(
            AnimalTrackingDTO dto
    ) throws Exception {

        AnimalTracking animalTracking =
                new AnimalTracking();

        animalTracking.setUuid(
                dto.getUuid() != null
                        ? dto.getUuid()
                        : UUID.randomUUID()
        );

        LocalDateTime createdAt =
                dto.getCreated_at() != null
                        ? dto.getCreated_at()
                        : LocalDateTime.now();

        animalTracking.setCreatedAt(createdAt);
        animalTracking.setUpdatedAt(createdAt);
        animalTracking.set_synced(true);

        if (dto.getLocation() != null) {

            Geometry location =
                    geometryService.parseGeoJson(
                            dto.getLocation()
                    );

            animalTracking.setLocation(
                    (Point) location
            );
        }

        animalTracking.setDate_tracking(
                dto.getDate_tracking()
        );

        animalTracking.setHave_seen(
                dto.isHave_seen()
        );

        animalTracking.setDescription(
                dto.getDescription()
        );

        if (dto.getId_plantation_block() == 0) {
            throw new IllegalArgumentException(
                    "Plantation block is required!"
            );
        }

        animalTracking.setPlantationBlock(
                plantationBlockService.findById(
                        dto.getId_plantation_block()
                )
        );

        if (dto.getAnimal_tracking_details() != null) {

            List<AnimalTrackingDetail> details =
                    new ArrayList<>();

            for (AnimalTrackingDetailDTO detailDTO :
                    dto.getAnimal_tracking_details()) {

                AnimalTrackingDetail detail =
                        toDetailEntity(detailDTO);

                detail.setAnimalTracking(
                        animalTracking
                );

                details.add(detail);
            }

            animalTracking.setAnimal_tracking_details(
                    details
            );
        }

        return animalTracking;
    }

    public AnimalTrackingDetail toDetailEntity(
            AnimalTrackingDetailDTO dto
    ) {

        if (dto.getId_animal() == 0) {
            throw new IllegalArgumentException(
                    "Animal is required!"
            );
        }

        Animal animal =
                animalService.findById(
                        dto.getId_animal()
                );

        AnimalTrackingDetail detail =
                new AnimalTrackingDetail();

        detail.setUuid(
                dto.getUuid() != null
                        ? dto.getUuid()
                        : UUID.randomUUID()
        );

        detail.setAnimal(animal);

        LocalDateTime createdAt =
                dto.getCreated_at() != null
                        ? dto.getCreated_at()
                        : LocalDateTime.now();

        detail.setCreatedAt(createdAt);

        detail.setUpdatedAt(
                dto.getUpdated_at() != null
                        ? dto.getUpdated_at()
                        : createdAt
        );

        detail.set_synced(true);

        detail.setHave_seen(
                dto.isHave_seen()
        );

        return detail;
    }

    public AnimalTrackingDTO toDTO(
            AnimalTracking entity
    ) {

        AnimalTrackingDTO dto =
                new AnimalTrackingDTO();

        dto.setId_animal_tracking(
                entity.getId()
        );

        dto.setUuid(
                entity.getUuid()
        );

        dto.setDate_tracking(
                entity.getDate_tracking()
        );

        dto.setHave_seen(
                entity.isHave_seen()
        );

        dto.setDescription(
                entity.getDescription()
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

        if (entity.getPlantationBlock() != null) {

            dto.setId_plantation_block(
                    entity.getPlantationBlock()
                            .getId()
            );
        }

        if (entity.getLocation() != null) {

            dto.setLocation(
                    geometryService.toGeoJson(
                            entity.getLocation()
                    )
            );
        }

        if (entity.getAnimal_tracking_details() != null) {

            List<AnimalTrackingDetailDTO> details =
                    entity.getAnimal_tracking_details()
                            .stream()
                            .map(this::toDetailDTO)
                            .toList();

            dto.setAnimal_tracking_details(
                    details
            );
        }

        return dto;
    }

    public AnimalTrackingDetailDTO toDetailDTO(
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

    public List<AnimalTrackingDTO> toDTOList(
            List<AnimalTracking> entities
    ) {

        return entities.stream()
                .map(this::toDTO)
                .toList();
    }

    public void updateEntity(
            AnimalTracking entity,
            AnimalTrackingDTO dto
    ) throws Exception {

        if (entity.getUuid() == null &&
                dto.getUuid() != null) {

            entity.setUuid(
                    dto.getUuid()
            );
        }

        entity.setUpdatedAt(
                LocalDateTime.now()
        );

        entity.setDate_tracking(
                dto.getDate_tracking()
        );

        entity.setHave_seen(
                dto.isHave_seen()
        );

        entity.setDescription(
                dto.getDescription()
        );

        entity.set_synced(true);

        if (dto.getLocation() != null) {

            Geometry location =
                    geometryService.parseGeoJson(
                            dto.getLocation()
                    );

            entity.setLocation(
                    (Point) location
            );
        }

        if (dto.getId_plantation_block() == 0) {
            throw new IllegalArgumentException(
                    "Plantation block is required!"
            );
        }

        entity.setPlantationBlock(
                plantationBlockService.findById(
                        dto.getId_plantation_block()
                )
        );

        if (dto.getAnimal_tracking_details() != null) {

            List<AnimalTrackingDetail> details =
                    new ArrayList<>();

            for (AnimalTrackingDetailDTO detailDTO :
                    dto.getAnimal_tracking_details()) {

                AnimalTrackingDetail detail =
                        toDetailEntity(detailDTO);

                detail.setAnimalTracking(
                        entity
                );

                details.add(detail);
            }

            entity.getAnimal_tracking_details()
                    .clear();

            entity.getAnimal_tracking_details()
                    .addAll(details);
        }
    }
}
