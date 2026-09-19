package org.ong.dryforest.service.reforestationDetail;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.entity.ReforestationDetail;

public interface ReforestationDetailService {
    ReforestationDetail createReforestationDetail(ReforestationDetail reforestationDetail);

    List<ReforestationDetail> findAll();

    ReforestationDetail findById(int id);

    ReforestationDetail findByUuid(UUID uuid);

    ReforestationDetail updateReforestationDetail(ReforestationDetail reforestationDetail);

    void deleteReforestationDetail(ReforestationDetail reforestationDetail);

    boolean existsByUuid(UUID uuid);

    ReforestationDetail mapToEntity(Map<String, Object> reforestationDetailMapping);
}
