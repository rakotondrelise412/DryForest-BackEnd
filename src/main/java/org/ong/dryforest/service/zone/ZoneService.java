package org.ong.dryforest.service.zone;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.entity.Zone;

public interface ZoneService {
    // Zone createZone(ZoneDTO zone);
    Zone createZone(Zone zone);

    List<Zone> findAll();

    Zone findById(int id_zone);

    Zone findByUuid(UUID uuid);

    Zone updateZone(Zone zone);

    void deleteZone(Zone zone);

    boolean existsByUuid(UUID uuid);

    Zone mapToEntity(Map<String, Object> zoneMapping);

    double totalAreaProtected();

    
}
