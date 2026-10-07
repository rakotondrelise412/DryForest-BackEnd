BEGIN@@

INSERT INTO role (id_role, name, created_at, updated_at, is_deleted) VALUES
(1, 'Admin',           '2025-12-18 09:00:00', '2025-12-18 09:00:00', false),
(2, 'Manager',         '2025-12-18 09:05:00', '2025-12-18 09:05:00', false),
(3, 'FieldAgent',      '2025-12-18 09:10:00', '2025-12-18 09:10:00', false),
(4, 'Viewer',          '2025-12-18 09:15:00', '2025-12-18 09:15:00', false)@@

INSERT INTO gender (id_gender, name) VALUES
(1, 'Home'),
(2, 'Femme')@@

INSERT INTO person (id_person, last_name, first_name, id_gender, email, phone_number, address, id_role, created_at, updated_at, is_deleted) VALUES
(1, 'Ramanamihaja', 'Voahary',   1, 'voahary@example.com', '0341234567', 'Lot II A 12, Antananarivo', 1, '2025-12-18 09:20:00', '2025-12-18 09:20:00', false),
(2, 'Rakotomalala','Miora',     2, 'miora@example.com',   '0329876543', 'Lot I B 5, Toamasina',     2, '2025-12-18 09:25:00', '2025-12-18 09:25:00', false),
(3, 'Andriamatoa','Jean',       1, 'jean@example.com',    '0331122334', 'Route Nationale 7, Fianarantsoa', 3, '2025-12-18 09:30:00', '2025-12-18 09:30:00', false),
(4, 'Rasoa',      'Lalao',      2, 'lalao@example.com',   '0349988776', 'Quartier Ambanidia, Toliara', 4, '2025-12-18 09:35:00', '2025-12-18 09:35:00', false)@@

-- Utilisateurs
INSERT INTO users (id_user, username, password, id_person) VALUES
(1, 'admin',      crypt('AdminPass@123', gen_salt('bf', 12)), 1),
(2, 'manager1',   crypt('ManagerPass@123', gen_salt('bf', 12)), 2),
(3, 'agent_jean', crypt('AgentPass@123',  gen_salt('bf', 12)), 3),
(4, 'viewer1',    crypt('ViewerPass@123', gen_salt('bf', 12)), 4)@@

-- 1) species_type
INSERT INTO species_type (id_species_type, name, created_at, updated_at, is_deleted) VALUES
(1, 'Arbre',        '2025-12-17 08:00:00', '2025-12-17 08:00:00', false),
(2, 'Arbuste',      '2025-12-17 08:05:00', '2025-12-17 08:05:00', false),
(3, 'Succulente',   '2025-12-17 08:10:00', '2025-12-17 08:10:00', false),
(4, 'Arbrisseau',   '2025-12-17 08:15:00', '2025-12-17 08:15:00', false)@@

-- 2) species
INSERT INTO species (id_species, mg_name, fr_name, en_name, density, scientific_name, created_at, updated_at, is_deleted, id_species_type) VALUES
(1, 'Reniala',   'Baobab za',                'Madagascar baobab',          15.00,  'Adansonia za',                 '2025-12-17 08:20:00', '2025-12-17 08:20:00', false, 1),
(2, 'Baobab',    'Baobab grandidier',        'Grandidier''s baobab',       8.00,   'Adansonia grandidieri',         '2025-12-17 08:21:00', '2025-12-17 08:21:00', false, 1),
(3, 'Alluaudia', 'Alluaudia',                'Alluaudia',                  120.00, 'Alluaudia procera',             '2025-12-17 08:22:00', '2025-12-17 08:22:00', false, 1),
(4, 'Didierea',  'Didierea',                 'Didierea',                   90.00,  'Didierea madagascariensis',     '2025-12-17 08:23:00', '2025-12-17 08:23:00', false, 1),
(5, 'Pachypodium','Pachypodium',              'Bottle tree / Pachypodium',  30.00,  'Pachypodium rosulatum',         '2025-12-17 08:24:00', '2025-12-17 08:24:00', false, 1),
(6, 'Commiphora','Commiphora',               'Commiphora (myrrh-like)',    150.00, 'Commiphora spp.',               '2025-12-17 08:25:00', '2025-12-17 08:25:00', false, 2),
(7, 'Euphorbia', 'Euphorbia stenoclada',     'Spiny euphorbia',            300.00, 'Euphorbia stenoclada',          '2025-12-17 08:26:00', '2025-12-17 08:26:00', false, 3),
(8, 'Cedrelopsis','Cedrelopsis grevei',      'Cedrelopsis',                80.00,  'Cedrelopsis grevei',            '2025-12-17 08:27:00', '2025-12-17 08:27:00', false, 1),
(9, 'Moringa',   'Moringa drouhardii',       'Drought-tolerant Moringa',  200.00, 'Moringa drouhardii',            '2025-12-17 08:28:00', '2025-12-17 08:28:00', false, 2),
(10,'Ziziphus',  'Ziziphus',                 'Jujube-like',                220.00, 'Ziziphus spp.',                 '2025-12-17 08:29:00', '2025-12-17 08:29:00', false, 2),
(11,'Operculicarya','Operculicarya decaryi', 'Elephant tree',               60.00, 'Operculicarya decaryi',         '2025-12-17 08:30:00', '2025-12-17 08:30:00', false, 1),
(12,'Aloe',      'Aloe spp.',                'Aloe',                       400.00, 'Aloe spp.',                     '2025-12-17 08:31:00', '2025-12-17 08:31:00', false, 3)@@

