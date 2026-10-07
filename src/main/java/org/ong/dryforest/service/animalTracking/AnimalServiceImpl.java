package org.ong.dryforest.service.animalTracking;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.entity.Animal;
import org.ong.dryforest.repository.AnimalRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    @Override
    public List<Animal> findAll() {
        return animalRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Animal findById(int id_animal) {
        return animalRepository
                .findByIdAndIsDeletedFalse(id_animal)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Animal introuvable pour l'id : "
                                        + id_animal
                        )
                );
    }

    @Override
    public List<Animal> findAllAnimalUpdatedSince(
            LocalDateTime last_sync
    ) {
        return animalRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public Animal create(Animal animal) {

        try {
            return animalRepository.save(animal);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Animal déjà existant"
            );
        }
    }

    @Override
    public Animal updateAnimal(Animal animal) {

        Animal existing =
                findById(animal.getId());

        existing.setName(animal.getName());
        existing.setCategory_animal(
                animal.getCategory_animal()
        );

        return animalRepository.save(existing);
    }

    @Override
    public void deleteAnimal(Animal animal) {

        Animal existing =
                findById(animal.getId());

        existing.setDeleted(true);

        animalRepository.save(existing);
    }
}