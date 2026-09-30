package org.ong.dryforest.service.species;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.species.SpeciesDTO;
import org.ong.dryforest.entity.Species;
import org.ong.dryforest.entity.SpeciesType;
import org.ong.dryforest.mapper.SpeciesMapper;
import org.ong.dryforest.repository.SpeciesRepository;
import org.ong.dryforest.repository.SpeciesTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SpeciesServiceImpl implements SpeciesService {

    private final SpeciesRepository speciesRepository;
    private final SpeciesTypeRepository speciesTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SpeciesDTO> findAllSpecies() {

        return SpeciesMapper.toMobileDTOList(
                speciesRepository.findAllByIsDeletedFalse()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public SpeciesDTO findSpeciesById(int id) {

        Species species =
                speciesRepository.findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Espèce '" + id + "' introuvable"
                                )
                        );

        return SpeciesMapper.toMobileDTO(species);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpeciesDTO> findAllSpeciesByType(
            int id_species_type
    ) {

        return SpeciesMapper.toMobileDTOList(
                speciesRepository
                        .findAllByType_IdAndIsDeletedFalse(
                                id_species_type
                        )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpeciesDTO> findAllSpeciesById(
            List<Integer> ids
    ) {

        return SpeciesMapper.toMobileDTOList(
                speciesRepository.findAllById(ids)
        );
    }

    @Override
    public Species findSpeciesEntityById(int id) {
        return speciesRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException(
                        "Espèce '" + id + "' introuvable"
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpeciesDTO> findAllSpeciesUpdatedSince(
            LocalDateTime last_sync
    ) {

        return SpeciesMapper.toMobileDTOList(
                speciesRepository.findAllUpdatedSince(last_sync)
        );
    }

    @Override
    public SpeciesDTO createSpecies(
            SpeciesDTO dto
    ) {

        SpeciesType speciesType =
                speciesTypeRepository
                        .findByIdAndIsDeletedFalse(
                                dto.getId_species_type()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Type d'espèce '"
                                                + dto.getId_species_type()
                                                + "' introuvable"
                                )
                        );

        Species species =
                SpeciesMapper.toEntity(
                        dto,
                        speciesType
                );

        try {

            Species saved =
                    speciesRepository.save(species);

            return SpeciesMapper.toMobileDTO(saved);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Espèce déjà existante"
            );
        }
    }

    @Override
    public SpeciesDTO updateSpecies(
            int id,
            SpeciesDTO dto
    ) {

        Species existingSpecies =
                speciesRepository
                        .findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Espèce '" + id
                                                + "' introuvable"
                                )
                        );

        SpeciesType speciesType =
                speciesTypeRepository
                        .findByIdAndIsDeletedFalse(
                                dto.getId_species_type()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Type d'espèce '"
                                                + dto.getId_species_type()
                                                + "' introuvable"
                                )
                        );

        SpeciesMapper.updateEntity(
                existingSpecies,
                dto,
                speciesType
        );

        try {

            Species updated =
                    speciesRepository.save(existingSpecies);

            return SpeciesMapper.toMobileDTO(updated);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Espèce déjà existante"
            );
        }
    }

    @Override
    public void deleteSpecies(int id) {

        Species species =
                speciesRepository
                        .findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Espèce '" + id
                                                + "' introuvable"
                                )
                        );

        try {

            speciesRepository.delete(species);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "Impossible de supprimer cette espèce"
            );
        }
    }
}