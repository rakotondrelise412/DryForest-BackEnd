
package org.ong.dryforest.repository;

import org.ong.dryforest.entity.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer> {

    @Query("""
        SELECT s
        FROM Site s
        WHERE s.isDeleted = false
        """)
    Page<Site> findAllActive(Pageable pageable);

    @Query("""
        SELECT s
        FROM Site s
        WHERE s.id = :id
          AND s.isDeleted = false
        """)
    Optional<Site> findActiveById(@Param("id") int id);
}
