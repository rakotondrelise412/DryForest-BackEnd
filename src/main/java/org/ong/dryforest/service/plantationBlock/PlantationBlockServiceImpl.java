package org.ong.dryforest.service.plantationBlock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.locationtech.jts.geom.Polygon;
import org.ong.dryforest.entity.PlantationBlock;
import org.ong.dryforest.repository.PlantationBlockRepository;
import org.ong.dryforest.service.geometryService.GeometryService;
// import org.ong.dryforest.service.subPlot.SubPlotServiceImpl;
import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PlantationBlockServiceImpl implements PlantationBlockService {
    @Autowired
    PlantationBlockRepository plantationBlockRepository;
    // @Autowired
    // SubPlotServiceImpl subPlotServiceImpl;
    @Autowired
    ZoneService zoneService;
    @Autowired
    GeometryService geometryService;

    @Override
    public List<PlantationBlock> findAll() { return plantationBlockRepository.findAllByIsDeletedFalse(); }

    @Override
    public PlantationBlock findById(int id_plantationBlock){
        return plantationBlockRepository.findByIdAndIsDeletedFalse(id_plantationBlock).orElseThrow(() -> new RuntimeException("Placeau '" + id_plantationBlock + "' introuvable"));
    }

    @Override
    public PlantationBlock findByUuid(UUID uuid){
        return plantationBlockRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Pleaau '" + uuid + "' introuvable"));
    }

    @Override
    public PlantationBlock createPlantationBlock(PlantationBlock plantationBlock){
        try {
            plantationBlock.set_synced(true);
            return plantationBlockRepository.save(plantationBlock);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Placeau déjà existant");
        }
    }

    @Override
    public PlantationBlock updatePlantationBlock(PlantationBlock plantationBlock){
        findById(plantationBlock.getId());
        return plantationBlockRepository.save(plantationBlock);
    }

    @Override
    public void deletedPlantationBlock(PlantationBlock plantationBlock){
        try {
            findById(plantationBlock.getId());
            plantationBlockRepository.delete(plantationBlock);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer ce placeau");
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid){
        return plantationBlockRepository.existsByUuidAndIsDeletedFalse(uuid);
    }


    @Override
    public PlantationBlock mapToEntity(Map<String, Object> plantationBlockMapping){
        PlantationBlock plantationBlock = new PlantationBlock();

        plantationBlock.setUuid(UUID.fromString((String)plantationBlockMapping.get("uuid")));
        plantationBlock.setName((String)plantationBlockMapping.get("name"));
        plantationBlock.setWidth(((Number)plantationBlockMapping.get("width")).doubleValue());
        plantationBlock.setLength(((Number)plantationBlockMapping.get("height")).doubleValue());
        plantationBlock.setNb_sub_plot((Integer)plantationBlockMapping.get("nb_sub_plot"));
        plantationBlock.setGeom((Polygon)plantationBlockMapping.get("geom"));
        plantationBlock.setCreatedAt(LocalDateTime.parse((String)plantationBlockMapping.get("created_at")));
        plantationBlock.setUpdatedAt(LocalDateTime.parse((String)plantationBlockMapping.get("updated_at")));
        plantationBlock.set_synced((boolean)plantationBlockMapping.get("is_synced"));
        plantationBlock.setZone(zoneService.findById(((Number)plantationBlockMapping.get("id_zone")).intValue()));

        return plantationBlock;
    }
    
    // @Override
    // public PlantationBlock createPlantationBlock(PlantationBlockDTO plantationBlockDTO) throws Exception{
    //     try {
    //         PlantationBlock plantationB = new PlantationBlock();
    //         if (plantationBlockDTO.getUuid() == null) {
    //             plantationBlockDTO.setUuid(UUID.randomUUID());
    //         }
    //         plantationB.setUuid(plantationBlockDTO.getUuid());
    //         plantationB.setName(plantationBlockDTO.getName());
    //         plantationB.setWidth(plantationBlockDTO.getWidth());
    //         plantationB.setHeight(plantationBlockDTO.getHeight());
    //         plantationB.setNb_sub_plot(plantationBlockDTO.getNb_sub_plot());
    //         Geometry geom = geometryService.parseGeoJson(plantationBlockDTO.getGeom());
    //         plantationB.setGeom((Polygon)geom);
    //         if (plantationBlockDTO.getCreated_at() == null) {
    //             plantationBlockDTO.setCreated_at(LocalDateTime.now());
    //         }
    //         plantationB.setCreated_at(plantationBlockDTO.getCreated_at());
    //         plantationB.setUpdated_at(plantationBlockDTO.getCreated_at());
    //         if (plantationBlockDTO.getIs_synced() == false) {
    //             plantationB.set_synced(true);
    //         }
    //         plantationB.set_synced(plantationBlockDTO.getIs_synced());
    //         plantationB.setZone(zoneService.findById(plantationBlockDTO.getId_zone()));
    //         PlantationBlock plantationBlock = plantationBlockRepository.save(plantationB);
    //         int nb_sub_plot = plantationBlock.getNb_sub_plot();
    //         for (int i = 0; i < nb_sub_plot; i++) {
    //             SubPlot subPlot = new SubPlot();
    //             subPlot.setName("Plct" + i+1);
    //             subPlot.setWidth(plantationBlock.getWidth()/plantationBlock.getNb_sub_plot());
    //             subPlot.setHeight(plantationBlock.getHeight()/2);
    //             subPlot.setLocation(null);
    //             subPlot.setPlantation_block(plantationBlock);

    //             subPlotServiceImpl.createSubPlot(subPlot);
    //         }
    //         return plantationBlock;
    //     } catch (DataIntegrityViolationException e) {
    //         throw new IllegalArgumentException("Sub plot already exist");
    //     }
    // }
}
