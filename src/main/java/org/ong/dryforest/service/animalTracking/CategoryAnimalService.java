package org.ong.dryforest.service.animalTracking;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.CategoryAnimal;

public interface CategoryAnimalService {

    CategoryAnimal findById(int id);

    List<CategoryAnimal> findAllCategoryAnimalUpdatedSince(LocalDateTime last_sync);

    List<CategoryAnimal> findAll();

    CategoryAnimal createCategoryAnimal(CategoryAnimal categoryAnimal);

    CategoryAnimal updateCategoryAnimal(CategoryAnimal categoryAnimal);

    void deleteCategoryAnimal(CategoryAnimal categoryAnimal);
    
}
