package org.ong.dryforest.controller;

import org.ong.dryforest.dto.auth.PasswordUpdateRequestDTO;
import org.ong.dryforest.dto.user.UserUpdateDTO;
import org.ong.dryforest.dto.user.UsersDTO;
import org.ong.dryforest.entity.Users;
import org.ong.dryforest.mapper.UsersMapper;
import org.ong.dryforest.service.blacklistedToken.BlacklistedTokenService;
import org.ong.dryforest.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlacklistedTokenService blacklistedTokenService;


    @GetMapping
    public ResponseEntity<Page<UsersDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<UsersDTO> result = userService.findAllUsers(pageable)
                .map(UsersMapper::toDTO);

        return ResponseEntity.ok(result);
    }



    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(
            @PathVariable int id) {

        Users user = userService.findUsersById(id);

        return ResponseEntity.ok(
                UsersMapper.toDTO(user)
        );
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsersDTO> getUserByUsername(
            @PathVariable String username) {

        Users user = userService.findUsersByUsername(username);

        return ResponseEntity.ok(
                UsersMapper.toDTO(user)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersDTO> updateUser(
            @PathVariable int id,
            @RequestBody UserUpdateDTO request) {

        Users user = userService.updateUsers(id, request);

        return ResponseEntity.ok(
                UsersMapper.toDTO(user)
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

        Users user = userService.findUsersById(id);

        userService.deleteUsers(user);

        return ResponseEntity.ok(
                "Utilisateur supprimé avec succès"
        );
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(
                    value = "Authorization",
                    required = false
            ) String authHeader) {

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            blacklistedTokenService.addToken(token);

            return ResponseEntity.ok(
                    "Déconnecté avec succès !"
            );
        }

        return ResponseEntity.badRequest()
                .body("Token invalide");
    }
}