-- 3) severity
INSERT INTO severity (id_severity, name, created_at, updated_at, is_deleted) VALUES
(1, 'Faible',   '2025-01-15 10:00:00', '2025-01-15 10:00:00', false),
(2, 'Moyen',    '2025-01-15 10:05:00', '2025-01-15 10:05:00', false),
(3, 'Élevé',    '2025-01-15 10:10:00', '2025-01-15 10:10:00', false),
(4, 'Critique', '2025-01-15 10:15:00', '2025-01-15 10:15:00', false)@@

INSERT INTO type_zone (id_type_zone, name, created_at, updated_at, is_deleted) VALUES
(1, 'Foret',            '2025-12-18 08:00:00', '2025-12-18 08:00:00', false),
(2, 'Marais',           '2025-12-18 08:05:00', '2025-12-18 08:05:00', false),
(3, 'Savane',           '2025-12-18 08:10:00', '2025-12-18 08:10:00', false),
(4, 'Reserve_protege',  '2025-12-18 08:15:00', '2025-12-18 08:15:00', false)@@

INSERT INTO zone (id_zone, uuid, name, area, geom, created_at, updated_at, is_synced, is_deleted, id_type_zone) VALUES
(1, '3a1f6b2e-1111-4f2a-9b8c-000000000001', 'Foret_Morahita', 25000.00,
    ST_GeomFromText('POLYGON((44.0850 -20.7120, 44.0920 -20.7050, 44.1010 -20.6980, 44.1150 -20.6950, 44.1280 -20.7010, 44.1350 -20.7100, 44.1420 -20.7250, 44.1380 -20.7380, 44.1250 -20.7450, 44.1120 -20.7480, 44.0980 -20.7520, 44.0880 -20.7490, 44.0790 -20.7410, 44.0750 -20.7300, 44.0780 -20.7220, 44.0850 -20.7120))', 4326),
    '2025-02-01 08:30:00', '2025-02-01 08:30:00', true, false, 1),

(2, '3a1f6b2e-1111-4f2a-9b8c-000000000002', 'Marais_Belo', 12440.00,
    ST_GeomFromText('POLYGON((44.1200 -20.8250, 44.1280 -20.8180, 44.1350 -20.8120, 44.1450 -20.8100, 44.1520 -20.8150, 44.1580 -20.8220, 44.1620 -20.8300, 44.1590 -20.8410, 44.1510 -20.8480, 44.1420 -20.8520, 44.1320 -20.8500, 44.1250 -20.8450, 44.1180 -20.8380, 44.1200 -20.8250))', 4326),
    '2025-03-10 07:00:00', '2025-03-10 07:00:00', false, false, 2),

(3, '3a1f6b2e-1111-4f2a-9b8c-000000000003', 'Savane_Ambararata', 31000.00,
    ST_GeomFromText('POLYGON((44.1650 -20.8200, 44.1750 -20.8100, 44.1880 -20.8050, 44.2020 -20.8080, 44.2150 -20.8150, 44.2220 -20.8250, 44.2280 -20.8380, 44.2240 -20.8510, 44.2150 -20.8620, 44.2010 -20.8680, 44.1880 -20.8650, 44.1760 -20.8580, 44.1680 -20.8450, 44.1620 -20.8320, 44.1650 -20.8200))', 4326),
    '2025-04-05 15:20:00', '2025-04-05 15:20:00', false, false, 3),

