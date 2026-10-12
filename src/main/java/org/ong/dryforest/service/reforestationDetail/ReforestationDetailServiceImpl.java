package org.ong.dryforest.service.reforestationDetail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.reforestation.ReforestationDetailDTO;
import org.ong.dryforest.entity.ReforestationDetail;
import org.ong.dryforest.repository.ReforestationDetailRepository;
import org.ong.dryforest.service.reforestation.ReforestationService;
import org.ong.dryforest.service.species.SpeciesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReforestationDetailServiceImpl
        implements ReforestationDetailService {

    @Autowired
    private ReforestationDetailRepository
            reforestationDetailRepository;

    @Autowired
    private ReforestationService reforestationService;

    @Autowired
    private SpeciesService speciesService;

    @Override
    @Transactional
    public ReforestationDetail createReforestationDetail(
            ReforestationDetailDTO dto) {

        if (dto.getId_reforestation() <= 0) {
            throw new IllegalArgumentException(
                    "id_reforestation est obligatoire");
        }

        if (dto.getId_species() <= 0) {
            throw new IllegalArgumentException(
                    "id_species est obligatoire");
        }

        ReforestationDetail detail =
                new ReforestationDetail();

        detail.setReforestation(
                reforestationService.findById(
                        dto.getId_reforestation()));

        detail.setSpecies(
                speciesService.findSpeciesEntityById(
                        dto.getId_species()));

        detail.setQuantity(dto.getQuantity());

        detail.setUuid(
                dto.getUuid() != null
                        ? dto.getUuid()
                        : UUID.randomUUID());

        LocalDateTime now = LocalDateTime.now();

        detail.setCreatedAt(now);
        detail.setUpdatedAt(now);
        detail.set_synced(true);

        try {
            return reforestationDetailRepository.save(detail);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Impossible de créer le détail de reboisement.",
                    e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReforestationDetail> findAll() {
        return reforestationDetailRepository
                .findAllByIsDeletedFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public ReforestationDetail findById(int id) {
        return reforestationDetailRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Détail de reboisement introuvable : "
                                        + id));
    }

    @Override
    @Transactional(readOnly = true)
    public ReforestationDetail findByUuid(UUID uuid) {
        return reforestationDetailRepository
                .findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Détail de reboisement introuvable : "
                                        + uuid));
    }

    @Override
    @Transactional
    public ReforestationDetail updateReforestationDetail(
            int id,
            ReforestationDetailDTO dto) {

        ReforestationDetail existing = findById(id);

        if (dto.getId_reforestation() <= 0) {
            throw new IllegalArgumentException(
                    "id_reforestation est obligatoire");
        }

        if (dto.getId_species() <= 0) {
            throw new IllegalArgumentException(
                    "id_species est obligatoire");
        }

        existing.setReforestation(
                reforestationService.findById(
                        dto.getId_reforestation()));

        existing.setSpecies(
                speciesService.findSpeciesEntityById(
                        dto.getId_species()));

        existing.setQuantity(dto.getQuantity());

        if (dto.getUuid() != null) {
            existing.setUuid(dto.getUuid());
        }

        existing.setUpdatedAt(LocalDateTime.now());

        return reforestationDetailRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteReforestationDetail(int id) {
        ReforestationDetail existing = findById(id);

        existing.setDeleted(true);
        existing.setUpdatedAt(LocalDateTime.now());

        reforestationDetailRepository.save(existing);
    }

    @Override
    public boolean existsByUuid(UUID uuid) {
        return reforestationDetailRepository
                .existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public void createReforestationDetail(
            ReforestationDetail detail) {

        if (detail.getUuid() == null) {
            detail.setUuid(UUID.randomUUID());
        }

        LocalDateTime now = LocalDateTime.now();

        if (detail.getCreatedAt() == null) {
            detail.setCreatedAt(now);
        }

        detail.setUpdatedAt(now);
        detail.set_synced(true);

        reforestationDetailRepository.save(detail);
    }

    @Override
    public void updateReforestationDetail(
            ReforestationDetail detail) {

        ReforestationDetail existing =
                reforestationDetailRepository
                        .findByUuidAndIsDeletedFalse(detail.getUuid())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "ReforestationDetail introuvable pour UUID : "
                                                + detail.getUuid()
                                )
                        );

        existing.setQuantity(detail.getQuantity());
        existing.setReforestation(detail.getReforestation());
        existing.setSpecies(detail.getSpecies());
        existing.setUpdatedAt(
                detail.getUpdatedAt() != null
                        ? detail.getUpdatedAt()
                        : LocalDateTime.now()
        );
        existing.set_synced(true);

        reforestationDetailRepository.save(existing);
    }

    @Override
    public ReforestationDetail mapToEntity(
            Map<String, Object> mapping) {

        ReforestationDetail detail =
                new ReforestationDetail();

        Object uuid = mapping.get("uuid");
        if (uuid != null) {
            detail.setUuid(UUID.fromString(uuid.toString()));
        }

        Object quantity = mapping.get("quantity");
        if (quantity != null) {
            detail.setQuantity(
                    ((Number) quantity).doubleValue());
        }

        Object idReforestation =
                mapping.get("id_reforestation");

        if (idReforestation != null) {
            detail.setReforestation(
                    reforestationService.findById(
                            ((Number) idReforestation).intValue()));
        }

        Object idSpecies = mapping.get("id_species");

        if (idSpecies != null) {
            detail.setSpecies(
                    speciesService.findSpeciesEntityById(
                            ((Number) idSpecies).intValue()));
        }

        return detail;
    }
}
