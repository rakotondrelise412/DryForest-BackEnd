package org.ong.dryforest.service.zone;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.species.SpeciesZoneNeedDTO;
import org.ong.dryforest.entity.Species;
import org.ong.dryforest.entity.SpeciesZoneNeed;
import org.ong.dryforest.entity.ZoneNeed;
import org.ong.dryforest.mapper.SpeciesZoneNeedMapper;
import org.ong.dryforest.repository.SpeciesZoneNeedRepository;
import org.ong.dryforest.service.species.SpeciesService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpeciesZoneNeedServiceImpl
        implements SpeciesZoneNeedService {

    private final SpeciesZoneNeedRepository speciesZoneNeedRepository;

    private final SpeciesZoneNeedMapper speciesZoneNeedMapper;

    private final SpeciesService speciesService;

    private final ZoneNeedService zoneNeedService;


    // =========================================================
    // ENTITY METHODS
    // Utilisées par la synchronisation
    // =========================================================


    @Override
    @Transactional(readOnly = true)
    public List<SpeciesZoneNeed> findAll() {

        return speciesZoneNeedRepository
                .findAllByIsDeletedFalse();
    }


    @Override
    @Transactional(readOnly = true)
    public Page<SpeciesZoneNeedDTO> findAllDTO(Pageable pageable) {

        return speciesZoneNeedRepository
                .findAllByIsDeletedFalse(pageable)
                .map(speciesZoneNeedMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public SpeciesZoneNeed findById(int id) {

        return speciesZoneNeedRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Détail du besoin de la zone introuvable"
                        )
                );
    }


    @Override
    @Transactional(readOnly = true)
    public SpeciesZoneNeed findByUuid(UUID uuid) {

        return speciesZoneNeedRepository
                .findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Détail du besoin de la zone introuvable"
                        )
                );
    }


    @Override
    public void createSpeciesZoneNeed(
            SpeciesZoneNeed speciesZoneNeed
    ) {

        try {

            speciesZoneNeed.set_synced(true);

            speciesZoneNeedRepository.save(
                    speciesZoneNeed
            );

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Détail du besoin de la zone déjà existant"
            );
        }
    }


    @Override
    public void updateSpeciesZoneNeed(
            SpeciesZoneNeed speciesZoneNeed
    ) {

        SpeciesZoneNeed existing =
                findById(speciesZoneNeed.getId());

        existing.setSpecies(
                speciesZoneNeed.getSpecies()
        );

        existing.setZoneNeed(
                speciesZoneNeed.getZoneNeed()
        );

        existing.set_synced(false);

        speciesZoneNeedRepository.save(existing);
    }


    @Override
    public void deleteSpeciesZoneNeed(
            SpeciesZoneNeed speciesZoneNeed
    ) {

        speciesZoneNeed.setDeleted(true);

        speciesZoneNeed.set_synced(false);

        speciesZoneNeedRepository.save(
                speciesZoneNeed
        );
    }


    @Override
    @Transactional(readOnly = true)
    public boolean existsByUuid(UUID uuid) {

        return speciesZoneNeedRepository
                .existsByUuidAndIsDeletedFalse(uuid);
    }


    @Override
    public SpeciesZoneNeed mapToEntity(
            Map<String, Object> speciesZoneNeedMapping
    ) {

        SpeciesZoneNeed entity =
                new SpeciesZoneNeed();

        Object uuidValue =
                speciesZoneNeedMapping.get("uuid");

        if (uuidValue != null) {

            entity.setUuid(
                    UUID.fromString(
                            uuidValue.toString()
                    )
            );
        }

        Object speciesIdValue =
                speciesZoneNeedMapping.get("id_species");

        if (speciesIdValue != null) {

            int speciesId =
                    ((Number) speciesIdValue).intValue();

            Species species =
                    speciesService
                            .findSpeciesEntityById(speciesId);

            entity.setSpecies(species);
        }

        Object zoneNeedIdValue =
                speciesZoneNeedMapping.get("id_zone_need");

        if (zoneNeedIdValue != null) {

            int zoneNeedId =
                    ((Number) zoneNeedIdValue).intValue();

            ZoneNeed zoneNeed =
                    zoneNeedService.findById(zoneNeedId);

            entity.setZoneNeed(zoneNeed);
        }

        Object createdAtValue =
                speciesZoneNeedMapping.get("createdAt");

        if (createdAtValue != null) {

            entity.setCreatedAt(
                    LocalDateTime.parse(
                            createdAtValue.toString()
                    )
            );
        }

        Object updatedAtValue =
                speciesZoneNeedMapping.get("updatedAt");

        if (updatedAtValue != null) {

            entity.setUpdatedAt(
                    LocalDateTime.parse(
                            updatedAtValue.toString()
                    )
            );
        }

        Object syncedValue =
                speciesZoneNeedMapping.get("is_synced");

        if (syncedValue != null) {

            entity.set_synced(
                    Boolean.parseBoolean(
                            syncedValue.toString()
                    )
            );
        }


        return entity;
    }


    // =========================================================
    // DTO METHODS
    // Utilisées par le Controller REST
    // =========================================================


    @Override
    @Transactional(readOnly = true)
    public SpeciesZoneNeedDTO findDTOById(int id) {

        SpeciesZoneNeed entity =
                speciesZoneNeedRepository
                        .findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Détail du besoin de la zone introuvable"
                                )
                        );

        return speciesZoneNeedMapper.toDTO(entity);
    }


    @Override
    @Transactional(readOnly = true)
    public SpeciesZoneNeedDTO findDTOByUuid(
            UUID uuid
    ) {

        SpeciesZoneNeed entity =
                speciesZoneNeedRepository
                        .findByUuidAndIsDeletedFalse(uuid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Détail du besoin de la zone introuvable"
                                )
                        );

        return speciesZoneNeedMapper.toDTO(entity);
    }


    @Override
    public SpeciesZoneNeedDTO createSpeciesZoneNeedDTO(
            SpeciesZoneNeedDTO dto
    ) {

        if (dto.getSpeciesId() == null) {

            throw new IllegalArgumentException(
                    "speciesId est obligatoire"
            );
        }

        if (dto.getZoneNeedId() == null) {

            throw new IllegalArgumentException(
                    "zoneNeedId est obligatoire"
            );
        }

        Species species =
                speciesService.findSpeciesEntityById(
                        dto.getSpeciesId()
                );

        ZoneNeed zoneNeed =
                zoneNeedService.findById(
                        dto.getZoneNeedId()
                );

        SpeciesZoneNeed entity =
                new SpeciesZoneNeed();

        entity.setUuid(
                dto.getUuid() != null
                        ? dto.getUuid()
                        : UUID.randomUUID()
        );

        entity.setSpecies(species);

        entity.setZoneNeed(zoneNeed);

        entity.set_synced(true);

        SpeciesZoneNeed saved =
                speciesZoneNeedRepository.save(entity);

        return speciesZoneNeedMapper.toDTO(saved);
    }


    @Override
    public SpeciesZoneNeedDTO updateSpeciesZoneNeedDTO(
            int id,
            SpeciesZoneNeedDTO dto
    ) {

        if (dto.getSpeciesId() == null) {

            throw new IllegalArgumentException(
                    "speciesId est obligatoire"
            );
        }

        if (dto.getZoneNeedId() == null) {

            throw new IllegalArgumentException(
                    "zoneNeedId est obligatoire"
            );
        }

        SpeciesZoneNeed existing =
                speciesZoneNeedRepository
                        .findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Détail du besoin de la zone introuvable"
                                )
                        );

        Species species =
                speciesService.findSpeciesEntityById(
                        dto.getSpeciesId()
                );

        ZoneNeed zoneNeed =
                zoneNeedService.findById(
                        dto.getZoneNeedId()
                );

        existing.setSpecies(species);

        existing.setZoneNeed(zoneNeed);

        existing.set_synced(false);

        if (dto.getUuid() != null) {

            existing.setUuid(
                    dto.getUuid()
            );
        }

        SpeciesZoneNeed updated =
                speciesZoneNeedRepository.save(
                        existing
                );

        return speciesZoneNeedMapper.toDTO(updated);
    }
}