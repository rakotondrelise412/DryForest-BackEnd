package org.ong.dryforest.service.gender;

import org.ong.dryforest.dto.gender.GenderDTO;

import java.util.List;

public interface GenderService {

    GenderDTO create(GenderDTO dto);

    List<GenderDTO> findAll();

    GenderDTO findById(Integer id);

    GenderDTO update(Integer id, GenderDTO dto);

    void delete(Integer id);
}


