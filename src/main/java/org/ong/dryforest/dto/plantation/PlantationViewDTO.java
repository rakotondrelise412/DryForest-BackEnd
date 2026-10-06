package org.ong.dryforest.dto.plantation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
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
}