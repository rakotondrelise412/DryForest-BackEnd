package org.ong.dryforest.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ong.dryforest.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Integer>{
    
    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<Zone> findByUuidAndIsDeletedFalse(UUID uuid);

    List<Zone> findAllByIsDeletedFalse();

    Optional<Zone> findByIdAndIsDeletedFalse(int id);

    @Query(value = "SELECT id_zone, name, area, ST_AsGeoJSON(geom) as geom_json, id_type_zone FROM zone",
        nativeQuery = true)
    List<Object[]> findAllWithGeomAsGeoJson();

}
