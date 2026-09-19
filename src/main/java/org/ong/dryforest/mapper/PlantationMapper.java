package org.ong.dryforest.mapper;

import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


import org.ong.dryforest.dto.plantation.PlantationMobileDTO;
import org.ong.dryforest.dto.plantation.PlantationStatusByYearDTO;
import org.ong.dryforest.dto.plantation.PlantationViewDTO;
import org.ong.dryforest.entity.Plantation;

public class PlantationMapper {
    public static PlantationMobileDTO toPlantationMobileDTO(Plantation plantation){
        PlantationMobileDTO plantationMobileDTO = new PlantationMobileDTO();
        
        plantationMobileDTO.setId_plantation(plantation.getId());
        plantationMobileDTO.setPlant_number(plantation.getPlant_number());
        plantationMobileDTO.setId_reforestation(plantation.getReforestation().getId());
        plantationMobileDTO.setId_species(plantation.getSpecies().getId());

        return plantationMobileDTO;
    }

    /** Map une ligne (Object[]) en PlantationStatusByYearDTO. */
    public static PlantationStatusByYearDTO toPlantationStatusByYearDTO(Object[] row) {
        if (row == null) return null;

        Integer year  = safeToInteger(row, 0, null);
        Integer alive = safeToInteger(row, 1, 0);
        Integer dead  = safeToInteger(row, 2, 0);
        Integer total = safeToInteger(row, 3, 0);

        return new PlantationStatusByYearDTO(year, alive, dead, total);
    }

    /** Map une liste de lignes en liste de DTOs. */
    public static List<PlantationStatusByYearDTO> toPlantationStatusByYearDTOList(List<Object[]> rows) {
        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }
        return rows.stream()
                    .map(PlantationMapper::toPlantationStatusByYearDTO)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
    }

    /** Conversion sûre d'une colonne en Integer, avec valeur par défaut si absent / non convertible. */
    private static Integer safeToInteger(Object[] row, int index, Integer defaultValue) {
        if (row == null || index < 0 || index >= row.length) return defaultValue;
        Object o = row[index];
        if (o == null) return defaultValue;

        if (o instanceof Number) {
            return ((Number) o).intValue();
        }

        // si ce n'est pas Number, tenter conversion via String (ex: "123")
        try {
            String s = o.toString().trim();
            if (s.isEmpty()) return defaultValue;
            return Integer.valueOf(s);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static PlantationViewDTO toPlantationViewDTO(Object[] r) {
        if (r == null) return null;
        PlantationViewDTO p = new PlantationViewDTO();
        int i = 0;
        Object o;

        // 0 id_plantation
        o = safeGet(r, i++); p.idPlantation = safeToIntegerObj(o);

        // 1 plantation_uuid
        o = safeGet(r, i++);
        p.plantationUuid = safeToUUID(o);

        // 2 diameter
        o = safeGet(r, i++); p.diameter = safeToBigDecimal(o);

        // 3 height
        o = safeGet(r, i++); p.height = safeToBigDecimal(o);

        // 4 carbon_sequestered
        o = safeGet(r, i++); p.carbonSequestered = safeToBigDecimal(o);

        // 5 image
        o = safeGet(r, i++); p.image = safeToString(o);

        // 6 date_plantation
        o = safeGet(r, i++); p.datePlantation = safeToLocalDate(o);

        // 7 plant_number
        o = safeGet(r, i++); p.plantNumber = safeToString(o);

        // 8 plantation_status
        o = safeGet(r, i++); p.plantationStatus = safeToBoolean(o);

        // 9 plantation_created_at
        o = safeGet(r, i++); p.plantationCreatedAt = safeToLocalDateTime(o);

        // 10 plantation_updated_at
        o = safeGet(r, i++); p.plantationUpdatedAt = safeToLocalDateTime(o);

        // 11 plantation_is_synced
        o = safeGet(r, i++); p.plantationIsSynced = safeToBoolean(o);

        // 12 plantation_is_deleted
        o = safeGet(r, i++); p.plantationIsDeleted = safeToBoolean(o);

        // 13 id_species
        o = safeGet(r, i++); p.idSpecies = safeToIntegerObj(o);

        o = safeGet(r, i++); p.speciesName = safeToString(o);

        // 14 id_sub_plot
        o = safeGet(r, i++); p.idSubPlot = safeToIntegerObj(o);

        // 15 id_reforestation  <-- si tu as ajouté ce champ
        o = safeGet(r, i++); p.idReforestation = safeToIntegerObj(o);

        // 16 sub_plot_name
        o = safeGet(r, i++); p.subPlotName = safeToString(o);

        // 17 plantation_block_name
        o = safeGet(r, i++); p.plantationBlockName = safeToString(o);

        // 18 id_plantation_block
        o = safeGet(r, i++); p.idPlantationBlock = safeToIntegerObj(o);

        return p;
    }

    public static List<PlantationViewDTO> toPlantationViewDTOList(List<Object[]> rows) {
        if (rows == null) return Collections.emptyList();
        return rows.stream()
                   .map(PlantationMapper::toPlantationViewDTO)
                   .filter(Objects::nonNull)
                   .collect(Collectors.toList());
    }

    // -------------------------
    // Safe conversion helpers
    // -------------------------
    private static Object safeGet(Object[] r, int idx) {
        if (r == null || idx < 0 || idx >= r.length) return null;
        return r[idx];
    }

    private static Integer safeToIntegerObj(Object o) {
        if (o == null) return null;
        if (o instanceof Number) return ((Number)o).intValue();
        try { return Integer.valueOf(o.toString()); } catch (Exception e) { return null; }
    }

    private static BigDecimal safeToBigDecimal(Object o) {
        if (o == null) return null;
        if (o instanceof BigDecimal) return (BigDecimal) o;
        if (o instanceof Number) return BigDecimal.valueOf(((Number)o).doubleValue());
        try { return new BigDecimal(o.toString()); } catch (Exception e) { return null; }
    }

    private static String safeToString(Object o) {
        if (o == null) return null;
        return o.toString();
    }

    private static Boolean safeToBoolean(Object o) {
        if (o == null) return null;
        if (o instanceof Boolean) return (Boolean) o;
        String s = o.toString().trim().toLowerCase();
        if (s.equals("true") || s.equals("t") || s.equals("1")) return true;
        if (s.equals("false") || s.equals("f") || s.equals("0")) return false;
        return null;
    }

    private static java.util.UUID safeToUUID(Object o) {
        if (o == null) return null;
        if (o instanceof java.util.UUID) return (java.util.UUID) o;
        try { return java.util.UUID.fromString(o.toString()); } catch (Exception e) { return null; }
    }

    private static LocalDate safeToLocalDate(Object o) {
        if (o == null) return null;
        if (o instanceof Date) return ((Date) o).toLocalDate();
        if (o instanceof Timestamp) return ((Timestamp)o).toLocalDateTime().toLocalDate();
        try { return LocalDate.parse(o.toString()); } catch (Exception ex) { return null; }
    }

    private static LocalDateTime safeToLocalDateTime(Object o) {
        if (o == null) return null;
        if (o instanceof Timestamp) return ((Timestamp)o).toLocalDateTime();
        if (o instanceof Date) return ((Date)o).toLocalDate().atStartOfDay();
        try { return LocalDateTime.parse(o.toString().replace(' ', 'T')); } catch (Exception ex) { return null; }
    }
}