(4, '3a1f6b2e-1111-4f2a-9b8c-000000000004', 'Reserve_Kirindy_Mitea', 45000.00,
    ST_GeomFromText('POLYGON((44.0200 -20.8800, 44.0350 -20.8650, 44.0550 -20.8550, 44.0800 -20.8520, 44.1050 -20.8600, 44.1220 -20.8750, 44.1350 -20.8950, 44.1380 -20.9150, 44.1280 -20.9350, 44.1120 -20.9500, 44.0900 -20.9620, 44.0650 -20.9650, 44.0420 -20.9550, 44.0250 -20.9380, 44.0150 -20.9150, 44.0120 -20.8980, 44.0200 -20.8800))', 4326),
    '2025-01-20 09:00:00', '2025-06-01 10:00:00', true, false, 4),

(5, '3a1f6b2e-1111-4f2a-9b8c-000000000005', 'Foret_Marofihitsy', 15000.00,
    ST_GeomFromText('POLYGON((44.1800 -20.7100, 44.1920 -20.7020, 44.2050 -20.6980, 44.2180 -20.7010, 44.2280 -20.7080, 44.2350 -20.7180, 44.2390 -20.7300, 44.2360 -20.7420, 44.2280 -20.7510, 44.2150 -20.7580, 44.2020 -20.7600, 44.1880 -20.7550, 44.1800 -20.7450, 44.1750 -20.7320, 44.1760 -20.7200, 44.1800 -20.7100))', 4326),
    '2025-05-12 11:11:00', '2025-05-12 11:11:00', false, false, 1)@@

INSERT INTO zone_need (id_zone_need, uuid, created_at, updated_at, is_synced, is_deleted, id_zone) VALUES
(1, gen_random_uuid(), '2025-06-01 08:00:00', '2025-06-01 08:00:00', false, false, 1),
(2, gen_random_uuid(), '2025-06-02 09:00:00', '2025-06-02 09:00:00', false, false, 2),
(3, gen_random_uuid(), '2025-06-03 10:15:00', '2025-06-03 10:15:00', false, false, 3),
(4, gen_random_uuid(), '2025-06-04 11:30:00', '2025-06-04 11:30:00', false, false, 5)@@

INSERT INTO species_zone_need (id_species_zone_need, uuid, id_zone_need, id_species, created_at, updated_at, is_synced, is_deleted) VALUES
(1, gen_random_uuid(), 1, 1, '2025-06-01 08:05:00', '2025-06-01 08:05:00', false, false),
(2, gen_random_uuid(), 1, 3, '2025-06-01 08:06:00', '2025-06-01 08:06:00', false, false),
(3, gen_random_uuid(), 1, 8, '2025-06-01 08:07:00', '2025-06-01 08:07:00', false, false),
(4, gen_random_uuid(), 2, 5, '2025-06-02 09:10:00', '2025-06-02 09:10:00', false, false),
(5, gen_random_uuid(), 2, 9, '2025-06-02 09:12:00', '2025-06-02 09:12:00', false, false),
(6, gen_random_uuid(), 3, 4, '2025-06-03 10:20:00', '2025-06-03 10:20:00', false, false),
(7, gen_random_uuid(), 3, 7, '2025-06-03 10:22:00', '2025-06-03 10:22:00', false, false),
(8, gen_random_uuid(), 3, 12, '2025-06-03 10:25:00', '2025-06-03 10:25:00', false, false),
(9, gen_random_uuid(), 4, 2, '2025-06-04 11:40:00', '2025-06-04 11:40:00', false, false),
(10, gen_random_uuid(), 4, 11, '2025-06-04 11:43:00', '2025-06-04 11:43:00', false, false)@@

INSERT INTO reforestation (id_reforestation, uuid, date_reforestation, quantity, created_at, updated_at, is_synced, is_deleted, id_zone) VALUES
(1, gen_random_uuid(), '2022-12-18', 78500, '2022-12-18 06:15:00', '2022-12-18 14:30:00', true, false, 1),
(2, gen_random_uuid(), '2023-01-22', 82100, '2023-01-22 07:00:00', '2023-01-22 16:00:00', true, false, 2),
(3, gen_random_uuid(), '2024-02-05', 85400, '2024-02-05 06:45:00', '2024-02-05 15:20:00', true, false, 3),
(4, gen_random_uuid(), '2025-01-14', 88000, '2025-01-14 07:10:00', '2025-01-14 13:45:00', true, false, 5)@@

