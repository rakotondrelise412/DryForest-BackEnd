package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.zone.ZoneMobileDTO;
import org.ong.dryforest.entity.Zone;

public class ZoneMapper {
    
    public static ZoneMobileDTO toZoneMobileDTO(Zone zone){
        ZoneMobileDTO zoneMobileDTO = new ZoneMobileDTO();
        
        zoneMobileDTO.setId_zone(zone.getId());
        zoneMobileDTO.setName(zone.getName());

        return zoneMobileDTO;
    }
}
