package org.ong.dryforest.service.severity;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.Severity;

public interface SeverityService {

    Severity findSeverityById(int id_severity);

    List<Severity> findAllSeverities();

    List<Severity> findAllSeveritiesUpdatedSince(LocalDateTime last_sync);

    Severity createSeverity(Severity severity);

    Severity updateSeverity(Severity severity);

    void deleteSeverity(Severity severity);
    
}
