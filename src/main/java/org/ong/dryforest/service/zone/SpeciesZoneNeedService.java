package org.ong.dryforest.service.zone;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.entity.SpeciesZoneNeed;

public interface SpeciesZoneNeedService {

    List<SpeciesZoneNeed> findAll();

    SpeciesZoneNeed findById(int id);

    SpeciesZoneNeed findByUuid(UUID uuid);

    SpeciesZoneNeed createSpeciesZoneNeed(SpeciesZoneNeed speciesZoneNeed);

    SpeciesZoneNeed updateSpeciesZoneNeed(SpeciesZoneNeed speciesZoneNeed);

    void deleteSpeciesZoneNeed(SpeciesZoneNeed speciesZoneNeed);

    boolean existsByUuid(UUID uuid);

    SpeciesZoneNeed mapToEntity(Map<String, Object> speciesZoneNeedMapping);
    
}
