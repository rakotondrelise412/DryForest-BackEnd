package org.ong.dryforest.service.zone;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.zone.ZoneDTO;
import org.ong.dryforest.entity.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ZoneService {

    Zone createZone(Zone zone);

    Page<Zone> findAll(Pageable pageable);

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