INSERT INTO reforestation_detail (id_reforestation_detail, uuid, quantity, created_at, updated_at, is_synced, is_deleted, id_species, id_reforestation) VALUES
(1, gen_random_uuid(), 25000, '2022-12-18 08:00:00', '2022-12-18 14:30:00', true, false, 1, 1),
(2, gen_random_uuid(), 33500, '2022-12-18 08:05:00', '2022-12-18 14:30:00', true, false, 3, 1),
(3, gen_random_uuid(), 20000, '2022-12-18 08:10:00', '2022-12-18 14:30:00', true, false, 8, 1),
(4, gen_random_uuid(), 42100, '2023-01-22 08:00:00', '2023-01-22 16:00:00', true, false, 5, 2),
(5, gen_random_uuid(), 40000, '2023-01-22 08:15:00', '2023-01-22 16:00:00', true, false, 9, 2),
(6, gen_random_uuid(), 35400, '2024-02-05 07:30:00', '2024-02-05 15:20:00', true, false, 4, 3),
(7, gen_random_uuid(), 30000, '2024-02-05 07:45:00', '2024-02-05 15:20:00', true, false, 7, 3),
(8, gen_random_uuid(), 20000, '2024-02-05 08:00:00', '2024-02-05 15:20:00', true, false, 12, 3),
(9, gen_random_uuid(), 38000, '2025-01-14 08:00:00', '2025-01-14 13:45:00', true, false, 2, 4),
(10, gen_random_uuid(), 50000, '2025-01-14 08:20:00', '2025-01-14 13:45:00', true, false, 11, 4)@@

INSERT INTO plantation_block (id_plantation_block, uuid, name, width, length, nb_sub_plot, geom, created_at, updated_at, is_synced, is_deleted, id_zone) VALUES
(1, gen_random_uuid(), 'Placeau A1 - Massif Nord', 50.00, 80.00, 2,
    ST_GeomFromText('POLYGON((44.0880 -20.7150, 44.0940 -20.7150, 44.0940 -20.7100, 44.0880 -20.7100, 44.0880 -20.7150))', 4326),
    '2025-07-20 08:00:00', '2025-07-20 08:00:00', false, false, 1),
(2, gen_random_uuid(), 'Placeau A2 - Transition Ouest', 40.00, 60.00, 1,
    ST_GeomFromText('POLYGON((44.1000 -20.7300, 44.1060 -20.7300, 44.1060 -20.7250, 44.1000 -20.7250, 44.1000 -20.7300))', 4326),
    '2025-07-21 09:00:00', '2025-07-21 09:00:00', false, false, 1),
(3, gen_random_uuid(), 'Placeau B1 - Lisière Humide', 100.00, 100.00, 2,
    ST_GeomFromText('POLYGON((44.1300 -20.8350, 44.1380 -20.8350, 44.1380 -20.8280, 44.1300 -20.8280, 44.1300 -20.8350))', 4326),
    '2025-07-22 10:00:00', '2025-07-22 10:00:00', false, false, 2)@@

INSERT INTO sub_plot (id_sub_plot, uuid, name, width, length, location, created_at, updated_at, is_synced, is_deleted, id_plantation_block) VALUES
(1, gen_random_uuid(), 'Placette A1-1', 25.00, 40.00,
    ST_GeomFromText('POINT(44.0900 -20.7120)', 4326),
    '2025-07-20 08:15:00', '2025-07-20 08:15:00', false, false, 1),
(2, gen_random_uuid(), 'Placette A1-2', 25.00, 40.00,
    ST_GeomFromText('POINT(44.0920 -20.7130)', 4326),
    '2025-07-20 08:20:00', '2025-07-20 08:20:00', false, false, 1),
(3, gen_random_uuid(), 'Placette A2-1', 40.00, 60.00,
    ST_GeomFromText('POINT(44.1030 -20.7270)', 4326),
    '2025-07-21 09:15:00', '2025-07-21 09:15:00', false, false, 2),
(4, gen_random_uuid(), 'Placette B1-1', 50.00, 50.00,
    ST_GeomFromText('POINT(44.1320 -20.8310)', 4326),
    '2025-07-22 10:20:00', '2025-07-22 10:20:00', false, false, 3),
(5, gen_random_uuid(), 'Placette B1-2', 50.00, 50.00,
    ST_GeomFromText('POINT(44.1350 -20.8330)', 4326),
    '2025-07-22 10:30:00', '2025-07-22 10:30:00', false, false, 3)@@

INSERT INTO plantation_block (id_plantation_block, uuid, name, width, length, nb_sub_plot, geom, created_at, updated_at, is_synced, is_deleted, id_zone) VALUES
(4, gen_random_uuid(), 'Placeau C1 - Plaine Aride', 60.00, 60.00, 2,
    ST_GeomFromText('POLYGON((44.1800 -20.8300, 44.1860 -20.8300, 44.1860 -20.8240, 44.1800 -20.8240, 44.1800 -20.8300))', 4326),
    '2025-07-23 08:00:00', '2025-07-23 08:00:00', false, false, 3),
