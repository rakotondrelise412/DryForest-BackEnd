package org.ong.dryforest.dto.zone;

import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneWebDTO {

    private int id_zone;

    private UUID uuid;

    private String name;

    private double area;

    private Map<String, Object> geom;

    private int id_type_zone;

    private boolean is_synced;
}
