package org.ong.dryforest.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ong.dryforest.entity.Plantation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantationRepository extends JpaRepository<Plantation, Integer> {
    @Query("""
            SELECT p.plant_number FROM Plantation p ORDER BY p.plant_number DESC limit 1
            """)
    String getLastPlantNumber();

    @Query(
      value = "SELECT year, alive_count, dead_count, total_count FROM v_plantations_status_by_year ORDER BY year",
      nativeQuery = true
    )
    List<Object[]> plantationStatusByYear();

    Optional<Plantation> findByUuidAndIsDeletedFalse(UUID uuid);

    boolean existsByUuidAndIsDeletedFalse(UUID uuid);

    List<Plantation> findAllByIsDeletedFalse();

    Optional<Plantation> findByIdAndIsDeletedFalse(int id);

    @Query(value = """
        SELECT
          id_plantation,
          plantation_uuid,
          diameter,
          height,
          carbon_sequestered,
          image,
          date_plantation,
          plant_number,
          plantation_status,
          plantation_created_at,
          plantation_updated_at,
          plantation_is_synced,
          plantation_is_deleted,
          id_species,
          species_name,
          id_sub_plot,
          id_reforestation,
          sub_plot_name,
          plantation_block_name,
          id_plantation_block
        FROM v_plantations_by_block_subplot
        """, nativeQuery = true)
    List<Object[]> findAllPlantationsView();

    @Query(value = """
        SELECT
          id_plantation,
          plantation_uuid,
          diameter,
          height,
          carbon_sequestered,
          image,
          date_plantation,
          plant_number,
          plantation_status,
          plantation_created_at,
          plantation_updated_at,
          plantation_is_synced,
          plantation_is_deleted,
          id_species,
          species_name,
          id_sub_plot,
          id_reforestation,
          sub_plot_name,
          plantation_block_name,
          id_plantation_block
        FROM v_plantations_by_block_subplot
        WHERE id_plantation_block = COALESCE(:idPlantationBlock, id_plantation_block)
          AND id_sub_plot        = COALESCE(:idSubPlot, id_sub_plot)
          AND id_species         = COALESCE(:idSpecies, id_species)
          AND date_plantation    = COALESCE(:datePlantation, date_plantation)
        """, nativeQuery = true)
    List<Object[]> searchPlantationsByCriteria(
        @Param("idPlantationBlock") Integer idPlantationBlock,
        @Param("idSubPlot") Integer idSubPlot,
        @Param("idSpecies") Integer idSpecies,
        @Param("datePlantation") Date datePlantation
    );

    // inutilisable pour le moment
    @Query(value = """
        SELECT
          id_plantation,
          plantation_uuid,
          diameter,
          height,
          carbon_sequestered,
          image,
          date_plantation,
          plant_number,
          plantation_status,
          plantation_created_at,
          plantation_updated_at,
          plantation_is_synced,
          plantation_is_deleted,
          id_species,
          species_name,
          id_sub_plot,
          id_reforestation,
          sub_plot_name,
          plantation_block_name,
          id_plantation_block
        FROM v_plantations_by_block_subplot
        WHERE id_plantation_block = :blockId
        """, nativeQuery = true)
    List<Object[]> findPlantationsByBlockId(@Param("blockId") int blockId);

    @Query(value = """
        SELECT
          id_plantation,
          plantation_uuid,
          diameter,
          height,
          carbon_sequestered,
          image,
          date_plantation,
          plant_number,
          plantation_status,
          plantation_created_at,
          plantation_updated_at,
          plantation_is_synced,
          plantation_is_deleted,
          id_species,
          species_name,
          id_sub_plot,
          id_reforestation,
          sub_plot_name,
          plantation_block_name,
          id_plantation_block
        FROM v_plantations_by_block_subplot
        WHERE id_plantation_block = :blockId AND id_sub_plot = :subPlotId
        """, nativeQuery = true)
    List<Object[]> findPlantationsByBlockAndSubPlot(@Param("blockId") int blockId, @Param("subPlotId") int subPlotId);

    @Query(
      value = "SELECT p.id_species AS species_id, s.mg_name AS species_name, SUM(p.carbon_sequestered) AS total_carbon " +
              "FROM plantation p " +
              "LEFT JOIN species s ON p.id_species = s.id_species " +
              "WHERE COALESCE(p.is_deleted, false) = false " +
              "GROUP BY p.id_species, s.mg_name " +
              "ORDER BY total_carbon DESC",
      nativeQuery = true
    )
    List<Object[]> sumCarbonBySpeciesNative();
}
