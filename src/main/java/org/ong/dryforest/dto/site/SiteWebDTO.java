package org.ong.dryforest.dto.site;

import lombok.Data;

@Data
public class SiteWebDTO {
    private int id;
    private String name;
    private double longitude;
    private double latitude;
}