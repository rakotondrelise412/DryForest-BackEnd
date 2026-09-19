package org.ong.dryforest.repository;

import java.util.Optional;

import org.ong.dryforest.entity.Users;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByPersonEmail(String email);
    Optional<Users> findByUsername(String username);

    @Query("""
            SELECT u.username FROM Users u
            WHERE u.username LIKE CONCAT(:prefix, '%')
            ORDER BY u.username DESC limit 1
           """)
    String findLastUsernameByPrefix(@Param("prefix") String prefix);
}
