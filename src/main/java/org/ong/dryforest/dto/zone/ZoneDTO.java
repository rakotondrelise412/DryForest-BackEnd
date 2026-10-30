package org.ong.dryforest.dto.zone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDTO {

    private int id_zone;
    private UUID uuid;
    private String name;
    private double area;
    private Map<String, Object> geom;
    private int id_type_zone;
    private Integer id_site;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean is_synced;

}