package org.ong.dryforest.service.blacklistedToken;

public interface BlacklistedTokenService {
    
    void addToken(String token);

    boolean isTokenBlacklisted(String token);

}
