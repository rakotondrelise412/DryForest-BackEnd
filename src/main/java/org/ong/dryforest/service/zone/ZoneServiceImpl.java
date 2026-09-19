package org.ong.dryforest.service.zone;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.locationtech.jts.geom.Polygon;
import org.ong.dryforest.entity.Zone;
import org.ong.dryforest.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ZoneServiceImpl implements ZoneService{
    @Autowired
    ZoneRepository zoneRepository;
    @Autowired
    TypeZoneService typeZoneService;

    @Override
    public List<Zone> findAll() { return zoneRepository.findAllByIsDeletedFalse(); }

    @Override
    public double totalAreaProtected(){
        List<Zone> zones = this.findAll();
        double area = 0;
        for (Zone zone : zones) {
            area = area + zone.getArea();
        }
        return area;
    }

    // @Override
    // public Zone createZone(ZoneDTO zoneDTO) {
    //     try {
    //         Zone zone = new Zone();
    //         if (zoneDTO.getUuid() == null){
    //             zoneDTO.setUuid(UUID.randomUUID());
    //         } 
    //         zone.setUuid(zoneDTO.getUuid());
    //         if (zoneDTO.getCreated_at() == null){
    //             zoneDTO.setCreated_at(LocalDateTime.now());
    //             zoneDTO.setUpdated_at(LocalDateTime.now());
    //         }
    //         zone.setCreated_at(zoneDTO.getCreated_at());
    //         zone.setUpdated_at(zoneDTO.getUpdated_at());
    //         zone.set_synced(zoneDTO.isIs_synced());

    //         zone.setName(zoneDTO.getName());
    //         zone.setArea(zoneDTO.getArea());
    //         zone.setGeom((Polygon)zoneDTO.getGeom());
    //         zone.setTypeZone(typeZoneService.findById(zoneDTO.getId_type_zone()));

    //         return zoneRepository.save(zone);
    //     } catch (DataIntegrityViolationException e) {
    //         throw new IllegalArgumentException("Zone already exist");
    //     }
    // }

    @Override
    public Zone createZone(Zone zone) {
        try {
            zone.set_synced(true);
            
            return zoneRepository.save(zone);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Zone déjà existant");
        }
    }

    @Override
    public Zone findById(int id_zone){
        return zoneRepository.findByIdAndIsDeletedFalse(id_zone).orElseThrow(() -> new RuntimeException("Zone introuvable"));
    }

    @Override
    public Zone findByUuid(UUID uuid){
        return zoneRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Zone not found by this uuid: " + uuid));
    }

    @Override
    public Zone updateZone(Zone zone){
        findById(zone.getId());
        return zoneRepository.save(zone);
    }

    @Override
    public void deleteZone(Zone zone){
        findById(zone.getId());
        try {
            zoneRepository.delete(zone);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cette zone");
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid){
        return zoneRepository.existsByUuidAndIsDeletedFalse(uuid);
    }
    
    @Override
    public Zone mapToEntity(Map<String, Object> zoneMapping){
        Zone zone = new Zone();

        zone.setUuid(UUID.fromString((String)zoneMapping.get("uuid")));
        zone.setName((String)zoneMapping.get("name"));
        zone.setArea((double)zoneMapping.get("area"));
        zone.setGeom((Polygon)zoneMapping.get("geom"));
        zone.setCreatedAt(LocalDateTime.parse((String)zoneMapping.get("created_at")));
        zone.setUpdatedAt(LocalDateTime.parse((String)zoneMapping.get("updated_at")));
        zone.set_synced((boolean)zoneMapping.get("is_synced"));
        zone.setTypeZone(typeZoneService.findById(((Number)zoneMapping.get("id_type_zone")).intValue()));

        return zone;
    }
}
