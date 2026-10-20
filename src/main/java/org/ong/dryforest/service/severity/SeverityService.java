package org.ong.dryforest.service.severity;

import org.ong.dryforest.dto.severity.SeverityDTO;
import org.ong.dryforest.entity.Severity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface SeverityService {

    Severity findSeverityEntityById(int id);

    SeverityDTO findSeverityById(int id);


    Page<SeverityDTO> findAllSeverities(Pageable pageable);

    List<SeverityDTO> findAllSeveritiesUpdatedSince(
            LocalDateTime lastSync
    );

    SeverityDTO createSeverity(
            SeverityDTO dto
    );

    SeverityDTO updateSeverity(
            int id,
            SeverityDTO dto
    );

    void deleteSeverity(int id);
}