package org.ong.dryforest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.ong.dryforest.entity.Person;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findAllByRole_Id(int id_role);
    
    List<Person> findAllByIsDeletedFalse();
    
    Optional<Person> findByIdAndIsDeletedFalse(int id);

    @Query("""
            SELECT ps FROM Person ps
            WHERE ps.updatedAt > :last_sync OR ps.isDeleted = true
           """)
    List<Person> findAllUpdatedSince(@Param("last_sync") LocalDateTime last_sync);
}
