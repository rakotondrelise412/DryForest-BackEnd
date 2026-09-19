package org.ong.dryforest.service.subPlot;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ong.dryforest.dto.subPlot.SubPlotDTO;
import org.ong.dryforest.entity.SubPlot;

public interface SubPlotService {
    List<SubPlot> findAll();
    SubPlot findById(int id_subPlot);
    SubPlot createSubPlot(SubPlot subPlot);
    SubPlot updateSubPlotLocation(SubPlotDTO subPlotDTO) throws Exception;
    SubPlot updateSubPlot(SubPlot subPlot);
    void deleteSubPlot(SubPlot subPlot);
    boolean existsByUuid(UUID uuid);
    SubPlot mapToEntity(Map<String, Object> subPlotMapping);
    SubPlot findByUuid(UUID uuid);
}
