package org.ong.dryforest.service.species;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.species.SpeciesTypeDTO;
import org.ong.dryforest.entity.SpeciesType;
import org.ong.dryforest.mapper.SpeciesTypeMapper;
import org.ong.dryforest.repository.SpeciesTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SpeciesTypeServiceImpl
        implements SpeciesTypeService {

    private final SpeciesTypeRepository speciesTypeRepository;

    // =========================
    // GET ALL
    // =========================
    @Override
    @Transactional(readOnly = true)
    public List<SpeciesTypeDTO> findAllSpeciesTypes() {

        return SpeciesTypeMapper.toDTOList(
                speciesTypeRepository
                        .findAllByIsDeletedFalse()
        );
    }

    // =========================
    // GET BY ID
    // =========================
    @Override
    @Transactional(readOnly = true)
    public SpeciesTypeDTO findSpeciesTypeById(
            int id
    ) {

        SpeciesType speciesType =
                speciesTypeRepository
                        .findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Type d'espèce '"
                                                + id
                                                + "' introuvable"
                                )
                        );

        return SpeciesTypeMapper
                .toMobileDTO(speciesType);
    }

    // =========================
    // UPDATED SINCE
    // =========================
    @Override
    @Transactional(readOnly = true)
    public List<SpeciesTypeDTO> findAllTypesUpdatedSince(
            LocalDateTime last_sync
    ) {

        return SpeciesTypeMapper.toDTOList(
                speciesTypeRepository
                        .findAllUpdatedSince(last_sync)
        );
    }

    // =========================
    // CREATE
    // =========================
    @Override
    public SpeciesTypeDTO createSpeciesType(
            SpeciesTypeDTO dto
    ) {

        SpeciesType speciesType =
                SpeciesTypeMapper.toEntity(dto);

        try {

            SpeciesType saved =
                    speciesTypeRepository.save(
                            speciesType
                    );

            return SpeciesTypeMapper
                    .toMobileDTO(saved);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Type d'espèce déjà existant"
            );
        }
    }

    // =========================
    // UPDATE
    // =========================
    @Override
    public SpeciesTypeDTO updateSpeciesType(
            int id,
            SpeciesTypeDTO dto
    ) {

        SpeciesType existing =
                speciesTypeRepository
                        .findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Type d'espèce '"
                                                + id
                                                + "' introuvable"
                                )
                        );

        SpeciesTypeMapper.updateEntity(
                existing,
                dto
        );

        try {

            SpeciesType updated =
                    speciesTypeRepository.save(
                            existing
                    );

            return SpeciesTypeMapper
                    .toMobileDTO(updated);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Type d'espèce déjà existant"
            );
        }
    }

    // =========================
    // DELETE
    // =========================
    @Override
    public void deleteSpeciesType(int id) {

        SpeciesType existing =
                speciesTypeRepository
                        .findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Type d'espèce '"
                                                + id
                                                + "' introuvable"
                                )
                        );

        try {

            speciesTypeRepository.delete(existing);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "Impossible de supprimer ce type d'espèce"
            );
        }
    }
}