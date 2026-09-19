package org.ong.dryforest.service.severity;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.Severity;
import org.ong.dryforest.repository.SeverityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SeverityServiceImpl implements SeverityService{
    @Autowired
    private SeverityRepository severityRepository;

    @Override
    public Severity findSeverityById(int id_severity) {
        return severityRepository.findByIdAndIsDeletedFalse(id_severity)
                .orElseThrow(() -> new RuntimeException("Niveau de gravité '" + id_severity + "' introuvable"));
    }

    @Override
    public List<Severity> findAllSeverities() {
        return severityRepository.findAllByIsDeletedFalse();
    }

    @Override
    public List<Severity> findAllSeveritiesUpdatedSince(LocalDateTime last_sync) {
        return severityRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public Severity createSeverity(Severity severity) {
        try {
            return severityRepository.save(severity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Niveau de gravité déjà créé");
        }
    }

    @Override
    public Severity updateSeverity(Severity severity) {
        findSeverityById(severity.getId());

        return severityRepository.save(severity);
    }

    @Override
    public void deleteSeverity(Severity severity) {
        findSeverityById(severity.getId());

        try {
            severityRepository.delete(severity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer ce niveau de gravité");
        }
    }
}
