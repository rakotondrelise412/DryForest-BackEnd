package org.ong.dryforest.controller;

import org.ong.dryforest.dto.auth.PasswordUpdateRequestDTO;
import org.ong.dryforest.dto.user.UserUpdateDTO;
import org.ong.dryforest.entity.Users;

import org.ong.dryforest.service.blacklistedToken.BlacklistedTokenService;
import org.ong.dryforest.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlacklistedTokenService blacklistedTokenService;


    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {

        return ResponseEntity.ok(
                userService.findAllUsers()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(
            @PathVariable int id) {

        return ResponseEntity.ok(
                userService.findUsersById(id)
        );
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<Users> getUserByUsername(
            @PathVariable String username) {

        return ResponseEntity.ok(
                userService.findUsersByUsername(username)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(
            @PathVariable int id,
            @RequestBody UserUpdateDTO request) {

        return ResponseEntity.ok(
                userService.updateUsers(id, request)
        );
    }


    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(
            @RequestBody PasswordUpdateRequestDTO request) {

        userService.updatePassword(request);

        return ResponseEntity.ok(
                "Mot de passe mis à jour avec succès"
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable int id) {

        Users user =
                userService.findUsersById(id);

        userService.deleteUsers(user);

        return ResponseEntity.ok(
                "Utilisateur supprimé avec succès"
        );
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader("Authorization")
            String authHeader) {

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            String token =
                    authHeader.substring(7);

            blacklistedTokenService.addToken(token);

            return ResponseEntity.ok(
                    "Déconnecté avec succès !"
            );
        }

        return ResponseEntity.badRequest()
                .body("Token invalide");
    }
}