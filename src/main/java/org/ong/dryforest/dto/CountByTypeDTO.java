package org.ong.dryforest.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountByTypeDTO {
    private String typeName;
    private Long count;
}

