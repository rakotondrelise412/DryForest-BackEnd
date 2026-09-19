package org.ong.dryforest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.ong.dryforest.entity.CategoryAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryAnimalRepository extends JpaRepository<CategoryAnimal, Integer> {
    List<CategoryAnimal> findAllByIsDeletedFalse();

    Optional<CategoryAnimal> findByIdAndIsDeletedFalse(int id);

    @Query("""
            SELECT catA FROM CategoryAnimal catA
            WHERE catA.updatedAt > :last_sync OR catA.isDeleted = true
            """)
    List<CategoryAnimal> findAllUpdatedSince(@Param("last_sync") LocalDateTime last_sync);
}
