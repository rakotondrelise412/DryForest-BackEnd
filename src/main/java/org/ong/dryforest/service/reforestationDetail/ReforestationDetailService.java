package org.ong.dryforest.service.reforestationDetail;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.reforestation.ReforestationDetailDTO;
import org.ong.dryforest.entity.ReforestationDetail;

public interface ReforestationDetailService {

    ReforestationDetail createReforestationDetail(
            ReforestationDetailDTO dto);

    List<ReforestationDetail> findAll();

    ReforestationDetail findById(int id);

    ReforestationDetail findByUuid(UUID uuid);

    ReforestationDetail updateReforestationDetail(
            int id,
            ReforestationDetailDTO dto);

    void deleteReforestationDetail(int id);

    boolean existsByUuid(UUID uuid);

    ReforestationDetail mapToEntity(
            Map<String, Object> reforestationDetailMapping);


    void createReforestationDetail(ReforestationDetail detail);

    void updateReforestationDetail(
            ReforestationDetail detail
    );
}
