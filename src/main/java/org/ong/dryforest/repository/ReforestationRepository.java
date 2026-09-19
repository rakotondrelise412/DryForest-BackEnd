package org.ong.dryforest.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.reforestation.ReforestationCountingDTO;
import org.ong.dryforest.entity.Reforestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReforestationRepository extends JpaRepository<Reforestation, Integer>{

    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    Optional<Reforestation> findByUuidAndIsDeletedFalse(UUID uuid);

    List<Reforestation> findAllByIsDeletedFalse();

    Optional<Reforestation> findByIdAndIsDeletedFalse(int id);

    @Query(value = "SELECT total_plants FROM v_total_plants_reforestation", nativeQuery = true)
    Integer getTotalPlanted();
    
    Reforestation findTopByOrderByIdDesc();

    @Query(value = "SELECT id_type_zone, type_zone_name, total_quantity FROM v_ref_quantity_by_type_zone", nativeQuery = true)
    List<Object[]> findRefQuantityByTypeZone();

    default List<ReforestationCountingDTO> getRefQuantityByTypeZone(){
        return findRefQuantityByTypeZone().stream().map(row -> {
            Integer id_type_zone = null;
            if (row[0] instanceof Number) {
                id_type_zone = ((Number) row[0]).intValue();
            } else if (row[0] != null) {
                try { id_type_zone = Integer.valueOf(row[0].toString()); } catch (Exception ignored) {}
            }

            String type_zone_name = row[1] != null ? row[1].toString() : null;

            Integer totalQuantity = null;
            if (row[2] instanceof Number) {
                totalQuantity = ((Number) row[2]).intValue();
            } else if (row[2] != null) {
                try { totalQuantity = Integer.valueOf(row[2].toString()); } catch (Exception ignored) {}
            }

            return new ReforestationCountingDTO(id_type_zone, type_zone_name, totalQuantity);
        }).collect(Collectors.toList());
    }
}