(5, gen_random_uuid(), 'Placeau C2 - Zone Épineuse', 40.00, 50.00, 1,
    ST_GeomFromText('POLYGON((44.2000 -20.8400, 44.2050 -20.8400, 44.2050 -20.8350, 44.2000 -20.8350, 44.2000 -20.8400))', 4326),
    '2025-07-24 09:15:00', '2025-07-24 09:15:00', false, false, 3),
(6, gen_random_uuid(), 'Placeau E1 - Massif Baobab', 100.00, 120.00, 3,
    ST_GeomFromText('POLYGON((44.1900 -20.7200, 44.1980 -20.7200, 44.1980 -20.7120, 44.1900 -20.7120, 44.1900 -20.7200))', 4326),
    '2025-07-25 10:30:00', '2025-07-25 10:30:00', false, false, 5)@@

INSERT INTO sub_plot (id_sub_plot, uuid, name, width, length, location, created_at, updated_at, is_synced, is_deleted, id_plantation_block) VALUES
(6, gen_random_uuid(), 'Placette C1-1', 30.00, 30.00,
    ST_GeomFromText('POINT(44.1820 -20.8270)', 4326),
    '2025-07-23 08:30:00', '2025-07-23 08:30:00', false, false, 4),
(7, gen_random_uuid(), 'Placette C1-2', 30.00, 30.00,
    ST_GeomFromText('POINT(44.1840 -20.8260)', 4326),
    '2025-07-23 08:45:00', '2025-07-23 08:45:00', false, false, 4),
(8, gen_random_uuid(), 'Placette C2-1', 40.00, 50.00,
    ST_GeomFromText('POINT(44.2020 -20.8380)', 4326),
    '2025-07-24 09:40:00', '2025-07-24 09:40:00', false, false, 5),
(9, gen_random_uuid(), 'Placette E1-1', 30.00, 40.00,
    ST_GeomFromText('POINT(44.1920 -20.7160)', 4326),
    '2025-07-25 11:00:00', '2025-07-25 11:00:00', false, false, 6),
(10, gen_random_uuid(), 'Placette E1-2', 30.00, 40.00,
    ST_GeomFromText('POINT(44.1940 -20.7150)', 4326),
    '2025-07-25 11:15:00', '2025-07-25 11:15:00', false, false, 6),
(11, gen_random_uuid(), 'Placette E1-3', 30.00, 40.00,
    ST_GeomFromText('POINT(44.1960 -20.7140)', 4326),
    '2025-07-25 11:30:00', '2025-07-25 11:30:00', false, false, 6)@@

DO $$
DECLARE
i INT;
    v_id_species INT;
    v_id_sub_plot INT;
    v_id_reforestation INT;
    v_diameter NUMERIC(4,2);
    v_height NUMERIC(4,2);
    v_carbon NUMERIC(5,2);
    v_status BOOLEAN;
    v_date_plantation DATE;
BEGIN

FOR i IN 9..1008 LOOP

        CASE
            WHEN i % 4 = 0 THEN
                v_id_reforestation := 1;
                v_id_sub_plot := (floor(random() * 2) + 1)::INT;
                v_id_species := (ARRAY[1, 3, 8])[floor(random() * 3) + 1];
                v_date_plantation := '2022-12-25';

WHEN i % 4 = 1 THEN
                v_id_reforestation := 2;
                v_id_sub_plot := 3;
                v_id_species := (ARRAY[5, 9])[floor(random() * 2) + 1];
                v_date_plantation := '2023-02-02';

WHEN i % 4 = 2 THEN
                v_id_reforestation := 3;
                v_id_sub_plot := (floor(random() * 3) + 6)::INT;
                v_id_species := (ARRAY[4, 7, 12])[floor(random() * 3) + 1];
                v_date_plantation := '2024-02-12';

ELSE
                v_id_reforestation := 4;
                v_id_sub_plot := (floor(random() * 3) + 9)::INT;
                v_id_species := (ARRAY[2, 11])[floor(random() * 2) + 1];
                v_date_plantation := '2025-01-20';
END CASE;

        v_height := round(
            (random() * (3.5 - 0.5) + 0.5)::numeric,
            2
        );

        v_diameter := round(
            (v_height * (2.5 - 1.2) + 1.0)::numeric,
            2
        );

        v_carbon := round(
            (v_diameter * 0.18)::numeric,
            2
        );

        v_status := (random() < 0.82);

INSERT INTO plantation (
    id_plantation,
    uuid,
    diameter,
    height,
    carbon_sequestered,
    image,
    date_plantation,
    plant_number,
    status,
    created_at,
    updated_at,
    is_synced,
    is_deleted,
    id_species,
    id_sub_plot,
    id_reforestation
)
VALUES (
           i,
           gen_random_uuid(),
           v_diameter,
           v_height,
           v_carbon,
           NULL,
           v_date_plantation,
           'PL-' || lpad(i::text, 4, '0'),
           v_status,
           v_date_plantation + interval '8 hours',
           v_date_plantation + interval '8 hours',
           true,
           false,
           v_id_species,
           v_id_sub_plot,
           v_id_reforestation
       );

