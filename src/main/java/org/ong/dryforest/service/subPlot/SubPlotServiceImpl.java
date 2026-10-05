package org.ong.dryforest.service.subPlot;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.ong.dryforest.dto.subPlot.SubPlotDTO;
import org.ong.dryforest.entity.PlantationBlock;
import org.ong.dryforest.entity.SubPlot;
import org.ong.dryforest.repository.SubPlotRepository;
import org.ong.dryforest.service.geometryService.GeometryService;
import org.ong.dryforest.service.plantationBlock.PlantationBlockService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubPlotServiceImpl implements SubPlotService {

    private final SubPlotRepository subPlotRepository;
    private final GeometryService geometryService;
    private final PlantationBlockService plantationBlockService;

    @Override
    public List<SubPlot> findAll() {
        return subPlotRepository.findAllByIsDeletedFalse();
    }

    @Override
    public SubPlot findById(int id) {
        return subPlotRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sub plot '" + id + "' introuvable"
                        )
                );
    }

    @Override
    public SubPlot findByUuid(UUID uuid) {
        return subPlotRepository
                .findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sub plot '" + uuid + "' introuvable"
                        )
                );
    }

    @Override
    public SubPlot createSubPlot(SubPlot subPlot) {

        try {
            if (subPlot.getUuid() == null) {
                subPlot.setUuid(UUID.randomUUID());
            }

            if (subPlot.getCreatedAt() == null) {
                subPlot.setCreatedAt(LocalDateTime.now());
            }

            subPlot.setUpdatedAt(LocalDateTime.now());

            return subPlotRepository.save(subPlot);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Sub plot déjà existant",
                    e
            );
        }
    }

    @Override
    public SubPlot createSubPlot(SubPlotDTO dto) {

        SubPlot subPlot = new SubPlot();

        if (dto.getUuid() != null) {
            subPlot.setUuid(dto.getUuid());
        } else {
            subPlot.setUuid(UUID.randomUUID());
        }

        subPlot.setName(dto.getName());
        subPlot.setWidth(dto.getWidth());
        subPlot.setLength(dto.getHeight());

        if (dto.getPlantationBlockId() > 0) {

            PlantationBlock plantationBlock =
                    plantationBlockService.findById(
                            dto.getPlantationBlockId()
                    );

            subPlot.setPlantationBlock(plantationBlock);
        }

        if (dto.getLocation() != null) {

            try {
                Geometry geometry =
                        geometryService.parseGeoJson(
                                dto.getLocation()
                        );

                if (!(geometry instanceof Point)) {
                    throw new IllegalArgumentException(
                            "La géométrie de la placette doit être un Point"
                    );
                }

                subPlot.setLocation((Point) geometry);

            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Localisation de la placette invalide",
                        e
                );
            }
        }

        if (dto.getCreatedAt() != null) {
            subPlot.setCreatedAt(dto.getCreatedAt());
        }

        if (dto.getUpdatedAt() != null) {
            subPlot.setUpdatedAt(dto.getUpdatedAt());
        }
        subPlot.setSynced(dto.isSynced());

        return createSubPlot(subPlot);
    }

    @Override
    public SubPlot updateSubPlot(SubPlot subPlot) {

        SubPlot existing = findById(subPlot.getId());

        if (subPlot.getUuid() == null) {
            subPlot.setUuid(existing.getUuid());
        }

        return subPlotRepository.save(subPlot);
    }

    @Override
    public SubPlot updateSubPlot(
            int id,
            SubPlotDTO dto) {

        SubPlot existing = findById(id);

        if (dto.getUuid() != null) {
            existing.setUuid(dto.getUuid());
        }

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }

        existing.setWidth(dto.getWidth());
        existing.setLength(dto.getHeight());

        if (dto.getPlantationBlockId() > 0) {

            PlantationBlock plantationBlock =
                    plantationBlockService.findById(
                            dto.getPlantationBlockId()
                    );

            existing.setPlantationBlock(plantationBlock);
        }
        if (dto.getLocation() != null) {

            try {
                Geometry geometry =
                        geometryService.parseGeoJson(
                                dto.getLocation()
                        );

                if (!(geometry instanceof Point)) {
                    throw new IllegalArgumentException(
                            "La géométrie de la placette doit être un Point"
                    );
                }

                existing.setLocation((Point) geometry);

            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Localisation de la placette invalide",
                        e
                );
            }
        }
        existing.setSynced(dto.isSynced());
        existing.setUpdatedAt(LocalDateTime.now());

        return subPlotRepository.save(existing);
    }

    @Override
    public SubPlot updateSubPlotLocation(
            SubPlotDTO subPlotDTO)
            throws Exception {

        SubPlot existing =
                subPlotRepository
                        .findById(
                                subPlotDTO.getIdSubPlot()
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "SubPlot not found"
                                )
                        );

        if (subPlotDTO.getName() != null
                && !subPlotDTO.getName()
                .equals(existing.getName())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Le nom de la placette ne peut pas être modifié"
            );
        }
        if (Double.compare(
                subPlotDTO.getWidth(),
                existing.getWidth()
        ) != 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La largeur de la placette ne peut pas être modifiée"
            );
        }

        if (Double.compare(
                subPlotDTO.getHeight(),
                existing.getLength()
        ) != 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La longueur/hauteur de la placette ne peut pas être modifiée"
            );
        }

        if (existing.getPlantationBlock() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La placette n'est associée à aucun placeau"
            );
        }

        if (subPlotDTO.getPlantationBlockId()
                != existing.getPlantationBlock().getId()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Le placeau fourni ne correspond pas à la placette sélectionnée"
            );
        }
        try {

            Geometry geometry =
                    geometryService.parseGeoJson(
                            subPlotDTO.getLocation()
                    );

            if (!(geometry instanceof Point)) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La localisation doit être un Point"
                );
            }

            existing.setLocation((Point) geometry);

        } catch (ResponseStatusException e) {

            throw e;

        } catch (Exception e) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Localisation de la placette invalide",
                    e
            );
        }

        existing.setUpdatedAt(LocalDateTime.now());

        return subPlotRepository.save(existing);
    }

    @Override
    public void deleteSubPlot(SubPlot subPlot) {

        SubPlot existing = findById(subPlot.getId());

        existing.setDeleted(true);
        existing.setUpdatedAt(LocalDateTime.now());

        subPlotRepository.save(existing);
    }

    @Override
    public void deleteSubPlot(int id) {

        SubPlot existing = findById(id);

        deleteSubPlot(existing);
    }

    @Override
    public boolean existsByUuid(UUID uuid) {

        return subPlotRepository
                .existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public SubPlot mapToEntity(
            Map<String, Object> subPlotMapping) {

        SubPlot subPlot = new SubPlot();

        Object uuid =
                subPlotMapping.get("uuid");

        if (uuid != null) {

            subPlot.setUuid(
                    UUID.fromString(uuid.toString())
            );

        } else {

            subPlot.setUuid(
                    UUID.randomUUID()
            );
        }

        subPlot.setName(
                (String) subPlotMapping.get("name")
        );

        Object width =
                subPlotMapping.get("width");

        if (width != null) {

            subPlot.setWidth(
                    ((Number) width).doubleValue()
            );
        }

        Object length =
                subPlotMapping.get("height");

        if (length == null) {
            length = subPlotMapping.get("length");
        }

        if (length != null) {

            subPlot.setLength(
                    ((Number) length).doubleValue()
            );
        }

        Object location =
                subPlotMapping.get("location");

        if (location instanceof Point) {

            subPlot.setLocation((Point) location);
        }

        Object createdAt =
                subPlotMapping.get("created_at");

        if (createdAt != null) {

            subPlot.setCreatedAt(
                    LocalDateTime.parse(
                            createdAt.toString()
                    )
            );
        }

        Object updatedAt =
                subPlotMapping.get("updated_at");

        if (updatedAt != null) {

            subPlot.setUpdatedAt(
                    LocalDateTime.parse(
                            updatedAt.toString()
                    )
            );
        }

        Object synced =
                subPlotMapping.get("is_synced");

        if (synced != null) {

            subPlot.setSynced(
                    Boolean.parseBoolean(
                            synced.toString()
                    )
            );
        }

        Object plantationBlockId =
                subPlotMapping.get(
                        "id_plantation_block"
                );

        if (plantationBlockId != null) {

            subPlot.setPlantationBlock(
                    plantationBlockService.findById(
                            ((Number) plantationBlockId)
                                    .intValue()
                    )
            );
        }

        return subPlot;
    }
}