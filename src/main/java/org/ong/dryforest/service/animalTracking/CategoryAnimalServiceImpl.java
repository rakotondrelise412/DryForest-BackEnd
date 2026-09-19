package org.ong.dryforest.service.animalTracking;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.CategoryAnimal;
import org.ong.dryforest.repository.CategoryAnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CategoryAnimalServiceImpl implements CategoryAnimalService{
    @Autowired
    private CategoryAnimalRepository categoryAnimalRepository;

    @Override
    public CategoryAnimal findById(int id){
        return categoryAnimalRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new RuntimeException("Category animal introuvable"));
    }

    @Override
    public List<CategoryAnimal> findAll(){
        return categoryAnimalRepository.findAllByIsDeletedFalse();
    }

    @Override
    public List<CategoryAnimal> findAllCategoryAnimalUpdatedSince(LocalDateTime last_sync){
        return categoryAnimalRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public CategoryAnimal createCategoryAnimal(CategoryAnimal categoryAnimal){
        try {
            return categoryAnimalRepository.save(categoryAnimal);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Category d'animal déjà existant");
        }
    }

    @Override
    public CategoryAnimal updateCategoryAnimal(CategoryAnimal categoryAnimal){
        findById(categoryAnimal.getId());
        return categoryAnimalRepository.save(categoryAnimal);
    }

    @Override
    public void deleteCategoryAnimal(CategoryAnimal categoryAnimal){
        try {
            findById(categoryAnimal.getId());
            categoryAnimalRepository.delete(categoryAnimal);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cette categorie d'animal");
        }
    }
}
