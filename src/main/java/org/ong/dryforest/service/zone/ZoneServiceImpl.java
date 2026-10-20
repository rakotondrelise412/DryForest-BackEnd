package org.ong.dryforest.service.zone;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.geojson.GeoJsonReader;

import org.ong.dryforest.dto.zone.ZoneDTO;
import org.ong.dryforest.entity.Zone;
import org.ong.dryforest.repository.ZoneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private TypeZoneService typeZoneService;


    @Override
    public List<Zone> findAll() {
        return zoneRepository.findAllByIsDeletedFalse();
    }


    @Override
    public double totalAreaProtected() {

        List<Zone> zones = findAll();

        double area = 0;

        for (Zone zone : zones) {
            area += zone.getArea();
        }

        return area;
    }


    @Override
    public Zone createZone(Zone zone) {

        try {
            if (zone.getUuid() == null) {
                zone.setUuid(UUID.randomUUID());
            }

            zone.set_synced(true);

            return zoneRepository.save(zone);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Zone déjà existante",
                    e
            );
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Zone> findAll(Pageable pageable) {
        return zoneRepository.findAllByIsDeletedFalse(pageable);
    }


    @Override
    public Zone findById(int id_zone) {

        return zoneRepository
                .findByIdAndIsDeletedFalse(id_zone)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Zone introuvable"
                        )
                );
    }



    @Override
    public Zone findByUuid(UUID uuid) {

        return zoneRepository
                .findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Zone introuvable avec cet UUID : " + uuid
                        )
                );
    }


    @Override
    @Transactional
    public Zone updateZone(Zone zone) {

        Zone existingZone = findById(zone.getId());

        existingZone.setName(zone.getName());
        existingZone.setArea(zone.getArea());

        if (zone.getUuid() != null) {
            existingZone.setUuid(zone.getUuid());
        }

        if (zone.getGeom() != null) {
            existingZone.setGeom(zone.getGeom());
        }

        if (zone.getTypeZone() != null) {
            existingZone.setTypeZone(zone.getTypeZone());
        }

        existingZone.set_synced(zone.is_synced());

        return zoneRepository.save(existingZone);
    }


    @Override
    public void deleteZone(Zone zone) {

        findById(zone.getId());

        try {
            zoneRepository.delete(zone);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "Impossible de supprimer cette zone",
                    e
            );
        }
    }


    @Override
    public boolean existsByUuid(UUID uuid) {
        return zoneRepository.existsByUuidAndIsDeletedFalse(uuid);
    }


    @Override
    public Zone mapToEntity(Map<String, Object> zoneMapping) {

        Zone zone = new Zone();

        Object uuid = zoneMapping.get("uuid");

        if (uuid != null) {
            zone.setUuid(UUID.fromString(uuid.toString()));
        } else {
            zone.setUuid(UUID.randomUUID());
        }

        zone.setName((String) zoneMapping.get("name"));

        Object area = zoneMapping.get("area");

        if (area != null) {
            zone.setArea(((Number) area).doubleValue());
        }

        Object geom = zoneMapping.get("geom");

        if (geom != null) {
            try {
                String geoJson =
                        new ObjectMapper().writeValueAsString(geom);

                Geometry geometry =
                        new GeoJsonReader().read(geoJson);

                if (!(geometry instanceof Polygon polygon)) {
                    throw new IllegalArgumentException(
                            "La géométrie doit être un Polygon"
                    );
                }

                polygon.setSRID(4326);
                zone.setGeom(polygon);

            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Impossible de convertir la géométrie GeoJSON",
                        e
                );
            }
        }

        Object createdAt = zoneMapping.get("created_at");

        if (createdAt != null) {
            zone.setCreatedAt(
                    LocalDateTime.parse(createdAt.toString())
            );
        }

        Object updatedAt = zoneMapping.get("updated_at");

        if (updatedAt != null) {
            zone.setUpdatedAt(
                    LocalDateTime.parse(updatedAt.toString())
            );
        }

        Object synced = zoneMapping.get("is_synced");

        if (synced != null) {
            zone.set_synced(
                    Boolean.parseBoolean(synced.toString())
            );
        }

        Object typeZoneId = zoneMapping.get("id_type_zone");

        if (typeZoneId != null) {
            zone.setTypeZone(
                    typeZoneService.findById(
                            ((Number) typeZoneId).intValue()
                    )
            );
        }

        return zone;
    }


    @Override
    public Zone mapToEntity(ZoneDTO zoneDTO) {

        Zone zone = new Zone();

        if (zoneDTO.getUuid() != null) {
            zone.setUuid(zoneDTO.getUuid());
        } else {
            zone.setUuid(UUID.randomUUID());
        }

        zone.setName(zoneDTO.getName());
        zone.setArea(zoneDTO.getArea());

        LocalDateTime now = LocalDateTime.now();

        zone.setCreatedAt(
                zoneDTO.getCreated_at() != null
                        ? zoneDTO.getCreated_at()
                        : now
        );

        zone.setUpdatedAt(
                zoneDTO.getUpdated_at() != null
                        ? zoneDTO.getUpdated_at()
                        : now
        );

        zone.set_synced(zoneDTO.is_synced());

        if (zoneDTO.getGeom() != null) {
            try {
                String geoJson =
                        new ObjectMapper().writeValueAsString(
                                zoneDTO.getGeom()
                        );

                Geometry geometry =
                        new GeoJsonReader().read(geoJson);

                if (!(geometry instanceof Polygon polygon)) {
                    throw new IllegalArgumentException(
                            "La géométrie doit être un Polygon"
                    );
                }

                polygon.setSRID(4326);
                zone.setGeom(polygon);

            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Impossible de convertir la géométrie GeoJSON",
                        e
                );
            }
        }

        if (zoneDTO.getId_type_zone() > 0) {
            zone.setTypeZone(
                    typeZoneService.findById(
                            zoneDTO.getId_type_zone()
                    )
            );
        }

        return zone;
    }
}
