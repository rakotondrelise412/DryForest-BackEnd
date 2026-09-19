package org.ong.dryforest.service.animalTracking;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.Animal;
import org.ong.dryforest.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;
    
    @Override
    public List<Animal> findAll(){
        return animalRepository.findAllByIsDeletedFalse();
    }

    @Override
    public List<Animal> findAllAnimalUpdatedSince(LocalDateTime last_sync){
        return animalRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public Animal findById(int id_animal){
        return animalRepository.findByIdAndIsDeletedFalse(id_animal).orElseThrow(() -> new RuntimeException("Plantation not found for this id plantation"));
    }

    @Override
    public Animal create(Animal animal){
        try {
            return animalRepository.save(animal);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Animal déjà existant");
        }
    }

    @Override
    public Animal updateAnimal(Animal animal){
        findById(animal.getId());
        return animalRepository.save(animal);
    }

    @Override
    public void deleteAnimal(Animal animal){
        try {
            findById(animal.getId());
            animalRepository.delete(animal);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cette categorie d'animal");
        }
    }

}
