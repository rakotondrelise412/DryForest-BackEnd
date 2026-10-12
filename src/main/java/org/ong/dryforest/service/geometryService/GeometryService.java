package org.ong.dryforest.service.geometryService;

import org.locationtech.jts.geom.Geometry;

import java.util.Map;

public interface GeometryService {

    Geometry parseGeoJson(
            Map<String, Object> geoJson
    ) throws Exception;

    Map<String, Object> toGeoJson(
            Geometry geometry
    );
}