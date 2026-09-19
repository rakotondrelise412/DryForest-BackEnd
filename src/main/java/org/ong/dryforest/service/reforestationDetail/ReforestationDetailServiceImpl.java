package org.ong.dryforest.service.reforestationDetail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.entity.ReforestationDetail;
import org.ong.dryforest.repository.ReforestationDetailRepository;
import org.ong.dryforest.service.reforestation.ReforestationService;
import org.ong.dryforest.service.species.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ReforestationDetailServiceImpl implements ReforestationDetailService {
    @Autowired    
    ReforestationDetailRepository reforestationDetailRepository;
    @Autowired
    ReforestationService reforestationService;
    @Autowired
    SpeciesService speciesService;

    @Override
    public ReforestationDetail createReforestationDetail(ReforestationDetail reforestationDetail) {
        
        try {
            reforestationDetail.set_synced(true);
            if (reforestationDetail.getUuid() == null) reforestationDetail.setUuid(UUID.randomUUID());
            if (reforestationDetail.getCreatedAt() == null) reforestationDetail.setCreatedAt(LocalDateTime.now());
            reforestationDetail.setUpdatedAt(LocalDateTime.now());
            return reforestationDetailRepository.save(reforestationDetail);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Reforestation detail already exist");
        }
    }

    @Override
    public List<ReforestationDetail> findAll(){
        try{
            return reforestationDetailRepository.findAllByIsDeletedFalse();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'findAll'");
        }
    }

    @Override
    public ReforestationDetail findById(int id){
        return reforestationDetailRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new RuntimeException("Le detail de la reforestation est introuvable par son id: " + id));
    }

    @Override
    public ReforestationDetail findByUuid(UUID uuid){
        return reforestationDetailRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Le detail de la reforestation est introuvable"));
    }

    @Override
    public ReforestationDetail updateReforestationDetail(ReforestationDetail reforestationDetail){
        try {
            findById(reforestationDetail.getId());
            return reforestationDetailRepository.save(reforestationDetail);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de modifier cette detail de la reforestation");
        }
    }

    @Override
    public void deleteReforestationDetail(ReforestationDetail reforestationDetail){
        try {
            findById(reforestationDetail.getId());
            reforestationDetailRepository.delete(reforestationDetail);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cet detail de la reforestation");
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid){
        return reforestationDetailRepository.existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public ReforestationDetail mapToEntity(Map<String, Object> reforestationDetailMapping){
        ReforestationDetail reforestationDetail = new ReforestationDetail();

        reforestationDetail.setUuid(UUID.fromString((String)reforestationDetailMapping.get("uuid")));
        reforestationDetail.setQuantity(((Number)reforestationDetailMapping.get("quantity")).intValue());
        reforestationDetail.setCreatedAt(LocalDateTime.parse((String)reforestationDetailMapping.get("created_at")));
        reforestationDetail.setUpdatedAt(LocalDateTime.parse((String)reforestationDetailMapping.get("updated_at")));
        reforestationDetail.set_synced((boolean)reforestationDetailMapping.get("is_synced"));
        reforestationDetail.setReforestation(reforestationService.findById(((Number)reforestationDetailMapping.get("id_reforestation")).intValue()));
        reforestationDetail.setSpecies(speciesService.findSpeciesById(((Number)reforestationDetailMapping.get("id_species")).intValue()));

        return reforestationDetail;
    }
    
}
