package org.ong.dryforest.service.observationPatrol;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.ong.dryforest.dto.CountByTypeDTO;
import org.ong.dryforest.dto.observationPatrol.ObservationPatrolDTO;
import org.ong.dryforest.entity.ObservationPatrol;
import org.ong.dryforest.repository.ObservationPatrolRepository;
import org.ong.dryforest.service.incidentPatrol.PatrolGroupService;
import org.ong.dryforest.service.user.UserService;
import org.ong.dryforest.service.zone.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ObservationPatrolServiceImpl implements ObservationPatrolService {

    @Autowired
    ObservationPatrolRepository observationPatrolRepository;
    @Autowired
    PatrolGroupService patrolGroupService;
    @Autowired
    ZoneService zoneService;
    @Autowired
    UserService userService;
    @Autowired
    TypeObservationPatrolService typeObservationPatrolService;

    @Override
    public List<ObservationPatrol> findAll(){
        return observationPatrolRepository.findAllByIsDeletedFalse();
    }

    @Override
    public ObservationPatrol findById(int id){
        return observationPatrolRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new RuntimeException("Observation introuvable par l'id : " + id));
    }

    @Override
    public ObservationPatrol findByUuid(UUID uuid){
        return observationPatrolRepository.findByUuidAndIsDeletedFalse(uuid).orElseThrow(() -> new RuntimeException("Observation introuvable par l'uuid : " + uuid));
    }

    @Override
    public ObservationPatrol createObservationPatrol(ObservationPatrol observationPatrol){
        observationPatrol.set_synced(true);
        return observationPatrolRepository.save(observationPatrol);
    }

    @Override
    public ObservationPatrol updateObservationPatrol(
            ObservationPatrol observationPatrol) {

        findById(observationPatrol.getId());

        return observationPatrolRepository.save(
                observationPatrol
        );
    }

    @Override
    public ObservationPatrol updateObservationPatrol(
            int id,
            ObservationPatrolDTO dto) {

        ObservationPatrol existing = findById(id);

        if (dto.getUuid() != null) {
            existing.setUuid(dto.getUuid());
        }

        if (dto.getDate_observation() != null) {
            existing.setDate_observation(
                    dto.getDate_observation()
            );
        }

        if (dto.getDescription() != null) {
            existing.setDescription(
                    dto.getDescription()
            );
        }

        if (dto.getId_type_observation_patrol() > 0) {
            existing.setTypeObservationPatrol(
                    typeObservationPatrolService.findById(
                            dto.getId_type_observation_patrol()
                    )
            );
        }

        if (dto.getId_patrol_group() > 0) {
            existing.setPatrolGroup(
                    patrolGroupService.findById(
                            dto.getId_patrol_group()
                    )
            );
        }

        if (dto.getId_zone() > 0) {
            existing.setZone(
                    zoneService.findById(
                            dto.getId_zone()
                    )
            );
        }

        if (dto.getId_user() > 0) {
            existing.setUsers(
                    userService.findUsersById(
                            dto.getId_user()
                    )
            );
        }
        if (dto.getLocation() != null) {

            Map<String, Object> location =
                    dto.getLocation();

            List<?> coordinates =
                    (List<?>) location.get("coordinates");

            if (coordinates != null && coordinates.size() >= 2) {

                double longitude =
                        ((Number) coordinates.get(0)).doubleValue();

                double latitude =
                        ((Number) coordinates.get(1)).doubleValue();

                GeometryFactory geometryFactory =
                        new GeometryFactory(
                                new PrecisionModel(),
                                4326
                        );

                Point point =
                        geometryFactory.createPoint(
                                new Coordinate(
                                        longitude,
                                        latitude
                                )
                        );

                existing.setLocation(point);
            }
        }
        existing.set_synced(dto.is_synced());
        existing.setUpdatedAt(LocalDateTime.now());

        return observationPatrolRepository.save(existing);
    }

    @Override
    public void deleteObservationPatrol(ObservationPatrol observationPatrol){
        try {
            findById(observationPatrol.getId());
            observationPatrolRepository.delete(observationPatrol);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cette observation pendant la patrouille.");
        }
    }

    @Override
    public boolean existsByUuid(UUID uuid){
        return observationPatrolRepository.existsByUuidAndIsDeletedFalse(uuid);
    }

    @Override
    public ObservationPatrol mapToEntity(Map<String, Object> observationPatrolMapping){
        ObservationPatrol observationPatrol = new ObservationPatrol();

        observationPatrol.setUuid(UUID.fromString((String)observationPatrolMapping.get("uuid")));
        observationPatrol.setDate_observation(LocalDateTime.parse((String)observationPatrolMapping.get("datetime_observation")));
        observationPatrol.setDescription((String)observationPatrolMapping.get("description"));
        observationPatrol.setCreatedAt(LocalDateTime.parse((String)observationPatrolMapping.get("created_at")));
        observationPatrol.setUpdatedAt(LocalDateTime.parse((String)observationPatrolMapping.get("updated_at")));
        observationPatrol.set_synced((boolean)observationPatrolMapping.get("is_synced"));
        observationPatrol.setTypeObservationPatrol(typeObservationPatrolService.findById(((Number)observationPatrolMapping.get("id_observation_patrol_type")).intValue()));
        observationPatrol.setPatrolGroup(patrolGroupService.findById(((Number)observationPatrolMapping.get("id_patrol_group")).intValue()));
        observationPatrol.setUsers(userService.findUsersById(((Number)observationPatrolMapping.get("id_user")).intValue()));
        observationPatrol.setZone(zoneService.findById(((Number)observationPatrolMapping.get("id_zone")).intValue()));

        return observationPatrol;
    }
    @Override
    public ObservationPatrol create(ObservationPatrolDTO observationPatrolDTO) {
        try {
            ObservationPatrol observationPatrol = new ObservationPatrol();

            if (observationPatrolDTO.getUuid() == null) {
                observationPatrolDTO.setUuid(UUID.randomUUID());
            }

            observationPatrol.setUuid(observationPatrolDTO.getUuid());

            if (observationPatrolDTO.getCreated_at() == null) {
                observationPatrolDTO.setCreated_at(LocalDateTime.now());
            }

            if (observationPatrolDTO.getUpdated_at() == null) {
                observationPatrolDTO.setUpdated_at(LocalDateTime.now());
            }

            observationPatrol.setCreatedAt(
                    observationPatrolDTO.getCreated_at()
            );

            observationPatrol.setUpdatedAt(
                    observationPatrolDTO.getUpdated_at()
            );

            observationPatrol.set_synced(
                    observationPatrolDTO.is_synced()
            );

            observationPatrol.setDate_observation(
                    observationPatrolDTO.getDate_observation()
            );



            // AJOUT
            observationPatrol.setDescription(
                    observationPatrolDTO.getDescription()
            );

            // Relations existantes
            observationPatrol.setPatrolGroup(
                    patrolGroupService.findById(
                            observationPatrolDTO.getId_patrol_group()
                    )
            );

            observationPatrol.setTypeObservationPatrol(
                    typeObservationPatrolService.findById(
                            observationPatrolDTO.getId_type_observation_patrol()
                    )
            );

            observationPatrol.setZone(
                    zoneService.findById(
                            observationPatrolDTO.getId_zone()
                    )
            );

            observationPatrol.setUsers(
                    userService.findUsersById(
                            observationPatrolDTO.getId_user()
                    )
            );

            // LOCATION
            if (observationPatrolDTO.getLocation() != null) {

                Map<String, Object> location =
                        observationPatrolDTO.getLocation();

                List<?> coordinates =
                        (List<?>) location.get("coordinates");

                if (coordinates != null && coordinates.size() >= 2) {

                    double longitude =
                            ((Number) coordinates.get(0)).doubleValue();

                    double latitude =
                            ((Number) coordinates.get(1)).doubleValue();

                    GeometryFactory geometryFactory =
                            new GeometryFactory(
                                    new PrecisionModel(),
                                    4326
                            );

                    Point point =
                            geometryFactory.createPoint(
                                    new Coordinate(
                                            longitude,
                                            latitude
                                    )
                            );

                    observationPatrol.setLocation(point);
                }
            }

            return observationPatrolRepository.save(observationPatrol);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Observation patrol already exist"
            );
        }
    }
    
    @Override
    public List<CountByTypeDTO> countByType() {
        return observationPatrolRepository.countByType();
    }
}
