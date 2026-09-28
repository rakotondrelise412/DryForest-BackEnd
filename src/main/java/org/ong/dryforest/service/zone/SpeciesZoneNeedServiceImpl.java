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
    public SpeciesZoneNeed createSpeciesZoneNeed(
            SpeciesZoneNeed speciesZoneNeed
    ) {

        try {

            speciesZoneNeed.set_synced(true);

            return speciesZoneNeedRepository.save(
                    speciesZoneNeed
            );

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Détail du besoin de la zone déjà existant"
            );
        }
    }


    @Override
    public SpeciesZoneNeed updateSpeciesZoneNeed(
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

        return speciesZoneNeedRepository.save(existing);
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


    // =========================================================
    // MAP -> ENTITY
    // IMPORTANT POUR LA SYNCHRONISATION
    // =========================================================

    @Override
    public SpeciesZoneNeed mapToEntity(
            Map<String, Object> speciesZoneNeedMapping
    ) {

        SpeciesZoneNeed entity =
                new SpeciesZoneNeed();


        // UUID
        Object uuidValue =
                speciesZoneNeedMapping.get("uuid");

        if (uuidValue != null) {

            entity.setUuid(
                    UUID.fromString(
                            uuidValue.toString()
                    )
            );
        }


        // SPECIES
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


        // ZONE NEED
        Object zoneNeedIdValue =
                speciesZoneNeedMapping.get("id_zone_need");

        if (zoneNeedIdValue != null) {

            int zoneNeedId =
                    ((Number) zoneNeedIdValue).intValue();

            ZoneNeed zoneNeed =
                    zoneNeedService.findById(zoneNeedId);

            entity.setZoneNeed(zoneNeed);
        }


        // CREATED AT
        Object createdAtValue =
                speciesZoneNeedMapping.get("createdAt");

        if (createdAtValue != null) {

            entity.setCreatedAt(
                    LocalDateTime.parse(
                            createdAtValue.toString()
                    )
            );
        }


        // UPDATED AT
        Object updatedAtValue =
                speciesZoneNeedMapping.get("updatedAt");

        if (updatedAtValue != null) {

            entity.setUpdatedAt(
                    LocalDateTime.parse(
                            updatedAtValue.toString()
                    )
            );
        }


        // IS SYNCED
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
    public List<SpeciesZoneNeedDTO> findAllDTO() {

        return speciesZoneNeedRepository
                .findAllByIsDeletedFalse()
                .stream()
                .map(speciesZoneNeedMapper::toDTO)
                .toList();
    }


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


        // Recherche de Species
        Species species =
                speciesService.findSpeciesEntityById(
                        dto.getSpeciesId()
                );


        // Recherche de ZoneNeed
        ZoneNeed zoneNeed =
                zoneNeedService.findById(
                        dto.getZoneNeedId()
                );


        // Création Entity
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


        // Sauvegarde
        SpeciesZoneNeed saved =
                speciesZoneNeedRepository.save(entity);


        // Retour DTO
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


        // Recherche de l'ancien enregistrement
        SpeciesZoneNeed existing =
                speciesZoneNeedRepository
                        .findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Détail du besoin de la zone introuvable"
                                )
                        );


        // Recherche Species
        Species species =
                speciesService.findSpeciesEntityById(
                        dto.getSpeciesId()
                );


        // Recherche ZoneNeed
        ZoneNeed zoneNeed =
                zoneNeedService.findById(
                        dto.getZoneNeedId()
                );


        // Mise à jour
        existing.setSpecies(species);

        existing.setZoneNeed(zoneNeed);

        existing.set_synced(false);


        // UUID uniquement si envoyé
        if (dto.getUuid() != null) {

            existing.setUuid(
                    dto.getUuid()
            );
        }


        // Sauvegarde
        SpeciesZoneNeed updated =
                speciesZoneNeedRepository.save(
                        existing
                );


        // Retour DTO
        return speciesZoneNeedMapper.toDTO(updated);
    }
}