package org.ong.dryforest.service.plantingMonitoring;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.PlantingMonitoringDTO;
import org.ong.dryforest.dto.species.SpeciesStatDTO;
import org.ong.dryforest.entity.Plantation;
import org.ong.dryforest.entity.PlantingMonitoring;
import org.ong.dryforest.repository.PlantingMonitoringRepository;
import org.ong.dryforest.service.plantation.PlantationService;
import org.ong.dryforest.service.plantation.PlantationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PlantingMonitoringServiceImpl implements PlantingMonitoringService{

    @Autowired
    PlantationService plantationService;
    @Autowired
    PlantingMonitoringRepository plantingMonitoringRepository;

    @Override
    public PlantingMonitoring createPlantingMonitoring(PlantingMonitoring plantingMonitoring){
        try {
            plantingMonitoring.set_synced(true);
            return plantingMonitoringRepository.save(plantingMonitoring);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Suivi plantation déjà existant");
        }
    }

    @Override
    public PlantingMonitoring updatePlantingMonitoring(PlantingMonitoring plantingMonitoring){
        findById(plantingMonitoring.getId());
        return plantingMonitoringRepository.save(plantingMonitoring);
    }

    @Override
    public void deletePlantingMonitoring(PlantingMonitoring plantingMonitoring){
        try {
            findById(plantingMonitoring.getId());
            plantingMonitoringRepository.delete(plantingMonitoring);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer ce placeau");
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid){
        return plantingMonitoringRepository.existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public PlantingMonitoring mapToEntity(Map<String, Object> plantingMonitoringMapping){
        PlantingMonitoring plantingMonitoring = new PlantingMonitoring();

        plantingMonitoring.setUuid(UUID.fromString((String)plantingMonitoringMapping.get("uuid")));
        plantingMonitoring.setDiameter(((Number)plantingMonitoringMapping.get("diameter")).doubleValue());
        plantingMonitoring.setHeight(((Number)plantingMonitoringMapping.get("height")).doubleValue());
        plantingMonitoring.setCarbon_sequestered(((Number)plantingMonitoringMapping.get("carbon_sequestered")).doubleValue());
        plantingMonitoring.setImage((String)plantingMonitoringMapping.get("image"));
        plantingMonitoring.setDate_planting_monitoring(LocalDate.parse((String)plantingMonitoringMapping.get("date_planting_monitoring")));
        plantingMonitoring.setAuto_generation(((Number)plantingMonitoringMapping.get("auto_generation")).intValue());
        plantingMonitoring.setCreatedAt(LocalDateTime.parse((String)plantingMonitoringMapping.get("created_at")));
        plantingMonitoring.setUpdatedAt(LocalDateTime.parse((String)plantingMonitoringMapping.get("updated_at")));
        plantingMonitoring.set_synced((boolean)plantingMonitoringMapping.get("is_synced"));
        plantingMonitoring.setPlantation(plantationService.findById(((Number)plantingMonitoringMapping.get("id_plantation")).intValue()));

        return plantingMonitoring;
    }

    @Override
    public List<PlantingMonitoring> findAll(){
        return plantingMonitoringRepository.findAllByIsDeletedFalse();
    }
    
    @Override
    public PlantingMonitoring findById(int id){
        return plantingMonitoringRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new RuntimeException("Suivi plantation introuvable avec id: " + id));
    }

    @Override
    public PlantingMonitoring findByUuid(UUID uuid){
        return plantingMonitoringRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Suivi plantation introuvable avec uuid : " + uuid));
    }


    @Override
    public PlantingMonitoring create(PlantingMonitoringDTO plantingMonitoringDTO) {
        
        try {
            PlantingMonitoring plantingMonitoring = new PlantingMonitoring();
            if (plantingMonitoringDTO.getUuid() == null) plantingMonitoringDTO.setUuid(UUID.randomUUID());
            plantingMonitoring.setUuid(plantingMonitoringDTO.getUuid());
            if (plantingMonitoringDTO.getCreatedAt() == null) plantingMonitoringDTO.setCreatedAt(LocalDateTime.now());
            plantingMonitoringDTO.setUpdatedAt(LocalDateTime.now());
            plantingMonitoring.setCreatedAt(plantingMonitoringDTO.getCreatedAt());
            plantingMonitoring.setUpdatedAt(plantingMonitoringDTO.getUpDateAt());

            if (plantingMonitoringDTO.getIsSynced() == false) {
                plantingMonitoringDTO.setIsSynced(true);
            }
            plantingMonitoring.set_synced(plantingMonitoringDTO.getIsSynced());

            plantingMonitoring.setDate_planting_monitoring(plantingMonitoringDTO.getDate_planting_monitoring());
            plantingMonitoring.setDiameter(plantingMonitoringDTO.getDiameter());
            plantingMonitoring.setHeight(plantingMonitoringDTO.getHeight());
            plantingMonitoring.setImage(plantingMonitoringDTO.getImage());
            plantingMonitoring.setAuto_generation(plantingMonitoringDTO.getAuto_generation());
            Plantation plantation = plantationService.findById(plantingMonitoringDTO.getId_plantation());
            double biomasse = PlantationServiceImpl.calculateDryAGB(plantingMonitoringDTO.getDiameter(), plantingMonitoringDTO.getHeight(), plantation.getSpecies().getDensity());
            plantingMonitoring.setCarbon_sequestered(PlantationServiceImpl.calculateCarbon(biomasse));

            return plantingMonitoring;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Plantation already exist");
        }
    }

    @Override
    public List<SpeciesStatDTO> statisticSpecies(){
        List<Plantation> plantations = plantationService.findAll();
        List<PlantingMonitoring> plantingMonitoring = this.findAll();
        List<SpeciesStatDTO> speciesStatDTOs = new ArrayList<>();

        if (plantations == null || plantingMonitoring == null || plantations.isEmpty() || plantingMonitoring.isEmpty()) {
            return speciesStatDTOs;
        }

            for (PlantingMonitoring plMonitoring : plantingMonitoring) {
                if (plMonitoring.getPlantation() == null) {
                    continue;
                }
                Plantation plantation = plMonitoring.getPlantation();

                if (plantation.getSpecies() == null) {
                    continue;
                }
                SpeciesStatDTO speciesStatDTO = new SpeciesStatDTO();

                double diameter = plMonitoring.getDiameter() - plantation.getDiameter();
                double height = plMonitoring.getHeight() - plantation.getHeight();
                double carbon_sequestered = plMonitoring.getCarbon_sequestered() - plantation.getCarbon_sequestered();
                speciesStatDTO.setId_species(plantation.getSpecies().getId());
                speciesStatDTO.setScientific_name(plantation.getSpecies().getScientific_name());
                speciesStatDTO.setMg_name(plantation.getSpecies().getMg_name());
                speciesStatDTO.setEn_name(plantation.getSpecies().getEn_name());
                speciesStatDTO.setFr_name(plantation.getSpecies().getFr_name());
                speciesStatDTO.setDiameter(diameter);
                speciesStatDTO.setHeight(height);
                speciesStatDTO.setCarbon_sequestered(carbon_sequestered);
                if (plantation.getReforestation() != null) {
                    speciesStatDTO.setDate_reforestation(plantation.getReforestation().getDate_reforestation());
                }
                speciesStatDTO.setId_plantation(plantation.getId());

                speciesStatDTOs.add(speciesStatDTO);

            }
        return speciesStatDTOs;
    }

    @Override
    public List<SpeciesStatDTO> statisticSpeciesAverageBySpeciesAndReforestationDate() {
        List<SpeciesStatDTO> stats = this.statisticSpecies();
        List<SpeciesStatDTO> result = new ArrayList<>();

        if (stats == null || stats.isEmpty()) {
            return result;
        }

        Map<String, List<SpeciesStatDTO>> grouped = stats.stream()
                .collect(Collectors.groupingBy(dto ->
                        dto.getId_species() + "_" +
                        (dto.getDate_reforestation() != null ? dto.getDate_reforestation().toString() : "null")
                ));

        for (List<SpeciesStatDTO> group : grouped.values()) {
            SpeciesStatDTO first = group.get(0);

            double avgDiameter = group.stream()
                    .mapToDouble(SpeciesStatDTO::getDiameter)
                    .average()
                    .orElse(0.0);

            double avgHeight = group.stream()
                    .mapToDouble(SpeciesStatDTO::getHeight)
                    .average()
                    .orElse(0.0);

            double avgCarbon = group.stream()
                    .mapToDouble(SpeciesStatDTO::getCarbon_sequestered)
                    .average()
                    .orElse(0.0);

            SpeciesStatDTO dto = new SpeciesStatDTO();
            dto.setId_species(first.getId_species());
            dto.setScientific_name(first.getScientific_name());
            dto.setMg_name(first.getMg_name());
            dto.setEn_name(first.getEn_name());
            dto.setFr_name(first.getFr_name());
            dto.setDate_reforestation(first.getDate_reforestation());

            dto.setDiameter(avgDiameter);
            dto.setHeight(avgHeight);
            dto.setCarbon_sequestered(avgCarbon);

            result.add(dto);
        }

        return result;
    }

    @Override
    public List<SpeciesStatDTO> statisticSpeciesAverageByReforestationDateRange(LocalDate startDate, LocalDate endDate) {
        List<SpeciesStatDTO> stats = this.statisticSpecies();
        List<SpeciesStatDTO> result = new ArrayList<>();

        if (stats == null || stats.isEmpty() || startDate == null || endDate == null) {
            return result;
        }

        Map<String, List<SpeciesStatDTO>> grouped = stats.stream()
                .filter(dto -> dto.getDate_reforestation() != null)
                .filter(dto -> {
                    LocalDate d = dto.getDate_reforestation();
                    return (d.isEqual(startDate) || d.isAfter(startDate))
                            && (d.isEqual(endDate) || d.isBefore(endDate));
                })
                .collect(Collectors.groupingBy(dto ->
                        dto.getId_species() + "_" + dto.getDate_reforestation()
                ));

        for (List<SpeciesStatDTO> group : grouped.values()) {
            SpeciesStatDTO first = group.get(0);

            double avgDiameter = group.stream()
                    .mapToDouble(SpeciesStatDTO::getDiameter)
                    .average()
                    .orElse(0.0);

            double avgHeight = group.stream()
                    .mapToDouble(SpeciesStatDTO::getHeight)
                    .average()
                    .orElse(0.0);

            double avgCarbon = group.stream()
                    .mapToDouble(SpeciesStatDTO::getCarbon_sequestered)
                    .average()
                    .orElse(0.0);

            SpeciesStatDTO dto = new SpeciesStatDTO();
            dto.setId_species(first.getId_species());
            dto.setScientific_name(first.getScientific_name());
            dto.setMg_name(first.getMg_name());
            dto.setEn_name(first.getEn_name());
            dto.setFr_name(first.getFr_name());
            dto.setDate_reforestation(first.getDate_reforestation());

            dto.setDiameter(avgDiameter);
            dto.setHeight(avgHeight);
            dto.setCarbon_sequestered(avgCarbon);

            result.add(dto);
        }

        return result;
    }
}
