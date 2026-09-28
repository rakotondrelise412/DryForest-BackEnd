package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.species.SpeciesZoneNeedDTO;
import org.ong.dryforest.entity.SpeciesZoneNeed;
import org.springframework.stereotype.Component;

@Component
public class SpeciesZoneNeedMapper {

    public SpeciesZoneNeedDTO toDTO(SpeciesZoneNeed entity) {

        if (entity == null) {
            return null;
        }

        SpeciesZoneNeedDTO dto = new SpeciesZoneNeedDTO();

        dto.setId(entity.getId());

        dto.setUuid(entity.getUuid());

        if (entity.getSpecies() != null) {
            dto.setSpeciesId(entity.getSpecies().getId());
        }

        if (entity.getZoneNeed() != null) {
            dto.setZoneNeedId(entity.getZoneNeed().getId());
        }

        dto.set_synced(entity.is_synced());

        dto.setCreatedAt(entity.getCreatedAt());

        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }
}