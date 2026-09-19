package org.ong.dryforest.service.blacklistedToken;

import java.time.LocalDateTime;

import org.ong.dryforest.entity.BlacklistedToken;
import org.ong.dryforest.repository.BlacklistedTokenRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BlacklistedTokenServiceImpl implements BlacklistedTokenService {
    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    public void addToken(String token) {
        if (!isTokenBlacklisted(token)) {
            BlacklistedToken blacklistedToken = new BlacklistedToken();
            blacklistedToken.setToken(token);
            blacklistedToken.setBlacklistedAt(LocalDateTime.now());

            blacklistedTokenRepository.save(blacklistedToken);
        }
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.existsByToken(token);
    }
    
}
