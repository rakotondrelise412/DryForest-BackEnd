--cmd
-- psql -U postgres
-- DROP DATABASE IF EXISTS dryforest;
-- CREATE DATABASE dryforest;
-- \c dryforest
-- CREATE EXTENSION IF NOT EXISTS postgis;
-- SELECT PostGIS_Version();


-- Suivi des plantes plantés:
BEGIN@@

-- Nombre total de plantes plantées (somme de quantity dans reforestation)
CREATE OR REPLACE VIEW v_total_plants_reforestation AS
SELECT COALESCE(SUM(quantity),0)::bigint AS total_plants
FROM reforestation@@

-- NOT YET USED
-- Nombre total de plantation pour chaque placette (sub_plot)
--    Ici on retourne aussi le nombre de plantations "vivantes" (status = true) pour info.
CREATE OR REPLACE VIEW v_plantation_per_sub_plot AS
SELECT sp.id_sub_plot,
       sp.uuid,
       sp.name AS sub_plot_name,
       sp.id_plantation_block,
       COALESCE(cnt.total_plantations,0) AS total_plantations,
       COALESCE(cnt.alive_plantations,0) AS alive_plantations
FROM sub_plot sp
         LEFT JOIN (
    SELECT id_sub_plot,
           COUNT(*) AS total_plantations,
           SUM(CASE WHEN status THEN 1 ELSE 0 END) AS alive_plantations
    FROM plantation
    GROUP BY id_sub_plot
) cnt ON cnt.id_sub_plot = sp.id_sub_plot@@


-- plantations dans chaques placettes pour chaques placeaux
    CREATE OR REPLACE VIEW v_plantations_by_block_subplot AS
SELECT
    p.id_plantation,
    p.uuid                  AS plantation_uuid,
    p.diameter,
    p.height,
    p.carbon_sequestered,
    p.image,
    p.date_plantation,
    p.plant_number,
    p.status                AS plantation_status,
    p.created_at            AS plantation_created_at,
    p.updated_at            AS plantation_updated_at,
    p.is_synced             AS plantation_is_synced,
    p.is_deleted            AS plantation_is_deleted,
    p.id_species,
    s.mg_name               AS species_name,
    p.id_sub_plot,
    p.id_reforestation,
    sp.name                 AS sub_plot_name,
    pb.name                 AS plantation_block_name,
    pb.id_plantation_block  AS id_plantation_block
FROM plantation p
         LEFT JOIN species s ON p.id_species = s.id_species
         JOIN sub_plot sp ON p.id_sub_plot = sp.id_sub_plot
         JOIN plantation_block pb ON sp.id_plantation_block = pb.id_plantation_block
WHERE COALESCE(p.is_deleted, false) = false
  AND COALESCE(sp.is_deleted, false) = false
  AND COALESCE(pb.is_deleted, false) = false@@


-- index sur la jointure plantation -> sub_plot
CREATE INDEX IF NOT EXISTS idx_plantation_id_sub_plot ON plantation (id_sub_plot)@@

-- index sur la jointure sub_plot -> plantation_block
    CREATE INDEX IF NOT EXISTS idx_sub_plot_id_block ON sub_plot (id_plantation_block)@@

-- index partiel: plantations non supprimées par date (si tu filtres souvent par date)
    CREATE INDEX IF NOT EXISTS idx_plantation_date_not_deleted ON plantation (date_plantation)
WHERE is_deleted = false@@

-- index sur species si souvent filtré
    CREATE INDEX IF NOT EXISTS dx_plantation_id_species ON plantation (id_species)@@

-- index pour les recherches par uuid (utile si tu cherches par uuid)
    CREATE INDEX IF NOT EXISTS idx_plantation_uuid ON plantation (uuid)@@
    CREATE INDEX IF NOT EXISTS idx_sub_plot_uuid ON sub_plot (uuid)@@
    CREATE INDEX IF NOT EXISTS idx_plantation_block_uuid ON plantation_block (uuid)@@


-- quantity de reforestation pour chaque type chose
    CREATE OR REPLACE VIEW v_ref_quantity_by_type_zone AS
SELECT tz.id_type_zone,
       tz.name AS type_zone_name,
       COALESCE(SUM(ref.quantity), 0)::INT AS total_quantity
FROM type_zone tz
         LEFT JOIN zone z
                   ON z.id_type_zone = tz.id_type_zone
                       AND COALESCE(z.is_deleted, false) = false
         LEFT JOIN reforestation ref
                   ON ref.id_zone = z.id_zone
                       AND COALESCE(ref.is_deleted, false) = false
WHERE COALESCE(tz.is_deleted, false) = false
GROUP BY tz.id_type_zone, tz.name
ORDER BY total_quantity DESC@@

-- quantity d'observation pour chaque type d'observation



-- taux de survie par la view v_plantations_by_block_subplot
CREATE OR REPLACE VIEW v_plantations_status_by_year AS
SELECT
    EXTRACT(YEAR FROM date_plantation)::int AS year,
  SUM(CASE WHEN COALESCE(plantation_status, false) THEN 1 ELSE 0 END) AS alive_count,
  SUM(CASE WHEN NOT COALESCE(plantation_status, false) THEN 1 ELSE 0 END) AS dead_count,
  COUNT(*) AS total_count
FROM v_plantations_by_block_subplot
WHERE date_plantation IS NOT NULL
GROUP BY year
ORDER BY year@@

    COMMIT@@