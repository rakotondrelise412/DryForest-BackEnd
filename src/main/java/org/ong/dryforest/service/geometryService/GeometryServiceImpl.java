package org.ong.dryforest.service.geometryService;

import java.util.Map;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeometryServiceImpl implements GeometryService{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Geometry parseGeoJson(Map<String, Object> geoJson) throws Exception{
        String geoJsonString = objectMapper.writeValueAsString(geoJson);

        GeoJsonReader reader = new GeoJsonReader();
        return reader.read(geoJsonString);
    }
}
