package org.ong.dryforest.service.animalTracking;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.animaltracking.AnimalTrackingDetailDTO;
import org.ong.dryforest.entity.AnimalTrackingDetail;
import org.ong.dryforest.mapper.AnimalTrackingDetailMapper;
import org.ong.dryforest.repository.AnimalRepository;
import org.ong.dryforest.repository.AnimalTrackingDetailRepository;
import org.ong.dryforest.repository.AnimalTrackingRepository;
import org.ong.dryforest.entity.Animal;
import org.ong.dryforest.entity.AnimalTracking;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class AnimalTrackingDetailServiceImpl
        implements AnimalTrackingDetailService {

    private final AnimalTrackingDetailRepository
            animalTrackingDetailRepository;

    private final AnimalTrackingRepository
            animalTrackingRepository;

    private final AnimalRepository
            animalRepository;

    private final AnimalTrackingDetailMapper
            animalTrackingDetailMapper;

    @Override
    public void createAnimalTrackingDetail(
            AnimalTrackingDetail animalTrackingDetail
    ) {

        animalTrackingDetail.set_synced(true);

        animalTrackingDetailRepository.save(
                animalTrackingDetail
        );
    }

    @Override
    public List<AnimalTrackingDetail> findAll() {
        return animalTrackingDetailRepository
                .findAllByIsDeletedFalse();
    }

    @Override
    public AnimalTrackingDetail findById(int id) {

        return animalTrackingDetailRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Le detail de suivi d'animal est introuvable par l'id : "
                                        + id
                        )
                );
    }

    @Override
    public AnimalTrackingDetail findByUuid(
            UUID uuid
    ) {

        return animalTrackingDetailRepository
                .findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Le detail de suivi d'animal est introuvable par l'uuid : "
                                        + uuid
                        )
                );
    }

    @Override
    public void updateAnimalTrackingDetail(
            AnimalTrackingDetail animalTrackingDetail
    ) {

        findById(
                animalTrackingDetail.getId()
        );

        animalTrackingDetailRepository.save(
                animalTrackingDetail
        );
    }

    @Override
    public boolean existsByUuid(UUID uuid) {

        return animalTrackingDetailRepository
                .existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public AnimalTrackingDetail mapToEntity(
            Map<String, Object> mapping
    ) {

        AnimalTrackingDetail detail =
                new AnimalTrackingDetail();

        Object uuid = mapping.get("uuid");

        if (uuid != null) {
            detail.setUuid(
                    UUID.fromString(
                            uuid.toString()
                    )
            );
        } else {
            detail.setUuid(
                    UUID.randomUUID()
            );
        }

        Object haveSeen =
                mapping.get("have_seen");

        if (haveSeen != null) {
            detail.setHave_seen(
                    (Boolean) haveSeen
            );
        }

        Object idAnimal =
                mapping.get("id_animal");

        if (idAnimal != null) {

            int id =
                    ((Number) idAnimal)
                            .intValue();

            Animal animal =
                    animalRepository.findById(id)
                            .orElseThrow(
                                    () -> new RuntimeException(
                                            "Animal introuvable id: "
                                                    + id
                                    )
                            );

            detail.setAnimal(animal);
        }

        Object idAnimalTracking =
                mapping.get("id_animal_tracking");

        if (idAnimalTracking != null) {

            int id =
                    ((Number) idAnimalTracking)
                            .intValue();

            AnimalTracking tracking =
                    animalTrackingRepository
                            .findByIdAndIsDeletedFalse(id)
                            .orElseThrow(
                                    () -> new RuntimeException(
                                            "AnimalTracking introuvable id: "
                                                    + id
                                    )
                            );

            detail.setAnimalTracking(tracking);
        }

        Object createdAt =
                mapping.get("created_at");

        if (createdAt != null) {

            detail.setCreatedAt(
                    LocalDateTime.parse(
                            createdAt.toString()
                    )
            );

        } else {

            detail.setCreatedAt(
                    LocalDateTime.now()
            );
        }

        Object updatedAt =
                mapping.get("updated_at");

        if (updatedAt != null) {

            detail.setUpdatedAt(
                    LocalDateTime.parse(
                            updatedAt.toString()
                    )
            );

        } else {

            detail.setUpdatedAt(
                    LocalDateTime.now()
            );
        }

        detail.set_synced(true);

        return detail;
    }

    @Override
    public AnimalTrackingDetail create(
            AnimalTrackingDetail animalTrackingDetail
    ) {

        try {

            if (animalTrackingDetail.getUuid() == null) {

                animalTrackingDetail.setUuid(
                        UUID.randomUUID()
                );
            }

            if (animalTrackingDetail.getCreatedAt() == null) {

                animalTrackingDetail.setCreatedAt(
                        LocalDateTime.now()
                );
            }

            animalTrackingDetail.setUpdatedAt(
                    LocalDateTime.now()
            );

            animalTrackingDetail.set_synced(true);

            return animalTrackingDetailRepository.save(
                    animalTrackingDetail
            );

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Animal tracking detail already exist"
            );
        }
    }

    @Override
    public AnimalTrackingDetail create(
            AnimalTrackingDetailDTO dto
    ) {

        AnimalTrackingDetail detail =
                animalTrackingDetailMapper.toEntity(dto);

        return animalTrackingDetailRepository.save(
                detail
        );
    }

    @Override
    public AnimalTrackingDetail update(
            int id,
            AnimalTrackingDetailDTO dto
    ) {

        AnimalTrackingDetail detail =
                findById(id);

        animalTrackingDetailMapper.updateEntity(
                detail,
                dto
        );

        return animalTrackingDetailRepository.save(
                detail
        );
    }

    @Override
    public void deleteAnimalTrackingDetail(
            AnimalTrackingDetail animalTrackingDetail
    ) {

        findById(
                animalTrackingDetail.getId()
        );

        animalTrackingDetail.setDeleted(true);

        animalTrackingDetail.setUpdatedAt(
                LocalDateTime.now()
        );

        animalTrackingDetailRepository.save(
                animalTrackingDetail
        );
    }

    @Override
    public Page<AnimalTrackingDetail> findAll(Pageable pageable) {
        return animalTrackingDetailRepository
                .findAllByIsDeletedFalse(pageable);
    }
}