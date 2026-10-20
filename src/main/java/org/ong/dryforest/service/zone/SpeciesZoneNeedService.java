package org.ong.dryforest.service.zone;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.species.SpeciesZoneNeedDTO;
import org.ong.dryforest.entity.SpeciesZoneNeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpeciesZoneNeedService {

    // =========================================================
    // MÉTHODES ENTITY
    // Utilisées par la synchronisation
    // =========================================================

    List<SpeciesZoneNeed> findAll();

    Page<SpeciesZoneNeedDTO> findAllDTO(Pageable pageable);

    SpeciesZoneNeed findById(int id);

    SpeciesZoneNeed findByUuid(UUID uuid);

    void createSpeciesZoneNeed(
            SpeciesZoneNeed speciesZoneNeed
    );

    void updateSpeciesZoneNeed(
            SpeciesZoneNeed speciesZoneNeed
    );

    void deleteSpeciesZoneNeed(
            SpeciesZoneNeed speciesZoneNeed
    );

    boolean existsByUuid(UUID uuid);

    SpeciesZoneNeed mapToEntity(
            Map<String, Object> speciesZoneNeedMapping
    );


    // =========================================================
    // MÉTHODES DTO
    // Utilisées par le Controller REST
    // =========================================================

    SpeciesZoneNeedDTO findDTOById(int id);

    SpeciesZoneNeedDTO findDTOByUuid(UUID uuid);

    SpeciesZoneNeedDTO createSpeciesZoneNeedDTO(
            SpeciesZoneNeedDTO dto
    );

    SpeciesZoneNeedDTO updateSpeciesZoneNeedDTO(
            int id,
            SpeciesZoneNeedDTO dto
    );
}