END LOOP;

END $$@@

DO $$
DECLARE
v_record RECORD;
    v_id_monitoring INT := 9;
    v_growth_h NUMERIC(4,2);
    v_growth_d NUMERIC(4,2);
    v_new_height NUMERIC(4,2);
    v_new_diameter NUMERIC(4,2);
    v_new_carbon NUMERIC(5,2);
    v_monitoring_date DATE;
BEGIN

FOR v_record IN
SELECT
    id_plantation,
    diameter,
    height,
    carbon_sequestered,
    date_plantation,
    status
FROM plantation
WHERE id_plantation >= 9
ORDER BY id_plantation
    LOOP

        -- Arbre vivant
        IF v_record.status = TRUE THEN

            v_growth_h := round(
                (random() * (0.60 - 0.15) + 0.15)::numeric,
                2
            );

v_growth_d := round(
                (v_growth_h * 0.4)::numeric,
                2
            );

            v_new_height := v_record.height + v_growth_h;
            v_new_diameter := v_record.diameter + v_growth_d;

            v_new_carbon := round(
                (v_new_diameter * 0.22)::numeric,
                2
            );

            v_monitoring_date :=
                v_record.date_plantation + interval '240 days';

INSERT INTO planting_monitoring (
    id_planting_monitoring,
    uuid,
    diameter,
    height,
    density,
    carbon_sequestered,
    image,
    date_planting_monitoring,
    auto_generation,
    created_at,
    updated_at,
    is_synced,
    is_deleted,
    id_plantation
)
VALUES (
           v_id_monitoring,
           gen_random_uuid(),
           v_new_diameter,
           v_new_height,
           1100.00,
           v_new_carbon,
           NULL,
           v_monitoring_date,

           -- CORRIGÉ : BOOLEAN
           random() < 0.5,

           v_monitoring_date + interval '9 hours',
           v_monitoring_date + interval '9 hours',
           true,
           false,
           v_record.id_plantation
       );

v_id_monitoring := v_id_monitoring + 1;

        -- Arbre mort
ELSE

            v_monitoring_date :=
                v_record.date_plantation + interval '90 days';

INSERT INTO planting_monitoring (
    id_planting_monitoring,
    uuid,
    diameter,
    height,
    density,
    carbon_sequestered,
    image,
    date_planting_monitoring,
    auto_generation,
    created_at,
    updated_at,
    is_synced,
    is_deleted,
    id_plantation
)
VALUES (
           v_id_monitoring,
           gen_random_uuid(),
           v_record.diameter,
           v_record.height,
           1050.00,
           v_record.carbon_sequestered,
           NULL,
           v_monitoring_date,

           -- CORRIGÉ : BOOLEAN
           false,

           v_monitoring_date + interval '14 hours',
           v_monitoring_date + interval '14 hours',
           true,
           false,
           v_record.id_plantation
       );

v_id_monitoring := v_id_monitoring + 1;

END IF;

END LOOP;

END $$@@

INSERT INTO patrol_group (id_patrol_group, name, created_at, updated_at, is_deleted) VALUES
(1, 'Polisin''ala Reniala', '2025-05-01 06:00:00', '2025-05-01 06:00:00', false),
(2, 'Brigade Fosa Kirindy', '2025-05-02 06:00:00', '2025-05-02 06:00:00', false),
(3, 'Vondrona Ankoay', '2025-05-03 07:30:00', '2025-05-03 07:30:00', false),
(4, 'Mpiandry Ala Sakoa', '2025-05-04 08:00:00', '2025-05-04 08:00:00', false),
(5, 'KDL Antsira (Komiti mpiaro)', '2025-05-05 06:45:00', '2025-05-05 06:45:00', false)@@

INSERT INTO incident_patrol_type (id_incident_patrol_type, name, created_at, updated_at, is_deleted) VALUES
(1, 'Braconnage', '2025-04-01 08:00:00', '2025-04-01 08:00:00', false),
(2, 'Feu de brousse', '2025-04-02 09:00:00', '2025-04-02 09:00:00', false),
(3, 'Déforestation illégale', '2025-04-03 10:00:00', '2025-04-03 10:00:00', false),
(4, 'Charbonnage illégal (Tamany)', '2025-04-04 11:00:00', '2025-04-04 11:00:00', false),
(5, 'Incursion de bétail / Pâturage de zébus', '2025-04-05 08:30:00', '2025-04-05 08:30:00', false),
(6, 'Collecte illégale de produits forestiers', '2025-04-06 09:15:00', '2025-04-06 09:15:00', false)@@

