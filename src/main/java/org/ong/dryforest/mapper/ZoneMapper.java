package org.ong.dryforest.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.geojson.GeoJsonWriter;
import org.ong.dryforest.dto.zone.ZoneMobileDTO;
import org.ong.dryforest.dto.zone.ZoneWebDTO;
import org.ong.dryforest.entity.Zone;

import java.util.Map;

public class ZoneMapper {

    private static final ObjectMapper objectMapper =
            new ObjectMapper();

    /**
     * Conversion Entity -> DTO Web
     */
    public static ZoneWebDTO toZoneWebDTO(Zone zone) {

        if (zone == null) {
            return null;
        }

        ZoneWebDTO dto = new ZoneWebDTO();

        dto.setId_zone(zone.getId());
        dto.setUuid(zone.getUuid());
        dto.setName(zone.getName());
        dto.setArea(zone.getArea());
        dto.setGeom(toGeoJson(zone.getGeom()));
        dto.set_synced(zone.is_synced());

        if (zone.getTypeZone() != null) {
            dto.setId_type_zone(
                    zone.getTypeZone().getId()
            );
        }

        if (zone.getSite() != null) {
            dto.setId_site(zone.getSite().getId());
        }

        return dto;
    }

    /**
     * Conversion Entity -> DTO Mobile
     */
    public static ZoneMobileDTO toZoneMobileDTO(Zone zone) {

        if (zone == null) {
            return null;
        }

        ZoneMobileDTO dto = new ZoneMobileDTO();

        dto.setId_zone(zone.getId());
        dto.setName(zone.getName());

        if (zone.getSite() != null) {
            dto.setId_site(zone.getSite().getId());
        }

        return dto;
    }

    /**
     * Conversion Polygon -> GeoJSON
     */
    private static Map<String, Object> toGeoJson(
            Polygon polygon) {

        if (polygon == null) {
            return null;
        }

        try {
            String geoJson =
                    new GeoJsonWriter().write(polygon);

            return objectMapper.readValue(
                    geoJson,
                    new TypeReference<>() {
                    }
            );

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Erreur lors de la conversion de la géométrie en GeoJSON",
                    e
            );
        }
    }
}
