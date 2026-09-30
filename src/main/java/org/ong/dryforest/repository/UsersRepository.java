package org.ong.dryforest.repository;

import org.ong.dryforest.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByPersonEmail(String email);

    Optional<Users> findByUsername(String username);

    @Query("""
            SELECT u.username FROM Users u
            WHERE u.username LIKE CONCAT(:prefix, '%')
            ORDER BY u.username DESC
           """)
    String findLastUsernameByPrefix(@Param("prefix") String prefix);
}