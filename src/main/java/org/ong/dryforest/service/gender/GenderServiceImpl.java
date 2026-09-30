package org.ong.dryforest.service.gender;


import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.gender.GenderDTO;
import org.ong.dryforest.entity.Gender;
import org.ong.dryforest.repository.GenderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenderServiceImpl implements GenderService {

    private final GenderRepository genderRepository;

    @Override
    public GenderDTO create(GenderDTO dto) {

        Gender gender = new Gender();
        gender.setName(dto.getName());

        Gender saved = genderRepository.save(gender);

        return toDTO(saved);
    }

    @Override
    public List<GenderDTO> findAll() {

        return genderRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public GenderDTO findById(Integer id) {

        Gender gender = genderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Gender introuvable avec l'id : " + id)
                );

        return toDTO(gender);
    }

    @Override
    public GenderDTO update(Integer id, GenderDTO dto) {

        Gender gender = genderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Gender introuvable avec l'id : " + id)
                );

        gender.setName(dto.getName());

        Gender updated = genderRepository.save(gender);

        return toDTO(updated);
    }

    @Override
    public void delete(Integer id) {

        Gender gender = genderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Gender introuvable avec l'id : " + id)
                );

        genderRepository.delete(gender);
    }

    private GenderDTO toDTO(Gender gender) {

        return new GenderDTO(
                gender.getId(),
                gender.getName()
        );
    }
}
