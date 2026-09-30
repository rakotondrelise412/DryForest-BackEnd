package org.ong.dryforest.service.severity;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.severity.SeverityDTO;
import org.ong.dryforest.entity.Severity;
import org.ong.dryforest.mapper.SeverityMapper;
import org.ong.dryforest.repository.SeverityRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeverityServiceImpl implements SeverityService {

    private final SeverityRepository severityRepository;

    @Override
    @Transactional(readOnly = true)
    public Severity findSeverityEntityById(int id) {

        return severityRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Niveau de gravité '" +
                                        id +
                                        "' introuvable"
                        )
                );
    }


    @Override
    @Transactional(readOnly = true)
    public SeverityDTO findSeverityById(int id) {

        Severity severity =
                findSeverityEntityById(id);

        return SeverityMapper.toDTO(severity);
    }


    @Override
    @Transactional(readOnly = true)
    public List<SeverityDTO> findAllSeverities() {

        List<Severity> severities =
                severityRepository.findAllByIsDeletedFalse();

        return SeverityMapper.toDTOList(severities);
    }


    @Override
    @Transactional(readOnly = true)
    public List<SeverityDTO> findAllSeveritiesUpdatedSince(
            LocalDateTime lastSync) {

        List<Severity> severities =
                severityRepository.findAllUpdatedSince(
                        lastSync
                );

        return SeverityMapper.toDTOList(severities);
    }


    @Override
    public SeverityDTO createSeverity(
            SeverityDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException(
                    "Les données sont obligatoires"
            );
        }

        if (dto.getName() == null ||
                dto.getName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Le nom du niveau de gravité est obligatoire"
            );
        }

        String name = dto.getName().trim();

        if (severityRepository
                .existsByNameIgnoreCaseAndIsDeletedFalse(name)) {

            throw new IllegalArgumentException(
                    "Le niveau de gravité '" +
                            name +
                            "' existe déjà"
            );
        }

        Severity severity =
                SeverityMapper.toEntity(dto);

        severity.setName(name);

        try {

            Severity saved =
                    severityRepository.save(severity);

            return SeverityMapper.toDTO(saved);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Le niveau de gravité '" +
                            name +
                            "' existe déjà"
            );
        }
    }


    @Override
    public SeverityDTO updateSeverity(
            int id,
            SeverityDTO dto) {

        Severity severity =
                findSeverityEntityById(id);

        if (dto == null) {
            throw new IllegalArgumentException(
                    "Les données sont obligatoires"
            );
        }

        if (dto.getName() == null ||
                dto.getName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Le nom du niveau de gravité est obligatoire"
            );
        }

        String name = dto.getName().trim();

        boolean duplicate =
                severityRepository
                        .existsByNameIgnoreCaseAndIsDeletedFalse(
                                name
                        );

        if (duplicate &&
                !severity.getName().equalsIgnoreCase(name)) {

            throw new IllegalArgumentException(
                    "Le niveau de gravité '" +
                            name +
                            "' existe déjà"
            );
        }

        SeverityMapper.updateEntity(
                severity,
                dto
        );

        severity.setName(name);

        Severity updated =
                severityRepository.save(severity);

        return SeverityMapper.toDTO(updated);
    }


    @Override
    public void deleteSeverity(int id) {

        Severity severity =
                findSeverityEntityById(id);

        severity.setDeleted(true);

        severityRepository.save(severity);
    }
}