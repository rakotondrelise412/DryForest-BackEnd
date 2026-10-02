package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.severity.SeverityDTO;
import org.ong.dryforest.entity.Severity;

import java.util.List;
import java.util.stream.Collectors;

public class SeverityMapper {

    private SeverityMapper() {
    }

    public static SeverityDTO toDTO(Severity severity) {

        if (severity == null) {
            return null;
        }

        SeverityDTO dto = new SeverityDTO();

        dto.setId(severity.getId());
        dto.setName(severity.getName());

        return dto;
    }

    public static List<SeverityDTO> toDTOList(
            List<Severity> severities) {

        return severities.stream()
                .map(SeverityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Severity toEntity(SeverityDTO dto) {

        if (dto == null) {
            return null;
        }

        Severity severity = new Severity();

        severity.setId(dto.getId());
        severity.setName(dto.getName());

        return severity;
    }

    public static void updateEntity(
            Severity severity,
            SeverityDTO dto) {

        severity.setName(dto.getName());
    }
}