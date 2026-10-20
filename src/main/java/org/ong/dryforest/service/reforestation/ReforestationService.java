package org.ong.dryforest.service.reforestation;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.reforestation.ReforestationDTO;
import org.ong.dryforest.dto.reforestation.ReforestationCountingDTO;
import org.ong.dryforest.entity.Reforestation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReforestationService {

    List<Reforestation> findAll();

    Page<Reforestation> findAll(Pageable pageable);

    Reforestation findById(int id_reforestation);

    Reforestation findByUuid(UUID uuid);

    void saveReforestation(
            Reforestation reforestation);

    Reforestation createReforestation(ReforestationDTO dto);

    void deleteReforestation(
            Reforestation reforestation);

    Reforestation updateReforestation(
            Reforestation reforestation);

    boolean existsByUuid(UUID uuid);

    Reforestation mapToEntity(
            Map<String, Object> reforestationMapping);

    int getTotalPlanted();

    Reforestation getTotalLastPlanted();

    List<ReforestationCountingDTO>
    getQuantityByTypeZone();
}