INSERT INTO observation_patrol_type (id_observation_patrol_type, name, created_at, updated_at, is_deleted) VALUES
(1, 'Observation faunique', '2025-03-01 07:00:00', '2025-03-01 07:00:00', false),
(2, 'Condition végétale', '2025-03-02 07:00:00', '2025-03-02 07:00:00', false),
(3, 'Régénération naturelle (Jeunes pousses)', '2025-03-03 08:00:00', '2025-03-03 08:00:00', false),
(4, 'Floraison et fructification (Phénologie)', '2025-03-04 09:00:00', '2025-03-04 09:00:00', false),
(5, 'Nidification / Site de reproduction', '2025-03-05 10:30:00', '2025-03-05 10:30:00', false)@@

INSERT INTO incident_patrol (id_incident_patrol, uuid, datetime_incident, location, description, image, created_at, updated_at, is_synced, is_deleted, id_patrol_group, id_user, id_zone, id_plantation_block, id_severity, id_incident_patrol_type) VALUES
(1, gen_random_uuid(), '2025-09-10 16:30:00', ST_GeomFromText('POINT(44.0910 -20.7120)', 4326),
    'Traces de coupe fraîche sur 3 arbres matures (essence Cedrelopsis/Katrafay). Outils abandonnés sur place.', NULL, '2025-09-10 17:00:00', '2025-09-10 17:00:00', true, false, 1, 1, 1, 1, 3, 3),

(2, gen_random_uuid(), '2025-10-05 11:00:00', ST_GeomFromText('POINT(44.1020 -20.7280)', 4326),
    'Foyer de feu anthropique détecté en lisière. Maîtrisé rapidement par l''équipe Polisin''ala à l''aide de pare-feux.', NULL, '2025-10-05 11:30:00', '2025-10-05 11:30:00', true, false, 1, 1, 1, 2, 2, 2),

(3, gen_random_uuid(), '2025-11-12 09:15:00', ST_GeomFromText('POINT(44.1340 -20.8320)', 4326),
    'Intrusion d''un troupeau d''environ 15 zébus sans gardien. Destruction par piétinement de jeunes pousses de Moringa.', NULL, '2025-11-12 10:00:00', '2025-11-12 10:00:00', true, false, 3, 1, 2, 3, 2, 5),

(4, gen_random_uuid(), '2025-12-01 06:45:00', ST_GeomFromText('POINT(44.1830 -20.8260)', 4326),
    'Découverte et destruction de 4 collets artisanaux destinés au braconnage de petits mammifères (Fosa ou lémuriens).', NULL, '2025-12-01 08:00:00', '2025-12-01 08:00:00', true, false, 4, 1, 3, 4, 3, 1),

(5, gen_random_uuid(), '2026-01-20 14:00:00', ST_GeomFromText('POINT(44.1930 -20.7150)', 4326),
    'Saisie de sacs de cueillette illégale contenant des fruits et graines de Baobab Grandidieri destinés au marché noir.', NULL, '2026-01-20 15:30:00', '2026-01-20 15:30:00', true, false, 5, 1, 5, 6, 2, 6)@@

INSERT INTO observation_pat (id_observation_pat, uuid, datetime_observation, description, location, created_at, updated_at, is_synced, is_deleted, id_observation_patrol_type, id_patrol_group, id_user, id_zone) VALUES
(1, gen_random_uuid(), '2025-09-12 08:00:00',
    'Observation visuelle de deux couples de lémuriens Microcebus berthae en bonne santé dans le sous-bois dense.',
    ST_GeomFromText('POINT(44.0890 -20.7130)', 4326),
    '2025-09-12 08:20:00', '2025-09-12 08:20:00', true, false, 1, 1, 1, 1),

(2, gen_random_uuid(), '2025-10-18 10:30:00',
    'Nid actif identifié au sommet d''un arbre sec avec présence d''un rapace Ankoay (Pygargue de Madagascar). Zone à éviter lors des reforestations.',
    ST_GeomFromText('POINT(44.1250 -20.8220)', 4326),
    '2025-10-18 11:00:00', '2025-10-18 11:00:00', true, false, 5, 3, 1, 2),

(3, gen_random_uuid(), '2025-11-05 07:15:00',
    'Forte densité de régénération naturelle de jeunes pousses d''Euphorbia stenoclada suite aux pluies précoces.',
    ST_GeomFromText('POINT(44.1720 -20.8350)', 4326),
    '2025-11-05 08:00:00', '2025-11-05 08:00:00', true, false, 3, 4, 1, 3),

