package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.zone.ZoneNeedDTO;
import org.ong.dryforest.entity.ZoneNeed;
import org.springframework.stereotype.Component;

@Component
public class ZoneNeedMapper {

    public ZoneNeedDTO toDTO(ZoneNeed zoneNeed) {

        return new ZoneNeedDTO(
                zoneNeed.getId(),
                zoneNeed.getUuid() != null
                        ? zoneNeed.getUuid().toString()
                        : null,
                zoneNeed.getZone() != null
                        ? zoneNeed.getZone().getId()
                        : 0
        );
    }
}