package org.ong.dryforest.service.zone;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.locationtech.jts.geom.Polygon;
import org.ong.dryforest.dto.zone.ZoneDTO;
import org.ong.dryforest.entity.Zone;
import org.ong.dryforest.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

        List<Zone> zones = this.findAll();

        double area = 0;

        for (Zone zone : zones) {
            area = area + zone.getArea();
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
                    "Zone déjà existante"
            );
        }
    }

    @Override
    public Zone createZone(ZoneDTO zoneDTO) {

        try {

            Zone zone = mapToEntity(zoneDTO);

            return createZone(zone);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Zone déjà existante"
            );
        }
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
    public Zone updateZone(Zone zone) {

        findById(zone.getId());

        return zoneRepository.save(zone);
    }

    @Override
    public void deleteZone(Zone zone) {

        findById(zone.getId());

        try {

            zoneRepository.delete(zone);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "Impossible de supprimer cette zone"
            );
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid) {

        return zoneRepository.existsByUuidAndIsDeletedFalse(uuid);
    }

    /**
     * Conversion Map -> Entity
     */
    @Override
    public Zone mapToEntity(Map<String, Object> zoneMapping) {

        Zone zone = new Zone();

        Object uuid = zoneMapping.get("uuid");

        if (uuid != null) {
            zone.setUuid(
                    UUID.fromString(uuid.toString())
            );
        } else {
            zone.setUuid(UUID.randomUUID());
        }

        zone.setName(
                (String) zoneMapping.get("name")
        );

        Object area = zoneMapping.get("area");

        if (area != null) {
            zone.setArea(
                    ((Number) area).doubleValue()
            );
        }

        Object geom = zoneMapping.get("geom");

        if (geom instanceof Polygon) {
            zone.setGeom((Polygon) geom);
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
                    (Boolean) synced
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

    /**
     * Conversion ZoneDTO -> Entity
     */
    @Override
    public Zone mapToEntity(ZoneDTO zoneDTO) {

        Zone zone = new Zone();

        if (zoneDTO.getUuid() != null) {
            zone.setUuid(zoneDTO.getUuid());
        } else {
            zone.setUuid(UUID.randomUUID());
        }

        zone.setName(
                zoneDTO.getName()
        );

        zone.setArea(
                zoneDTO.getArea()
        );

        /*
         * Attention :
         * zoneDTO.getGeom() est une Map GeoJSON.
         * Elle ne peut pas être directement castée en Polygon.
         *
         * Si ton frontend envoie déjà un Polygon JTS,
         * cette partie peut être adaptée.
         */

        zone.setCreatedAt(
                zoneDTO.getCreated_at()
        );

        zone.setUpdatedAt(
                zoneDTO.getUpdated_at()
        );

        zone.set_synced(
                zoneDTO.isIs_synced()
        );

        zone.setTypeZone(
                typeZoneService.findById(
                        zoneDTO.getId_type_zone()
                )
        );

        return zone;
    }
}