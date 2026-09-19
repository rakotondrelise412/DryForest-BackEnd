package org.ong.dryforest.service.animalTracking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.ong.dryforest.dto.animaltracking.AnimalObservedByZoneDTO;
import org.ong.dryforest.dto.animaltracking.AnimalTrackingDTO;
import org.ong.dryforest.dto.animaltracking.AnimalTrackingDetailDTO;
import org.ong.dryforest.entity.Animal;
import org.ong.dryforest.entity.AnimalTracking;
import org.ong.dryforest.entity.AnimalTrackingDetail;
import org.ong.dryforest.repository.AnimalTrackingRepository;
import org.ong.dryforest.service.geometryService.GeometryService;
import org.ong.dryforest.service.plantationBlock.PlantationBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AnimalTrackingServiceImpl implements AnimalTrackingService{


    private final GeometryService geometryService;
    private final AnimalService animalService;
    private final AnimalTrackingRepository animalTrackingRepository;
    private final PlantationBlockService plantationBlockService;

    @Autowired
    public AnimalTrackingServiceImpl(
            GeometryService geometryService,
            AnimalService animalService,
            AnimalTrackingRepository animalTrackingRepository,
            PlantationBlockService plantationBlockService) {
        this.geometryService = geometryService;
        this.animalService = animalService;
        this.animalTrackingRepository = animalTrackingRepository;
        this.plantationBlockService = plantationBlockService;
    }

    @Override
    public AnimalTracking createAnimalTracking(AnimalTracking animalTracking){
        animalTracking.set_synced(true);
        return animalTrackingRepository.save(animalTracking);
    }

    @Override
    public List<AnimalTracking> findAll(){
        return animalTrackingRepository.findAllByIsDeletedFalse();
    }

    @Override
    public AnimalTracking findById(int id){
        return animalTrackingRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new RuntimeException("L'animal est introuvable par l'id : " + id));
    }

    @Override
    public AnimalTracking findByUuid(UUID uuid){
        return animalTrackingRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("L'animal est introuvable par l'uuid : " + uuid));
    }

    @Override
    public AnimalTracking updateAnimalTracking(AnimalTracking animalTracking){
        findById(animalTracking.getId());
        return animalTrackingRepository.save(animalTracking);
    }

    @Override
    public void deleteAnimalTracking(AnimalTracking animalTracking){
        try {
            findById(animalTracking.getId());
            animalTrackingRepository.delete(animalTracking);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cette animal");
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid){
        return animalTrackingRepository.existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public AnimalTracking mapToEntity(Map<String, Object> animalTrackingMapping){
        AnimalTracking animalTracking = new AnimalTracking();

        animalTracking.setUuid(UUID.fromString((String)animalTrackingMapping.get("uuid")));
        animalTracking.setDate_tracking(LocalDateTime.parse((String)animalTrackingMapping.get("date_tracking")));
        animalTracking.setDescription((String)animalTrackingMapping.get("description"));
        animalTracking.setLocation((Point)animalTrackingMapping.get("location"));
        animalTracking.setPlantationBlock(plantationBlockService.findById(((Integer)animalTrackingMapping.get("id_plantation_block")).intValue()));
        animalTracking.setCreatedAt(LocalDateTime.parse((String)animalTrackingMapping.get("created_at")));
        animalTracking.setUpdatedAt(LocalDateTime.parse((String)animalTrackingMapping.get("updated_at")));
        animalTracking.set_synced((boolean)animalTrackingMapping.get("is_synced"));

        return animalTracking;
    }

    @Override
    public AnimalTracking create(AnimalTrackingDTO animalTrackingDTO) throws Exception{
        AnimalTracking animalTracking = new AnimalTracking();

        if (animalTrackingDTO.getUuid() == null) {
            animalTrackingDTO.setUuid(UUID.randomUUID());
        }
        animalTracking.setUuid(animalTrackingDTO.getUuid());
        if (animalTrackingDTO.getCreated_at() == null) {
            animalTrackingDTO.setCreated_at(LocalDateTime.now());
        }
        animalTracking.setCreatedAt(animalTrackingDTO.getCreated_at());
        animalTracking.setUpdatedAt(animalTrackingDTO.getCreated_at());
        if (animalTrackingDTO.isIs_synced() == false) {
            animalTrackingDTO.setIs_synced(true);
        }
        animalTracking.set_synced(animalTrackingDTO.isIs_synced());

        Geometry location = geometryService.parseGeoJson(animalTrackingDTO.getLocation());
        animalTracking.setLocation((Point)location);
        animalTracking.setDate_tracking(animalTrackingDTO.getDate_tracking());
        animalTracking.setHave_seen(animalTrackingDTO.isHave_seen());
        animalTracking.setDescription(animalTrackingDTO.getDescription());
        if (animalTrackingDTO.getAnimal_tracking_details() != null && !animalTrackingDTO.getAnimal_tracking_details().isEmpty()) {
            for (AnimalTrackingDetailDTO animalTrackingDetailDTO : animalTrackingDTO.getAnimal_tracking_details()) {
                AnimalTrackingDetail animalTrackingDetail = new AnimalTrackingDetail();

                if (animalTrackingDetailDTO.getId_animal() != 0) {
                    Animal animal = animalService.findById(animalTrackingDetailDTO.getId_animal());
                    animalTrackingDetail.setAnimal(animal);
                } else {
                    throw new IllegalArgumentException("Animal is required! Give me that");
                }
                animalTrackingDetail.setUuid(animalTrackingDetailDTO.getUuid());
                animalTrackingDetail.setCreatedAt(animalTrackingDetailDTO.getCreated_at());
                animalTrackingDetail.setUpdatedAt(animalTrackingDetailDTO.getUpdated_at());
                animalTrackingDetail.set_synced(animalTrackingDetailDTO.isIs_synced());
                animalTracking.getAnimal_tracking_details().add(animalTrackingDetail);
                //animalTrackingDetailService.create(animalTrackingDetail);
            }
        }
        return animalTrackingRepository.save(animalTracking);
    }

    @Override
    public List<AnimalObservedByZoneDTO> getObservedAnimalsByZone() {
        return animalTrackingRepository.findObservedAnimalsByZone();
    }
}
