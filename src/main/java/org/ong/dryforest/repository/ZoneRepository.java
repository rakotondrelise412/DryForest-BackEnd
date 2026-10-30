package org.ong.dryforest.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ong.dryforest.entity.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Integer> {

    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<Zone> findByUuidAndIsDeletedFalse(UUID uuid);

    List<Zone> findAllByIsDeletedFalse();

    Page<Zone> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Zone> findByIdAndIsDeletedFalse(int id);


    @Query(value = """
    SELECT id_zone, uuid, name, area,
           ST_AsGeoJSON(geom) AS geom_json,
           id_type_zone, is_synced, id_site
    FROM zone
    WHERE is_deleted = false
    ORDER BY created_at DESC
    """,
            countQuery = """
    SELECT COUNT(*)
    FROM zone
    WHERE is_deleted = false
    """,
            nativeQuery = true)
    Page<Object[]> findAllWithGeomAsGeoJson(Pageable pageable);

}