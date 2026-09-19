package org.ong.dryforest.service.zone;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.entity.ZoneNeed;

public interface ZoneNeedService {

    ZoneNeed createZoneNeed(ZoneNeed zone_need);

    List<ZoneNeed> findAll();

    ZoneNeed findById(int id);

    ZoneNeed findByUuid(UUID uuid);

    ZoneNeed updatZoneNeed(ZoneNeed zoneNeed);

    void deleteZoneNeed(ZoneNeed zoneNeed);

    ZoneNeed mapToEntity(Map<String, Object> zoneNeedMapping);

    boolean existsByUuid(UUID uuid);
    
}
