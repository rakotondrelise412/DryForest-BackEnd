package org.ong.dryforest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.ong.dryforest.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByIsDeletedFalse();

    Optional<Role> findByIdAndIsDeletedFalse(int id);

    @Query("""
            SELECT r FROM Role r
            WHERE r.updatedAt > :last_sync OR r.isDeleted = true
            """)
    List<Role> findAllUpdatedSince(@Param("last_sync") LocalDateTime last_sync);
}
