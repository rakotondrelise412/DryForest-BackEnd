package org.ong.dryforest.mapper;


import org.ong.dryforest.entity.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.severity.SeverityDTO;

public class SeverityMapper {
    
    public static SeverityDTO toMobileDTO(Severity severity) {
        SeverityDTO severityDTO = new SeverityDTO();

        severityDTO.setId(severity.getId());
        severityDTO.setName(severity.getName());

        return severityDTO;
    }

    public static List<SeverityDTO> toDTOList(List<Severity> severities) {
        List<SeverityDTO> severitiesDTO = new ArrayList<>();
        
        severitiesDTO = severities.stream()
                        .map(SeverityMapper::toMobileDTO)
                        .collect(Collectors.toList());

        return severitiesDTO;
    }

}
