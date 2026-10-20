package org.ong.dryforest.service.subPlot;

import org.ong.dryforest.dto.subPlot.SubPlotDTO;
import org.ong.dryforest.entity.SubPlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface SubPlotService {

    List<SubPlot> findAll();

    Page<SubPlot> findAll(Pageable pageable);

    SubPlot findById(int id);

    SubPlot findByUuid(UUID uuid);

    SubPlot createSubPlot(SubPlot subPlot);

    SubPlot createSubPlot(SubPlotDTO dto);

    void updateSubPlot(SubPlot subPlot);

    SubPlot updateSubPlot(
            int id,
            SubPlotDTO dto
    );

    SubPlot updateSubPlotLocation(
            SubPlotDTO subPlotDTO
    ) throws Exception;

    void deleteSubPlot(SubPlot subPlot);

    void deleteSubPlot(int id);

    boolean existsByUuid(UUID uuid);

    SubPlot mapToEntity(
            Map<String, Object> subPlotMapping
    );
}