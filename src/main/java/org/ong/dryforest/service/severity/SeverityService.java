package org.ong.dryforest.service.severity;

import org.ong.dryforest.dto.severity.SeverityDTO;
import org.ong.dryforest.entity.Severity;

import java.time.LocalDateTime;
import java.util.List;

public interface SeverityService {

    // ==========================================
    // ENTITY
    // Utilisé par les autres entities/services
    // ==========================================

    Severity findSeverityEntityById(int id);

    // ==========================================
    // DTO
    // Utilisé par les API
    // ==========================================

    SeverityDTO findSeverityById(int id);

    List<SeverityDTO> findAllSeverities();

    List<SeverityDTO> findAllSeveritiesUpdatedSince(
            LocalDateTime lastSync
    );

    // ==========================================
    // CRUD
    // ==========================================

    SeverityDTO createSeverity(
            SeverityDTO dto
    );

    SeverityDTO updateSeverity(
            int id,
            SeverityDTO dto
    );

    void deleteSeverity(int id);
}