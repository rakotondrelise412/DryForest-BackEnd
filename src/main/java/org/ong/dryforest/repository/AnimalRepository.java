package org.ong.dryforest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.ong.dryforest.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    List<Animal> findAllByIsDeletedFalse();

    Optional<Animal> findByIdAndIsDeletedFalse(int id);

    @Query("""
            SELECT a FROM Animal a
            WHERE a.updatedAt > :last_sync OR a.isDeleted = true
            """)
    List<Animal> findAllUpdatedSince(@Param("last_sync") LocalDateTime last_sync);
}
