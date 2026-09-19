package org.ong.dryforest.service.reforestation;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.reforestation.ReforestationCountingDTO;
import org.ong.dryforest.entity.Reforestation;

public interface ReforestationService {
    List<Reforestation> findAll();

    // Reforestation createReforestation(ReforestationDTO reforestationDTO);

    Reforestation findById(int id_reforestation);

    Reforestation findByUuid(UUID uuid);

    Reforestation createReforestation(Reforestation reforestation);

    void deleteReforestation(Reforestation reforestation);

    Reforestation updateReforestation(Reforestation reforestation);

    boolean existsByUuid(UUID uuid);

    Reforestation mapToEntity(Map<String, Object> reforestationMapping);

    int getTotalPlanted();

    Reforestation getTotalLastPlanted();

    List<ReforestationCountingDTO> getQuantityByTypeZone();

}
