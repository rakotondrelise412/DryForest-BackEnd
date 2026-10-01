package org.ong.dryforest.service.zone;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.zone.ZoneDTO;
import org.ong.dryforest.entity.Zone;

public interface ZoneService {

    Zone createZone(Zone zone);

    Zone createZone(ZoneDTO zoneDTO);

    List<Zone> findAll();

    Zone findById(int id_zone);

    Zone findByUuid(UUID uuid);

    Zone updateZone(Zone zone);

    void deleteZone(Zone zone);

    boolean existsByUuid(UUID uuid);

    Zone mapToEntity(Map<String, Object> zoneMapping);

    Zone mapToEntity(ZoneDTO zoneDTO);

    double totalAreaProtected();
}