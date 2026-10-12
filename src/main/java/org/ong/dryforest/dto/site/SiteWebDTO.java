package org.ong.dryforest.dto.site;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SiteWebDTO {
    private int id;
    private String name;
    private double longitude;
    private double latitude;
}