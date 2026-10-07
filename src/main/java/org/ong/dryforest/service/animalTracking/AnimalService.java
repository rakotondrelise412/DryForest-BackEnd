package org.ong.dryforest.service.animalTracking;

import org.ong.dryforest.entity.Animal;

import java.time.LocalDateTime;
import java.util.List;


public interface AnimalService {

    List<Animal> findAll();

    Animal findById(int id_animal);

    List<Animal> findAllAnimalUpdatedSince(
            LocalDateTime last_sync
    );

    Animal create(Animal animal);

    Animal updateAnimal(Animal animal);

    void deleteAnimal(Animal animal);
}