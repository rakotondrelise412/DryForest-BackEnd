package org.ong.dryforest.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;

public class PolygonToMap {
    
    // Convertit un JTS Polygon en Map<String,Object> (GeoJSON-like)
    public static Map<String, Object> polygonToMap(Polygon polygon) {
        if (polygon == null) return null;

        Map<String, Object> map = new HashMap<>();
        map.put("type", "Polygon");

        List<List<List<Double>>> coordinates = new ArrayList<>();

        // anneau extérieur
        LineString exterior = polygon.getExteriorRing();
        coordinates.add(lineStringToRingCoords(exterior));

        // anneaux intérieurs (trous)
        for (int i = 0; i < polygon.getNumInteriorRing(); i++) {
            LineString hole = polygon.getInteriorRingN(i);
            coordinates.add(lineStringToRingCoords(hole));
        }

        map.put("coordinates", coordinates);
        return map;
    }

    private static List<List<Double>> lineStringToRingCoords(LineString ls) {
        List<List<Double>> ring = new ArrayList<>();
        Coordinate[] coords = ls.getCoordinates();
        for (Coordinate c : coords) {
            List<Double> point = new ArrayList<>(2);
            point.add(c.x); // longitude / X
            point.add(c.y); // latitude  / Y
            ring.add(point);
        }
        return ring;
    }
}
