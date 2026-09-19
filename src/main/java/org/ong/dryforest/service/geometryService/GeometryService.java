package org.ong.dryforest.service.geometryService;

import java.util.Map;

import org.locationtech.jts.geom.Geometry;

public interface GeometryService {
    Geometry parseGeoJson(Map<String, Object> geoJson) throws Exception;
}