(4, gen_random_uuid(), '2026-02-14 17:45:00',
    'Début de la floraison massive des spécimens d''Adansonia grandidieri. Excellente période pour planifier la récolte scientifique des graines.',
    ST_GeomFromText('POINT(44.2100 -20.7350)', 4326),
    '2026-02-14 18:30:00', '2026-02-14 18:30:00', true, false, 4, 5, 1, 5)@@

INSERT INTO category_animal (id_category_animal, name, created_at, updated_at, is_deleted) VALUES
(1, 'Mammifère', '2025-02-01 08:00:00', '2025-02-01 08:00:00', false),
(2, 'Oiseau',    '2025-02-01 08:05:00', '2025-02-01 08:05:00', false),
(3, 'Reptile',   '2025-02-01 08:10:00', '2025-02-01 08:10:00', false)@@

INSERT INTO animal (id_animal, name, id_category_animal, created_at, updated_at, is_deleted) VALUES
(1, 'Microcebus berthae (Microcèbe de Madame Berthe)', 1, '2025-02-05 09:00:00', '2025-02-05 09:00:00', false),
(3, 'Cryptoprocta ferox (Fosa)', 1, '2025-02-05 09:15:00', '2025-02-05 09:15:00', false),
(4, 'Hypogeomys antimena (Rat sauteur géant du Menabe)', 1, '2025-02-05 09:20:00', '2025-02-05 09:20:00', false),
(2, 'Coua caerulea (Coua bleu)', 2, '2025-02-06 09:10:00', '2025-02-06 09:10:00', false),
(5, 'Haliaeetus vociferoides (Pygargues de Madagascar / Ankoay)', 2, '2025-02-06 09:40:00', '2025-02-06 09:40:00', false),
(6, 'Furcifer labordi (Caméléon de Labord)', 3, '2025-02-07 10:00:00', '2025-02-07 10:00:00', false),
(7, 'Acrantophis madagascariensis (Boa de Madagascar)', 3, '2025-02-07 10:15:00', '2025-02-07 10:15:00', false)@@

INSERT INTO animal_tracking (id_animal_tracking, uuid, date_tracking, description, location, id_plantation_block, created_at, updated_at, is_synced, is_deleted) VALUES
(1, gen_random_uuid(), '2025-09-12 07:45:00',
    'Traces de pas fraîches au sol et restes de fruits rongés observés près des baobabs.',
    ST_GeomFromText('POINT(44.0912 -20.7125)', 4326), 1,
    '2025-09-12 08:00:00', '2025-09-12 08:00:00', true, false),

(2, gen_random_uuid(), '2025-10-20 18:30:00',
    'Session d''écoute nocturne près de la zone de transition humide. Forte activité biologique.',
    ST_GeomFromText('POINT(44.1325 -20.8315)', 4326), 3,
    '2025-10-20 19:30:00', '2025-10-20 19:30:00', true, false),

(3, gen_random_uuid(), '2025-11-05 10:15:00',
    'Patrouille de suivi biodiversité le long du transect principal de restauration.',
    ST_GeomFromText('POINT(44.1945 -20.7155)', 4326), 6,
    '2025-11-05 12:00:00', '2025-11-05 12:00:00', true, false)@@

INSERT INTO animal_tracking_detail (id_animal_tracking_detail, have_seen, uuid, id_animal, id_animal_tracking, created_at, updated_at, is_synced, is_deleted) VALUES
(1, true,  gen_random_uuid(), 1, 1, '2025-09-12 08:05:00', '2025-09-12 08:05:00', true, false),
(2, false, gen_random_uuid(), 2, 1, '2025-09-12 08:06:00', '2025-09-12 08:06:00', true, false),
(3, true,  gen_random_uuid(), 3, 1, '2025-09-12 08:10:00', '2025-09-12 08:10:00', true, false),
(4, true,  gen_random_uuid(), 4, 2, '2025-10-20 19:40:00', '2025-10-20 19:40:00', true, false),
(5, true,  gen_random_uuid(), 5, 2, '2025-10-20 19:42:00', '2025-10-20 19:42:00', true, false),
(6, false, gen_random_uuid(), 7, 2, '2025-10-20 19:45:00', '2025-10-20 19:45:00', true, false),
(7, true,  gen_random_uuid(), 6, 3, '2025-11-05 12:05:00', '2025-11-05 12:05:00', true, false),
(8, true,  gen_random_uuid(), 2, 3, '2025-11-05 12:10:00', '2025-11-05 12:10:00', true, false)@@

COMMIT@@