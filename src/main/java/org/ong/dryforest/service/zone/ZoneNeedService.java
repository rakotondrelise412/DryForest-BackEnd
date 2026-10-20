package org.ong.dryforest.service.zone;

import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.entity.ZoneNeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ZoneNeedService {

    ZoneNeed createZoneNeed(ZoneNeed zone_need);

    Page<ZoneNeed> findAll(Pageable pageable);

    ZoneNeed findById(int id);

    ZoneNeed findByUuid(UUID uuid);

    ZoneNeed updatZoneNeed(ZoneNeed zoneNeed);

    void deleteZoneNeed(ZoneNeed zoneNeed);

    ZoneNeed mapToEntity(Map<String, Object> zoneNeedMapping);

    boolean existsByUuid(UUID uuid);
    
}
