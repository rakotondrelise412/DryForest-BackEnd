package org.ong.dryforest.service.subPlot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.ong.dryforest.dto.subPlot.SubPlotDTO;
import org.ong.dryforest.entity.SubPlot;
import org.ong.dryforest.repository.SubPlotRepository;
import org.ong.dryforest.service.geometryService.GeometryService;
import org.ong.dryforest.service.plantationBlock.PlantationBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SubPlotServiceImpl implements SubPlotService{
    @Autowired
    SubPlotRepository subPlotRepository;

    @Autowired
    GeometryService geometryService;

    @Autowired
    PlantationBlockService plantationBlockService;

    @Override
    public List<SubPlot> findAll(){ return subPlotRepository.findAllByIsDeletedFalse(); }

    @Override
    public SubPlot findById(int id_subPlot){
        return subPlotRepository.findByIdAndIsDeletedFalse(id_subPlot).orElseThrow(() -> new RuntimeException("Sub plot not found"));
    }

    @Override
    public SubPlot findByUuid(UUID uuid){
        return subPlotRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Sub plot not found"));
    }

    @Override
    public SubPlot createSubPlot(SubPlot subPlot){
        try {
            if (subPlot.getUuid() == null) subPlot.setUuid(UUID.randomUUID());
            if (subPlot.getCreatedAt() == null) subPlot.setCreatedAt(LocalDateTime.now());
            subPlot.setUpdatedAt(LocalDateTime.now());
            return subPlotRepository.save(subPlot);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Sub plot already exist");
        }
    }

    @Override
    public SubPlot updateSubPlot(SubPlot subPlot){
        findById(subPlot.getId());
        return subPlotRepository.save(subPlot);
    }

    @Override
    public void deleteSubPlot(SubPlot subPlot){
        try {
            findById(subPlot.getId());
            subPlotRepository.delete(subPlot);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cette placette");
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid){
        return subPlotRepository.existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public SubPlot mapToEntity(Map<String, Object> subPlotMapping){
        SubPlot subPlot = new SubPlot();

        subPlot.setUuid(UUID.fromString((String)subPlotMapping.get("uuid")));
        subPlot.setName((String)subPlotMapping.get("name"));
        subPlot.setWidth(((Number)subPlotMapping.get("width")).doubleValue());
        subPlot.setLength(((Number)subPlotMapping.get("height")).doubleValue());
        subPlot.setLocation((Point)subPlotMapping.get("location"));
        subPlot.setCreatedAt(LocalDateTime.parse((String)subPlotMapping.get("created_at")));
        subPlot.setUpdatedAt(LocalDateTime.parse((String)subPlotMapping.get("updated_at")));
        subPlot.set_synced((boolean)subPlotMapping.get("is_synced"));
        subPlot.setPlantation_block(plantationBlockService.findById(((Number)subPlotMapping.get("id_plantation_block")).intValue()));

        return subPlot;
    }


    @Override
    public SubPlot updateSubPlotLocation(SubPlotDTO subPlotDTO) throws Exception {
        SubPlot existing = subPlotRepository.findById(subPlotDTO.getId_sub_plot())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SubPlot not found"));

        if (subPlotDTO.getName() != null && !subPlotDTO.getName().equals(existing.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le nom de la placette ne peut pas être modifié");
        }
        if (subPlotDTO.getWidth() != existing.getWidth()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La largeur de la placette ne peut pas être modifiée");
        }
        if (subPlotDTO.getHeight() != existing.getLength()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La longueur/hauteur de la placette ne peut pas être modifiée");
        }

        if (subPlotDTO.getPlantation_block_id() != existing.getPlantation_block().getId() || existing.getPlantation_block() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le placeau fourni ne correspond pas à la placette sélectionnée");
        }

        Geometry geom = geometryService.parseGeoJson(subPlotDTO.getLocation());
        
        existing.setLocation((Point)geom);
        return subPlotRepository.save(existing);
    }
}
