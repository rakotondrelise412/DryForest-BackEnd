package org.ong.dryforest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountByTypeDTO {
    private String typeName;
    private Long count;
}

