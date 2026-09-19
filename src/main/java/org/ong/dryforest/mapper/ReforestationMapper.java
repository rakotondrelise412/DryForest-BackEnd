package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.reforestation.LatestReforestationDTO;
import org.ong.dryforest.entity.Reforestation;
import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.stereotype.Component;

@Component
public class ReforestationMapper {
    private ZoneService zoneService;

    public ReforestationMapper(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    public LatestReforestationDTO toLatestReforestationDTO(Reforestation reforestation){
        if (reforestation == null) return null;
        LatestReforestationDTO latestReforestationDTO = new LatestReforestationDTO();

        latestReforestationDTO.setQuantity(reforestation.getQuantity());
        latestReforestationDTO.setZone_name(zoneService.findById(reforestation.getZone().getId()).getName());

        return latestReforestationDTO;
    }
}
