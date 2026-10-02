package org.ong.dryforest.service.reforestationDetail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.service.reforestation.ReforestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import org.ong.dryforest.entity.ReforestationDetail;
import org.ong.dryforest.repository.ReforestationDetailRepository;
import org.ong.dryforest.service.species.SpeciesService;

@Service
public class ReforestationDetailServiceImpl
        implements ReforestationDetailService {

    @Autowired
    ReforestationDetailRepository
            reforestationDetailRepository;

    @Autowired
    ReforestationService
            reforestationService;

    @Autowired
    SpeciesService speciesService;


    @Override
    public ReforestationDetail
    createReforestationDetail(
            ReforestationDetail detail) {

        try {

            if (detail.getUuid() == null) {

                detail.setUuid(
                        UUID.randomUUID()
                );
            }


            if (detail.getCreatedAt() == null) {

                detail.setCreatedAt(
                        LocalDateTime.now()
                );
            }


            detail.setUpdatedAt(
                    LocalDateTime.now()
            );


            detail.set_synced(true);


            return reforestationDetailRepository
                    .save(detail);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Reforestation detail already exist"
            );
        }
    }


    @Override
    public List<ReforestationDetail> findAll() {

        return reforestationDetailRepository
                .findAllByIsDeletedFalse();
    }


    @Override
    public ReforestationDetail findById(
            int id) {

        return reforestationDetailRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Le detail de la reforestation "
                                        + "est introuvable par son id: "
                                        + id
                        )
                );
    }


    @Override
    public ReforestationDetail findByUuid(
            UUID uuid) {

        return reforestationDetailRepository
                .findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Le detail de la reforestation "
                                        + "est introuvable"
                        )
                );
    }


    @Override
    public ReforestationDetail
    updateReforestationDetail(
            ReforestationDetail detail) {

        try {

            ReforestationDetail existing =
                    findById(detail.getId());


            existing.setUuid(
                    detail.getUuid()
            );


            existing.setQuantity(
                    detail.getQuantity()
            );


            existing.setSpecies(
                    detail.getSpecies()
            );


            existing.setReforestation(
                    detail.getReforestation()
            );


            existing.setUpdatedAt(
                    LocalDateTime.now()
            );


            return reforestationDetailRepository
                    .save(existing);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "Impossible de modifier ce detail "
                            + "de la reforestation"
            );
        }
    }


    @Override
    public void deleteReforestationDetail(
            ReforestationDetail detail) {

        ReforestationDetail existing =
                findById(detail.getId());


        existing.setDeleted(true);

        existing.setUpdatedAt(
                LocalDateTime.now()
        );


        reforestationDetailRepository.save(
                existing
        );
    }


    @Override
    public boolean existsByUuid(
            UUID uuid) {

        return reforestationDetailRepository
                .existsByUuidAndIsDeletedFalse(uuid);
    }


    @Override
    public ReforestationDetail mapToEntity(
            Map<String, Object>
                    reforestationDetailMapping) {

        ReforestationDetail detail =
                new ReforestationDetail();


        Object uuid =
                reforestationDetailMapping.get(
                        "uuid"
                );

        if (uuid != null) {

            detail.setUuid(
                    UUID.fromString(
                            uuid.toString()
                    )
            );
        }


        Object quantity =
                reforestationDetailMapping.get(
                        "quantity"
                );

        if (quantity != null) {

            detail.setQuantity(
                    ((Number) quantity)
                            .doubleValue()
            );
        }


        Object createdAt =
                reforestationDetailMapping.get(
                        "created_at"
                );

        if (createdAt != null) {

            detail.setCreatedAt(
                    LocalDateTime.parse(
                            createdAt.toString()
                    )
            );
        }


        Object updatedAt =
                reforestationDetailMapping.get(
                        "updated_at"
                );

        if (updatedAt != null) {

            detail.setUpdatedAt(
                    LocalDateTime.parse(
                            updatedAt.toString()
                    )
            );
        }


        Object synced =
                reforestationDetailMapping.get(
                        "is_synced"
                );

        if (synced != null) {

            detail.set_synced(
                    (boolean) synced
            );
        }


        Object idReforestation =
                reforestationDetailMapping.get(
                        "id_reforestation"
                );

        if (idReforestation != null) {

            detail.setReforestation(
                    reforestationService.findById(
                            ((Number) idReforestation)
                                    .intValue()
                    )
            );
        }


        Object idSpecies =
                reforestationDetailMapping.get(
                        "id_species"
                );

        if (idSpecies != null) {

            detail.setSpecies(
                    speciesService.findSpeciesEntityById(
                            ((Number) idSpecies)
                                    .intValue()
                    )
            );
        }


        return detail;
    }
}