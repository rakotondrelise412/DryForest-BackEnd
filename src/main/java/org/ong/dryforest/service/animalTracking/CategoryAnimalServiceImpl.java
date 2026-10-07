package org.ong.dryforest.service.animalTracking;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.entity.CategoryAnimal;
import org.ong.dryforest.repository.CategoryAnimalRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryAnimalServiceImpl implements CategoryAnimalService {

    private final CategoryAnimalRepository categoryAnimalRepository;

    @Override
    public CategoryAnimal findById(int id) {
        return categoryAnimalRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category animal introuvable pour l'id : " + id
                        )
                );
    }

    @Override
    public List<CategoryAnimal> findAll() {
        return categoryAnimalRepository.findAllByIsDeletedFalse();
    }

    @Override
    public List<CategoryAnimal> findAllCategoryAnimalUpdatedSince(
            LocalDateTime last_sync
    ) {
        return categoryAnimalRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public CategoryAnimal createCategoryAnimal(
            CategoryAnimal categoryAnimal
    ) {
        try {
            return categoryAnimalRepository.save(categoryAnimal);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Catégorie d'animal déjà existante"
            );
        }
    }

    @Override
    public CategoryAnimal updateCategoryAnimal(
            CategoryAnimal categoryAnimal
    ) {
        CategoryAnimal existing =
                findById(categoryAnimal.getId());

        existing.setName(categoryAnimal.getName());

        return categoryAnimalRepository.save(existing);
    }

    @Override
    public void deleteCategoryAnimal(CategoryAnimal categoryAnimal) {

        CategoryAnimal existing =
                findById(categoryAnimal.getId());

        existing.setDeleted(true);

        categoryAnimalRepository.save(existing);
    }
}
