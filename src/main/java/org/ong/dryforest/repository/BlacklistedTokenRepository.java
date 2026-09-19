package org.ong.dryforest.repository;

import java.util.Optional;

import org.ong.dryforest.entity.BlacklistedToken;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
    Optional<BlacklistedToken> findByToken(String token);
    boolean existsByToken(String token);
}
