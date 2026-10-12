package org.ong.dryforest.service.geometryService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeometryServiceImpl
        implements GeometryService {

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Override
    public Geometry parseGeoJson(
            Map<String, Object> geoJson
    ) throws Exception {

        String geoJsonString =
                objectMapper.writeValueAsString(
                        geoJson
                );

        GeoJsonReader reader =
                new GeoJsonReader();

        return reader.read(geoJsonString);
    }

    @Override
    public Map<String, Object> toGeoJson(
            Geometry geometry
    ) {

        if (geometry == null) {
            return null;
        }

        Map<String, Object> geoJson =
                new HashMap<>();

        geoJson.put(
                "type",
                geometry.getGeometryType()
        );

        if (geometry instanceof Point point) {

            geoJson.put(
                    "coordinates",
                    List.of(
                            point.getX(),
                            point.getY()
                    )
            );
        }

        return geoJson;
    }
}