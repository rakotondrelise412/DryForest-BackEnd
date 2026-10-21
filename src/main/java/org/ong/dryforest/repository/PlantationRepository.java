package org.ong.dryforest.repository;

import org.ong.dryforest.entity.Plantation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PlantationRepository
        extends JpaRepository<Plantation, Integer> {

    // ============================================================
    // PLANT NUMBER
    // ============================================================

    @Query("""
        SELECT p.plant_number
        FROM Plantation p
        ORDER BY p.plant_number DESC
        LIMIT 1
    """)
    String getLastPlantNumber();

    // ============================================================
    // STATUS PAR ANNEE
    // ============================================================

    @Query(
            value = """
            SELECT
                year,
                alive_count,
                dead_count,
                total_count
            FROM v_plantations_status_by_year
            ORDER BY year
            """,
            nativeQuery = true
    )
    List<Object[]> plantationStatusByYear();

    // ============================================================
    // RECHERCHE
    // ============================================================

    Optional<Plantation>
    findByUuidAndIsDeletedFalse(UUID uuid);

    boolean
    existsByUuidAndIsDeletedFalse(UUID uuid);

    List<Plantation>
    findAllByIsDeletedFalse();

    Page<Plantation> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Plantation>
    findByIdAndIsDeletedFalse(int id);

    // ============================================================
    // VUE PLANTATION
    // ============================================================

    @Query(
            value = """
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
            """,
            nativeQuery = true
    )
    List<Object[]> findAllPlantationsView();

    // ============================================================
    // CARBONE PAR ESPECE
    // ============================================================
    @Query(
            value = """
        SELECT
            p.id_species AS species_id,
            s.mg_name AS species_name,
            SUM(p.carbon_sequestered) AS total_carbon
        FROM plantation p
        LEFT JOIN species s
            ON p.id_species = s.id_species
        WHERE COALESCE(p.is_deleted, false) = false
        GROUP BY p.id_species, s.mg_name
        ORDER BY total_carbon DESC
        """,
            nativeQuery = true
    )
    List<Object[]> sumCarbonBySpeciesNative();

    // ---------------------Pagination de toutes les plantations web
    @Query(
            value = """
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
        ORDER BY plantation_created_at DESC NULLS LAST,
                 id_plantation DESC
        """,
            countQuery = """
        SELECT COUNT(*)
        FROM v_plantations_by_block_subplot
        """,
            nativeQuery = true
    )
    Page<Object[]> findAllPlantationsView(Pageable pageable);

    /// -------------Pagination avec les filtres

    @Query(
            value = """
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
        WHERE id_plantation_block =
              COALESCE(:idPlantationBlock, id_plantation_block)
          AND id_sub_plot =
              COALESCE(:idSubPlot, id_sub_plot)
          AND id_species =
              COALESCE(:idSpecies, id_species)
          AND date_plantation =
              COALESCE(:datePlantation, date_plantation)
        ORDER BY plantation_created_at DESC NULLS LAST,
                 id_plantation DESC
        """,
            countQuery = """
        SELECT COUNT(*)
        FROM v_plantations_by_block_subplot
        WHERE id_plantation_block =
              COALESCE(:idPlantationBlock, id_plantation_block)
          AND id_sub_plot =
              COALESCE(:idSubPlot, id_sub_plot)
          AND id_species =
              COALESCE(:idSpecies, id_species)
          AND date_plantation =
              COALESCE(:datePlantation, date_plantation)
        """,
            nativeQuery = true
    )
    Page<Object[]> searchPlantationsByCriteria(
            @Param("idPlantationBlock") Integer idPlantationBlock,
            @Param("idSubPlot") Integer idSubPlot,
            @Param("idSpecies") Integer idSpecies,
            @Param("datePlantation") Date datePlantation,
            Pageable pageable
    );

    //-------------Pagination par bloc

    @Query(
            value = """
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
        ORDER BY plantation_created_at DESC NULLS LAST,
                 id_plantation DESC
        """,
            countQuery = """
        SELECT COUNT(*)
        FROM v_plantations_by_block_subplot
        WHERE id_plantation_block = :blockId
        """,
            nativeQuery = true
    )
    Page<Object[]> findPlantationsByBlockId(
            @Param("blockId") int blockId,
            Pageable pageable
    );

    //----------------Pagination par bloc et sous-parcelle

    @Query(
            value = """
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
          AND id_sub_plot = :subPlotId
        ORDER BY plantation_created_at DESC NULLS LAST,
                 id_plantation DESC
        """,
            countQuery = """
        SELECT COUNT(*)
        FROM v_plantations_by_block_subplot
        WHERE id_plantation_block = :blockId
          AND id_sub_plot = :subPlotId
        """,
            nativeQuery = true
    )
    Page<Object[]> findPlantationsByBlockAndSubPlot(
            @Param("blockId") int blockId,
            @Param("subPlotId") int subPlotId,
            Pageable pageable
    );


}