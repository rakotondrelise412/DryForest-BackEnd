package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.plantation.PlantationMobileDTO;
import org.ong.dryforest.dto.plantation.PlantationStatusByYearDTO;
import org.ong.dryforest.dto.plantation.PlantationViewDTO;
import org.ong.dryforest.entity.Plantation;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlantationMapper {

    private PlantationMapper() {
    }

    // ============================================================
    // MOBILE
    // ============================================================

    public static PlantationMobileDTO toPlantationMobileDTO(
            Plantation plantation
    ) {
        if (plantation == null) {
            return null;
        }

        PlantationMobileDTO dto = new PlantationMobileDTO();

        dto.setId_plantation(plantation.getId());
        dto.setPlant_number(plantation.getPlant_number());

        if (plantation.getReforestation() != null) {
            dto.setId_reforestation(
                    plantation.getReforestation().getId()
            );
        }

        if (plantation.getSpecies() != null) {
            dto.setId_species(
                    plantation.getSpecies().getId()
            );
        }

        if (plantation.getSubPlot() != null) {
            dto.setId_sub_plot(
                    plantation.getSubPlot().getId()
            );
        }

        return dto;
    }

    // ============================================================
    // STATUS BY YEAR
    // ============================================================

    public static PlantationStatusByYearDTO toPlantationStatusByYearDTO(
            Object[] row
    ) {
        if (row == null) {
            return null;
        }

        Integer year = safeToInteger(row, 0, null);
        Integer alive = safeToInteger(row, 1, 0);
        Integer dead = safeToInteger(row, 2, 0);
        Integer total = safeToInteger(row, 3, 0);

        return new PlantationStatusByYearDTO(
                year,
                alive,
                dead,
                total
        );
    }

    public static List<PlantationStatusByYearDTO>
    toPlantationStatusByYearDTOList(List<Object[]> rows) {

        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }

        return rows.stream()
                .map(PlantationMapper::toPlantationStatusByYearDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static Integer safeToInteger(
            Object[] row,
            int index,
            Integer defaultValue
    ) {
        if (row == null || index < 0 || index >= row.length) {
            return defaultValue;
        }

        Object value = row[index];

        if (value == null) {
            return defaultValue;
        }

        if (value instanceof Number number) {
            return number.intValue();
        }

        try {
            String text = value.toString().trim();

            if (text.isEmpty()) {
                return defaultValue;
            }

            return Integer.valueOf(text);

        } catch (Exception e) {
            return defaultValue;
        }
    }

    // ============================================================
    // VIEW DTO
    // ============================================================

    public static PlantationViewDTO toPlantationViewDTO(Object[] row) {

        if (row == null) {
            return null;
        }

        PlantationViewDTO dto = new PlantationViewDTO();

        int i = 0;

        dto.idPlantation = safeToIntegerObj(safeGet(row, i++));
        dto.plantationUuid = safeToUUID(safeGet(row, i++));
        dto.diameter = safeToBigDecimal(safeGet(row, i++));
        dto.height = safeToBigDecimal(safeGet(row, i++));
        dto.carbonSequestered = safeToBigDecimal(safeGet(row, i++));
        dto.image = safeToString(safeGet(row, i++));
        dto.datePlantation = safeToLocalDate(safeGet(row, i++));
        dto.plantNumber = safeToString(safeGet(row, i++));
        dto.plantationStatus = safeToBoolean(safeGet(row, i++));
        dto.plantationCreatedAt = safeToLocalDateTime(safeGet(row, i++));
        dto.plantationUpdatedAt = safeToLocalDateTime(safeGet(row, i++));
        dto.plantationIsSynced = safeToBoolean(safeGet(row, i++));
        dto.plantationIsDeleted = safeToBoolean(safeGet(row, i++));
        dto.idSpecies = safeToIntegerObj(safeGet(row, i++));
        dto.speciesName = safeToString(safeGet(row, i++));
        dto.idSubPlot = safeToIntegerObj(safeGet(row, i++));
        dto.idReforestation = safeToIntegerObj(safeGet(row, i++));
        dto.subPlotName = safeToString(safeGet(row, i++));
        dto.plantationBlockName = safeToString(safeGet(row, i++));
        dto.idPlantationBlock = safeToIntegerObj(safeGet(row, i++));

        return dto;
    }

    public static List<PlantationViewDTO> toPlantationViewDTOList(
            List<Object[]> rows
    ) {
        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }

        return rows.stream()
                .map(PlantationMapper::toPlantationViewDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // ============================================================
    // SAFE CONVERSION
    // ============================================================

    private static Object safeGet(Object[] row, int index) {
        if (row == null || index < 0 || index >= row.length) {
            return null;
        }

        return row[index];
    }

    private static Integer safeToIntegerObj(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof Number number) {
            return number.intValue();
        }

        try {
            return Integer.valueOf(value.toString().trim());
        } catch (Exception e) {
            return null;
        }
    }

    private static BigDecimal safeToBigDecimal(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal bigDecimal) {
            return bigDecimal;
        }

        if (value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }

        try {
            return new BigDecimal(value.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private static String safeToString(Object value) {
        return value == null ? null : value.toString();
    }

    private static Boolean safeToBoolean(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof Boolean booleanValue) {
            return booleanValue;
        }

        String valueString = value.toString()
                .trim()
                .toLowerCase();

        return switch (valueString) {
            case "true", "t", "1" -> true;
            case "false", "f", "0" -> false;
            default -> null;
        };
    }

    private static UUID safeToUUID(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof UUID uuid) {
            return uuid;
        }

        try {
            return UUID.fromString(value.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private static LocalDate safeToLocalDate(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof Date date) {
            return date.toLocalDate();
        }

        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime().toLocalDate();
        }

        try {
            return LocalDate.parse(value.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private static LocalDateTime safeToLocalDateTime(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime();
        }

        if (value instanceof Date date) {
            return date.toLocalDate().atStartOfDay();
        }

        try {
            return LocalDateTime.parse(
                    value.toString().replace(' ', 'T')
            );
        } catch (Exception e) {
            return null;
        }
    }
}