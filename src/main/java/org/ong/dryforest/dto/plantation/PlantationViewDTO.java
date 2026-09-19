package org.ong.dryforest.dto.plantation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class PlantationViewDTO {
    public Integer idPlantation;
    public UUID plantationUuid;
    public BigDecimal diameter;
    public BigDecimal height;
    public BigDecimal carbonSequestered;
    public String image;
    public LocalDate datePlantation;
    public String plantNumber;
    public Boolean plantationStatus;
    public LocalDateTime plantationCreatedAt;
    public LocalDateTime plantationUpdatedAt;
    public Boolean plantationIsSynced;
    public Boolean plantationIsDeleted;
    public Integer idSpecies;
    public String speciesName;
    public Integer idSubPlot;
    public Integer idReforestation;
    public String subPlotName;
    public String plantationBlockName;
    public Integer idPlantationBlock;

    public PlantationViewDTO() {}

    
    // // helper util (place this method inside the same class)
    // private static LocalDateTime parseToLocalDateTimeOrNull(Object o) {
    //     if (o == null) return null;
    //     try {
    //         if (o instanceof Timestamp) {
    //             return ((Timestamp) o).toLocalDateTime();
    //         } else if (o instanceof Date) {
    //             return ((Date) o).toLocalDate().atStartOfDay();
    //         } else {
    //             String s = o.toString();
    //             // common DB formats: "yyyy-MM-dd HH:mm:ss[.f]" -> Timestamp.valueOf handles it
    //             try {
    //                 return Timestamp.valueOf(s).toLocalDateTime();
    //             } catch (IllegalArgumentException e) {
    //                 // try ISO with 'T'
    //                 return LocalDateTime.parse(s.replace(' ', 'T'));
    //             }
    //         }
    //     } catch (Exception e) {
    //         // log if needed then return null to avoid throwing
    //         return null;
    //     }
    // }
}
