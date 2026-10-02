package org.ong.dryforest.mapper;

import java.util.List;

import org.ong.dryforest.dto.reforestation.LatestReforestationDTO;
import org.ong.dryforest.dto.reforestation.ReforestationDetailResponseDTO;
import org.ong.dryforest.dto.reforestation.ReforestationResponseDTO;
import org.ong.dryforest.entity.Reforestation;
import org.ong.dryforest.entity.ReforestationDetail;
import org.ong.dryforest.entity.Zone;
import org.ong.dryforest.service.zone.ZoneService;

import org.springframework.stereotype.Component;

@Component
public class ReforestationMapper {

    private final ZoneService zoneService;


    public ReforestationMapper(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    public ReforestationResponseDTO toResponseDTO(
            Reforestation reforestation) {

        if (reforestation == null) {
            return null;
        }

        ReforestationResponseDTO dto =
                new ReforestationResponseDTO();

        dto.setId_reforestation(
                reforestation.getId()
        );

        dto.setUuid(
                reforestation.getUuid()
        );

        dto.setDate_reforestation(
                reforestation.getDate_reforestation()
        );

        dto.setQuantity(
                reforestation.getQuantity()
        );

        dto.setCreated_at(
                reforestation.getCreatedAt()
        );

        dto.setUpdated_at(
                reforestation.getUpdatedAt()
        );

        dto.setSynced(
                reforestation.is_synced()
        );

        if (reforestation.getZone() != null) {

            dto.setId_zone(
                    reforestation.getZone().getId()
            );
        }

        if (reforestation.getReforestationDetail() != null) {

            List<ReforestationDetailResponseDTO> details =
                    reforestation
                            .getReforestationDetail()
                            .stream()
                            .map(this::toDetailResponseDTO)
                            .toList();

            dto.setReforestationDetails(details);

        } else {

            dto.setReforestationDetails(
                    List.of()
            );
        }


        return dto;
    }


    private ReforestationDetailResponseDTO
    toDetailResponseDTO(
            ReforestationDetail detail) {

        ReforestationDetailResponseDTO dto =
                new ReforestationDetailResponseDTO();

        dto.setId_reforestation_detail(
                detail.getId()
        );

        dto.setQuantity(
                detail.getQuantity()
        );

        dto.setUuid(
                detail.getUuid()
        );

        dto.setCreated_at(
                detail.getCreatedAt()
        );

        dto.setUpdated_at(
                detail.getUpdatedAt()
        );

        dto.setSynced(
                detail.is_synced()
        );


        if (detail.getSpecies() != null) {

            dto.setId_species(
                    detail.getSpecies().getId()
            );
        }


        return dto;
    }


    public LatestReforestationDTO toLatestReforestationDTO(
            Reforestation reforestation) {

        if (reforestation == null) {
            return null;
        }

        LatestReforestationDTO dto =
                new LatestReforestationDTO();

        dto.setQuantity(
                reforestation.getQuantity()
        );

        if (reforestation.getZone() != null) {

            Zone zone =
                    zoneService.findById(
                            reforestation
                                    .getZone()
                                    .getId()
                    );

            if (zone != null) {

                dto.setZone_name(
                        zone.getName()
                );
            }
        }

        return dto;
    }
}