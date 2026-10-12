package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.reforestation.ReforestationDetailDTO;
import org.ong.dryforest.entity.ReforestationDetail;
import org.springframework.stereotype.Component;

@Component
public class ReforestationDetailMapper {

    public ReforestationDetailDTO toDTO(
            ReforestationDetail detail) {

        if (detail == null) {
            return null;
        }

        ReforestationDetailDTO dto =
                new ReforestationDetailDTO();

        dto.setId_reforestation_detail(detail.getId());

        dto.setId_reforestation(
                detail.getReforestation().getId());

        dto.setId_species(
                detail.getSpecies().getId());

        dto.setQuantity(detail.getQuantity());
        dto.setUuid(detail.getUuid());
        dto.setCreated_at(detail.getCreatedAt());
        dto.setUpdated_at(detail.getUpdatedAt());
        dto.set_synced(detail.is_synced());

        return dto;
    }
}

