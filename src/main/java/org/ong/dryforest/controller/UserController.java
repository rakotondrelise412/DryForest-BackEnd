package org.ong.dryforest.controller;

import org.ong.dryforest.service.user.UserService;
import org.ong.dryforest.dto.auth.PasswordUpdateRequestDTO;
import org.ong.dryforest.service.blacklistedToken.BlacklistedTokenService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BlacklistedTokenService blacklistedTokenService;

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordUpdateRequestDTO request) {
        userService.updatePassword(request);

        return ResponseEntity.ok("Mot de passe mis à jour avec succès");
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            blacklistedTokenService.addToken(token);
            return ResponseEntity.ok("Déconnecté avec succès !");
        }
        return ResponseEntity.badRequest().body("Token invalide");
    }
}
