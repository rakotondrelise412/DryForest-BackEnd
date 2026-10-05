package org.ong.dryforest.service.plantationBlock;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.ong.dryforest.dto.plantationBlock.PlantationBlockDTO;
import org.ong.dryforest.entity.PlantationBlock;
import org.ong.dryforest.entity.Zone;
import org.ong.dryforest.repository.PlantationBlockRepository;
import org.ong.dryforest.service.geometryService.GeometryService;
import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlantationBlockServiceImpl
        implements PlantationBlockService {

    private final PlantationBlockRepository plantationBlockRepository;
    private final ZoneService zoneService;
    private final GeometryService geometryService;

    @Override
    public List<PlantationBlock> findAll() {
        return plantationBlockRepository
                .findAllByIsDeletedFalse();
    }

    @Override
    public PlantationBlock findById(int id) {
        return plantationBlockRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Placeau '" + id + "' introuvable"
                        )
                );
    }

    @Override
    public PlantationBlock findByUuid(UUID uuid) {
        return plantationBlockRepository
                .findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Placeau '" + uuid + "' introuvable"
                        )
                );
    }

    @Override
    public PlantationBlock createPlantationBlock(
            PlantationBlock plantationBlock) {

        try {
            if (plantationBlock.getUuid() == null) {
                plantationBlock.setUuid(
                        UUID.randomUUID()
                );
            }

            plantationBlock.setSynced(true);

            return plantationBlockRepository.save(
                    plantationBlock
            );

        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Placeau déjà existant",
                    e
            );
        }
    }

    @Override
    public PlantationBlock createPlantationBlock(
            PlantationBlockDTO dto) {

        PlantationBlock plantationBlock =
                new PlantationBlock();

        if (dto.getUuid() != null) {
            plantationBlock.setUuid(
                    dto.getUuid()
            );
        } else {
            plantationBlock.setUuid(
                    UUID.randomUUID()
            );
        }

        plantationBlock.setName(
                dto.getName()
        );

        plantationBlock.setWidth(
                dto.getWidth()
        );

        plantationBlock.setLength(
                dto.getLength()
        );

        plantationBlock.setNbSubPlot(
                dto.getNbSubPlot()
        );

        if (dto.getIdZone() > 0) {
            Zone zone =
                    zoneService.findById(
                            dto.getIdZone()
                    );

            plantationBlock.setZone(zone);
        }

        if (dto.getGeom() != null) {
            try {
                Geometry geometry =
                        geometryService.parseGeoJson(
                                dto.getGeom()
                        );

                if (!(geometry instanceof Polygon)) {
                    throw new IllegalArgumentException(
                            "La géométrie du placeau doit être un Polygon"
                    );
                }

                plantationBlock.setGeom(
                        (Polygon) geometry
                );

            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Géométrie du placeau invalide",
                        e
                );
            }
        }

        plantationBlock.setSynced(
                dto.isSynced()
        );

        if (dto.getCreatedAt() != null) {
            plantationBlock.setCreatedAt(
                    dto.getCreatedAt()
            );
        }

        if (dto.getUpdatedAt() != null) {
            plantationBlock.setUpdatedAt(
                    dto.getUpdatedAt()
            );
        }

        return createPlantationBlock(
                plantationBlock
        );
    }

    @Override
    public PlantationBlock updatePlantationBlock(
            PlantationBlock plantationBlock) {

        PlantationBlock existing =
                findById(
                        plantationBlock.getId()
                );

        if (plantationBlock.getUuid() == null) {
            plantationBlock.setUuid(
                    existing.getUuid()
            );
        }

        return plantationBlockRepository.save(
                plantationBlock
        );
    }

    @Override
    public PlantationBlock updatePlantationBlock(
            int id,
            PlantationBlockDTO dto) {

        PlantationBlock existing =
                findById(id);

        if (dto.getUuid() != null) {
            existing.setUuid(
                    dto.getUuid()
            );
        }

        existing.setName(
                dto.getName()
        );

        existing.setWidth(
                dto.getWidth()
        );

        existing.setLength(
                dto.getLength()
        );

        existing.setNbSubPlot(
                dto.getNbSubPlot()
        );

        if (dto.getGeom() != null) {
            try {
                Geometry geometry =
                        geometryService.parseGeoJson(
                                dto.getGeom()
                        );

                if (!(geometry instanceof Polygon)) {
                    throw new IllegalArgumentException(
                            "La géométrie du placeau doit être un Polygon"
                    );
                }

                existing.setGeom(
                        (Polygon) geometry
                );

            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Géométrie du placeau invalide",
                        e
                );
            }
        }

        if (dto.getIdZone() > 0) {
            Zone zone =
                    zoneService.findById(
                            dto.getIdZone()
                    );

            existing.setZone(zone);
        }

        existing.setSynced(
                dto.isSynced()
        );

        existing.setUpdatedAt(
                LocalDateTime.now()
        );

        return plantationBlockRepository.save(
                existing
        );
    }

    @Override
    public void deletePlantationBlock(
            PlantationBlock plantationBlock) {

        PlantationBlock existing =
                findById(
                        plantationBlock.getId()
                );

        existing.setDeleted(true);

        existing.setUpdatedAt(
                LocalDateTime.now()
        );

        plantationBlockRepository.save(
                existing
        );
    }

    @Override
    public void deletePlantationBlock(int id) {

        PlantationBlock existing =
                findById(id);

        existing.setDeleted(true);

        existing.setUpdatedAt(
                LocalDateTime.now()
        );

        plantationBlockRepository.save(
                existing
        );
    }

    @Override
    public boolean existsByUuid(UUID uuid) {
        return plantationBlockRepository
                .existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public PlantationBlock mapToEntity(
            Map<String, Object> data) {

        PlantationBlock plantationBlock =
                new PlantationBlock();

        Object uuid =
                data.get("uuid");

        if (uuid != null) {
            plantationBlock.setUuid(
                    UUID.fromString(
                            uuid.toString()
                    )
            );
        } else {
            plantationBlock.setUuid(
                    UUID.randomUUID()
            );
        }

        plantationBlock.setName(
                (String) data.get("name")
        );

        Object width =
                data.get("width");

        if (width != null) {
            plantationBlock.setWidth(
                    ((Number) width).doubleValue()
            );
        }

        Object length =
                data.get("length");

        if (length == null) {
            length = data.get("height");
        }

        if (length != null) {
            plantationBlock.setLength(
                    ((Number) length).doubleValue()
            );
        }

        Object nbSubPlot =
                data.get("nb_sub_plot");

        if (nbSubPlot != null) {
            plantationBlock.setNbSubPlot(
                    ((Number) nbSubPlot).intValue()
            );
        }

        Object geom =
                data.get("geom");

        if (geom instanceof Polygon) {
            plantationBlock.setGeom(
                    (Polygon) geom
            );
        }

        Object createdAt =
                data.get("created_at");

        if (createdAt != null) {
            plantationBlock.setCreatedAt(
                    LocalDateTime.parse(
                            createdAt.toString()
                    )
            );
        }

        Object updatedAt =
                data.get("updated_at");

        if (updatedAt != null) {
            plantationBlock.setUpdatedAt(
                    LocalDateTime.parse(
                            updatedAt.toString()
                    )
            );
        }

        Object synced =
                data.get("is_synced");

        if (synced != null) {
            plantationBlock.setSynced(
                    Boolean.parseBoolean(
                            synced.toString()
                    )
            );
        }

        Object idZone =
                data.get("id_zone");

        if (idZone != null) {
            plantationBlock.setZone(
                    zoneService.findById(
                            ((Number) idZone).intValue()
                    )
            );
        }

        return plantationBlock;
    }
}