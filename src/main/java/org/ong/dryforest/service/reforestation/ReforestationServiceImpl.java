package org.ong.dryforest.service.reforestation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.ong.dryforest.dto.reforestation.ReforestationCountingDTO;
import org.ong.dryforest.dto.reforestation.ReforestationDTO;
import org.ong.dryforest.dto.reforestation.ReforestationDetailDTO;
import org.ong.dryforest.entity.Reforestation;
import org.ong.dryforest.entity.ReforestationDetail;
import org.ong.dryforest.entity.Species;
import org.ong.dryforest.repository.ReforestationRepository;
import org.ong.dryforest.service.species.SpeciesService;
import org.ong.dryforest.service.zone.ZoneService;

@Service
public class ReforestationServiceImpl
        implements ReforestationService {

    @Autowired
    ReforestationRepository reforestationRepository;

    @Autowired
    ZoneService zoneService;

    @Autowired
    SpeciesService speciesService;


    @Override
    public List<Reforestation> findAll() {

        return reforestationRepository
                .findAllByIsDeletedFalse();
    }

    @Override
    public Reforestation findById(
            int id_reforestation) {

        return reforestationRepository
                .findByIdAndIsDeletedFalse(
                        id_reforestation
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Reforestation not found: "
                                        + id_reforestation
                        )
                );
    }


    @Override
    public Reforestation findByUuid(
            UUID uuid) {

        return reforestationRepository
                .findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Reforestation not found by uuid: "
                                        + uuid
                        )
                );
    }


    @Override
    @Transactional
    public Reforestation createReforestationWithDetails(
            ReforestationDTO dto) {

        Reforestation reforestation =
                new Reforestation();

        if (dto.getId_zone() == 0) {
            throw new IllegalArgumentException(
                    "id_zone est obligatoire"
            );
        }

        reforestation.setZone(
                zoneService.findById(
                        dto.getId_zone()
                )
        );

        if (dto.getUuid() != null) {

            reforestation.setUuid(
                    dto.getUuid()
            );

        } else {

            reforestation.setUuid(
                    UUID.randomUUID()
            );
        }

        reforestation.setDate_reforestation(
                dto.getDate_reforestation()
        );

        reforestation.setQuantity(
                dto.getQuantity()
        );

        reforestation.set_synced(true);

        if (dto.getReforestationDetailsDTO() != null
                && !dto.getReforestationDetailsDTO().isEmpty()) {

            for (ReforestationDetailDTO detailDTO :
                    dto.getReforestationDetailsDTO()) {

                if (detailDTO.getId_species() == 0) {

                    throw new IllegalArgumentException(
                            "id_species est obligatoire"
                    );
                }

                ReforestationDetail detail =
                        new ReforestationDetail();

                detail.setQuantity(
                        detailDTO.getQuantity()
                );

                Species species =
                        speciesService.findSpeciesEntityById(
                                detailDTO.getId_species()
                        );

                detail.setSpecies(species);

                detail.setReforestation(
                        reforestation
                );

                if (detailDTO.getUuid() != null) {

                    detail.setUuid(
                            detailDTO.getUuid()
                    );

                } else {

                    detail.setUuid(
                            UUID.randomUUID()
                    );
                }

                detail.set_synced(true);

                reforestation
                        .getReforestationDetail()
                        .add(detail);
            }
        }

        return reforestationRepository.save(
                reforestation
        );
    }

    @Override
    public Reforestation updateReforestation(
            Reforestation reforestation) {

        try {

            Reforestation existing =
                    findById(
                            reforestation.getId()
                    );


            existing.setUuid(
                    reforestation.getUuid()
            );


            existing.setDate_reforestation(
                    reforestation
                            .getDate_reforestation()
            );


            existing.setQuantity(
                    reforestation.getQuantity()
            );


            existing.setZone(
                    reforestation.getZone()
            );


            existing.setUpdatedAt(
                    LocalDateTime.now()
            );


            return reforestationRepository.save(
                    existing
            );

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "Impossible de modifier cette reforestation"
            );
        }
    }


    @Override
    public void deleteReforestation(
            Reforestation reforestation) {

        Reforestation existing =
                findById(
                        reforestation.getId()
                );


        existing.setDeleted(true);

        existing.setUpdatedAt(
                LocalDateTime.now()
        );


        reforestationRepository.save(
                existing
        );
    }


    @Override
    public boolean existsByUuid(
            UUID uuid) {

        return reforestationRepository
                .existsByUuidAndIsDeletedFalse(
                        uuid
                );
    }

    @Override
    public Reforestation mapToEntity(
            Map<String, Object> reforestationMapping) {

        Reforestation reforestation =
                new Reforestation();

        Object uuid =
                reforestationMapping.get("uuid");

        if (uuid != null) {

            reforestation.setUuid(
                    UUID.fromString(
                            uuid.toString()
                    )
            );
        }

        Object date =
                reforestationMapping.get(
                        "date_reforestation"
                );

        if (date != null) {

            reforestation.setDate_reforestation(
                    LocalDate.parse(
                            date.toString()
                    )
            );
        }

        Object quantity =
                reforestationMapping.get(
                        "quantity"
                );

        if (quantity != null) {

            reforestation.setQuantity(
                    ((Number) quantity)
                            .intValue()
            );
        }

        Object createdAt =
                reforestationMapping.get(
                        "created_at"
                );

        if (createdAt != null) {

            reforestation.setCreatedAt(
                    LocalDateTime.parse(
                            createdAt.toString()
                    )
            );
        }


        Object updatedAt =
                reforestationMapping.get(
                        "updated_at"
                );

        if (updatedAt != null) {

            reforestation.setUpdatedAt(
                    LocalDateTime.parse(
                            updatedAt.toString()
                    )
            );
        }


        Object synced =
                reforestationMapping.get(
                        "is_synced"
                );

        if (synced != null) {

            reforestation.set_synced(
                    (boolean) synced
            );
        }

        Object idZone =
                reforestationMapping.get(
                        "id_zone"
                );

        if (idZone != null) {

            reforestation.setZone(
                    zoneService.findById(
                            ((Number) idZone)
                                    .intValue()
                    )
            );
        }


        return reforestation;
    }


    @Override
    public int getTotalPlanted() {

        Integer total =
                reforestationRepository
                        .getTotalPlanted();

        return total == null
                ? 0
                : total;
    }


    @Override
    public Reforestation getTotalLastPlanted() {

        return reforestationRepository
                .findTopByOrderByIdDesc();
    }


    @Override
    public List<ReforestationCountingDTO>
    getQuantityByTypeZone() {

        return reforestationRepository
                .getRefQuantityByTypeZone();
    }
}