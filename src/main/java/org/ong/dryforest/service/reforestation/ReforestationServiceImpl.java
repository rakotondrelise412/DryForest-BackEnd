package org.ong.dryforest.service.reforestation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.reforestation.ReforestationCountingDTO;
import org.ong.dryforest.entity.Reforestation;
import org.ong.dryforest.repository.ReforestationRepository;
import org.ong.dryforest.repository.ZoneRepository;
// import org.ong.dryforest.service.reforestationDetail.ReforestationDetailService;
import org.ong.dryforest.service.species.SpeciesService;
import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ReforestationServiceImpl implements ReforestationService{

    @Autowired
    ReforestationRepository reforestationRepository;
    @Autowired
    ZoneRepository zoneRepository;
    @Autowired
    ZoneService zoneService;
    @Autowired
    SpeciesService speciesService;
    // @Autowired
    // ReforestationDetailService reforestationDetailService;

    @Override
    public List<Reforestation> findAll() {
        try {
            return reforestationRepository.findAllByIsDeletedFalse();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'findAll'");
        }
    }

    @Override
    public Reforestation findById(int id_reforestation){
        return reforestationRepository.findById(id_reforestation).orElseThrow(() -> new RuntimeException("Reforestation not found"));
    }

    @Override
    public Reforestation findByUuid(UUID uuid){
        return reforestationRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Reforestation not found by uuid: " + uuid));
    }

    @Override
    public Reforestation createReforestation(Reforestation reforestation){
        try {
            reforestation.set_synced(true);
            return reforestationRepository.save(reforestation);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Reforestation déjà existant");
        }
    }

    @Override
    public Reforestation updateReforestation(Reforestation reforestation){
        try {
            findById(reforestation.getId());
            return reforestationRepository.save(reforestation);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de modifier cette reforestation");
        }
    }

    @Override
    public void deleteReforestation(Reforestation reforestation){
        
        try {
            findById(reforestation.getId());
            reforestationRepository.delete(reforestation);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cette reforestation");
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid){
        return reforestationRepository.existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public Reforestation mapToEntity(Map<String, Object> reforestationMapping){
        Reforestation reforestation = new Reforestation();

        reforestation.setUuid((UUID.fromString((String)reforestationMapping.get("uuid"))));
        reforestation.setDate_reforestation(LocalDate.parse((String)reforestationMapping.get("date_reforestation")));
        reforestation.setQuantity(((Number)reforestationMapping.get("quantity")).intValue());
        reforestation.setCreatedAt(LocalDateTime.parse((String)reforestationMapping.get("created_at")));
        reforestation.setUpdatedAt(LocalDateTime.parse((String)reforestationMapping.get("updated_at")));
        reforestation.set_synced((boolean)reforestationMapping.get("is_synced"));
        reforestation.setZone(zoneService.findById(((Number)reforestationMapping.get("id_zone")).intValue()));

        return reforestation;
    }

    @Override
    public int getTotalPlanted(){
        Integer total = reforestationRepository.getTotalPlanted();
        return (total == null) ? 0 : total;
    }

    @Override
    public Reforestation getTotalLastPlanted() {
        return reforestationRepository.findTopByOrderByIdDesc();
    }

    @Override
    public List<ReforestationCountingDTO> getQuantityByTypeZone() {
        return reforestationRepository.getRefQuantityByTypeZone();
    }

    // @Override
    // public Reforestation createReforestation(ReforestationDTO reforestationDTO){
    //     try {
    //         Reforestation reforestation = new Reforestation();
    //         reforestation.setZone(zoneService.findById(reforestationDTO.getIdZone()));
    //         if (reforestationDTO.getUUID() == null) {
    //             reforestationDTO.setUUID(UUID.randomUUID());
    //         }
    //         reforestation.setUuid(reforestationDTO.getUUID());
    //         reforestation.setDate_reforestation(reforestationDTO.getDateReforestation());
    //         reforestation.setQuantity(reforestationDTO.getQuantity());
    //         if (reforestationDTO.getCreatedAt() == null) {
    //             reforestationDTO.setCreatedAt(LocalDateTime.now());
    //         }
    //         reforestation.setCreated_at(reforestationDTO.getCreatedAt());
    //         reforestation.setUpdated_at(reforestationDTO.getCreatedAt());
    //         if (reforestationDTO.getIsSynced() == false) {
    //             reforestation.set_synced(true);
    //         } else {
    //             reforestation.set_synced(reforestationDTO.getIsSynced());
    //         }
            

    //         if (reforestationDTO.getReforestationDetail() != null && !reforestationDTO.getReforestationDetail().isEmpty()) {
    //             for (ReforestationDetailDTO reforestationDetailDTO : reforestationDTO.getReforestationDetail()) {
    //                 ReforestationDetail refDetail = new ReforestationDetail();
                    
    //                 refDetail.setQuantity(reforestationDetailDTO.getQuantity());

    //                 if (reforestationDetailDTO.getIdSpecies() != 0) {
    //                     Species species =  speciesService.findById(reforestationDetailDTO.getIdSpecies());
    //                     refDetail.setSpecies(species);
    //                 } else {
    //                     throw new IllegalArgumentException("Species is required! Give me that");
    //                 }
    //                 refDetail.setReforestation(reforestation);
    //                 refDetail.setUuid(reforestationDetailDTO.getUuid());
    //                 refDetail.setCreated_at(reforestationDetailDTO.getCreated_at());
    //                 refDetail.setUpdated_at(reforestationDetailDTO.getUpdated_at());
    //                 refDetail.set_synced(reforestationDetailDTO.isIs_synced());
    //                 reforestation.getReforestationDetail().add(refDetail);
    //                 //reforestationDetailService.createReforestationDetail(refDetail);
    //             }
    //         }
    //         return reforestationRepository.save(reforestation);
    //     } catch (DataIntegrityViolationException e) {
    //         throw new IllegalArgumentException("Sub plot already exist");
    //     }
    // }
    
}
