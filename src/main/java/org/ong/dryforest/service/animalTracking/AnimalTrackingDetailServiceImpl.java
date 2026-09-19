package org.ong.dryforest.service.animalTracking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.entity.Animal;
import org.ong.dryforest.entity.AnimalTracking;
import org.ong.dryforest.entity.AnimalTrackingDetail;
import org.ong.dryforest.repository.AnimalRepository;
import org.ong.dryforest.repository.AnimalTrackingDetailRepository;
import org.ong.dryforest.repository.AnimalTrackingRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AnimalTrackingDetailServiceImpl implements AnimalTrackingDetailService {

    private final AnimalTrackingDetailRepository animalTrackingDetailRepository;

    private final AnimalTrackingRepository animalTrackingRepository; 

    private final AnimalRepository animalRepository;

    public AnimalTrackingDetailServiceImpl(
        AnimalTrackingDetailRepository animalTrackingDetailRepository,
        AnimalRepository animalRepository,
        AnimalTrackingRepository animalTrackingRepository) {
    this.animalTrackingDetailRepository = animalTrackingDetailRepository;
    this.animalRepository = animalRepository;
    this.animalTrackingRepository = animalTrackingRepository;
}

    @Override
    public AnimalTrackingDetail createAnimalTrackingDetail(AnimalTrackingDetail animalTrackingDetail){
        animalTrackingDetail.set_synced(true);
        return animalTrackingDetailRepository.save(animalTrackingDetail);
    }

    @Override
    public List<AnimalTrackingDetail> findAll(){
        return animalTrackingDetailRepository.findAllByIsDeletedFalse();
    }

    @Override
    public AnimalTrackingDetail findById(int id){
        return animalTrackingDetailRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new RuntimeException("Le detail de suivi d'animal est introuvable par l'id : " + id));
    }

    @Override
    public AnimalTrackingDetail findByUuid(UUID uuid){
        return animalTrackingDetailRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Le detail de suivi d'animal est introuvable par l'uuid : " + uuid));
    }

    @Override
    public AnimalTrackingDetail updateAnimalTrackingDetail(AnimalTrackingDetail animalTrackingDetail){
        findById(animalTrackingDetail.getId());
        return animalTrackingDetailRepository.save(animalTrackingDetail);
    }

    @Override
    public void deleteAnimalTrackingDetail(AnimalTrackingDetail animalTrackingDetail){
        try {
            findById(animalTrackingDetail.getId());
            animalTrackingDetailRepository.delete(animalTrackingDetail);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cette suivi animal");
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid){
        return animalTrackingDetailRepository.existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public AnimalTrackingDetail mapToEntity(Map<String, Object> animalTrackingDetailMapping){
        AnimalTrackingDetail animalTrackingDetail = new AnimalTrackingDetail();

        animalTrackingDetail.setUuid(UUID.fromString((String)animalTrackingDetailMapping.get("uuid")));
        animalTrackingDetail.setHave_seen((boolean)animalTrackingDetailMapping.get("have_seen"));
        
        Integer idAnimal = (Integer) animalTrackingDetailMapping.get("id_animal");
        if (idAnimal != null) {
            Animal animal = animalRepository.findById(idAnimal)
                .orElseThrow(() -> new RuntimeException("Animal introuvable id: " + idAnimal));
            animalTrackingDetail.setAnimal(animal);
        }

        Integer idAnimalTracking = (Integer) animalTrackingDetailMapping.get("id_animal_tracking");
        if (idAnimalTracking != null) {
            AnimalTracking parent = animalTrackingRepository.findByIdAndIsDeletedFalse(idAnimalTracking)
                .orElseThrow(() -> new RuntimeException("AnimalTracking introuvable id: " + idAnimalTracking));
            animalTrackingDetail.setAnimalTracking(parent);
        }

        animalTrackingDetail.setCreatedAt(LocalDateTime.parse((String)animalTrackingDetailMapping.get("created_at")));
        animalTrackingDetail.setUpdatedAt(LocalDateTime.parse((String)animalTrackingDetailMapping.get("updated_at")));
        animalTrackingDetail.set_synced((boolean)animalTrackingDetailMapping.get("is_synced"));

        return animalTrackingDetail;
    }

    @Override
    public AnimalTrackingDetail create(AnimalTrackingDetail animalTrackingDetail){
        try {
            if (animalTrackingDetail.getUuid() == null) animalTrackingDetail.setUuid(UUID.randomUUID());
            if (animalTrackingDetail.getCreatedAt() == null) animalTrackingDetail.setCreatedAt(LocalDateTime.now());
            animalTrackingDetail.setUpdatedAt(LocalDateTime.now());
            return animalTrackingDetailRepository.save(animalTrackingDetail);
            
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Animal tracking detail already exist");
        }
    }
}
