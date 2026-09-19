package org.ong.dryforest.controller;

import java.time.LocalDateTime;

import org.ong.dryforest.entity.Users;
import org.ong.dryforest.mapper.PersonMapper;
import org.ong.dryforest.security.JwtUtils;
import org.ong.dryforest.dto.auth.LoginResponseMobileDTO;
import org.ong.dryforest.dto.auth.LoginResponseWebDTO;
import org.ong.dryforest.dto.auth.LoginRequestDTO;
import org.ong.dryforest.dto.auth.RegisterRequestDTO;
import org.ong.dryforest.service.user.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/mobilelogin")
    public LoginResponseMobileDTO loginMobile(@RequestBody LoginRequestDTO request) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Users user = userService.findUsersByUsername(request.getUsername());
        LoginResponseMobileDTO response = new LoginResponseMobileDTO();

        String token = jwtUtils.generateToken(authentication.getName());
        LocalDateTime expires_at = jwtUtils.getExpirationDateFromJwt(token);

        response.setId_user(user.getId());
        response.setUsername(user.getUsername());
        response.setId_person(user.getPerson().getId());
        response.setRole(user.getPerson().getRole().getName());
        response.setToken(token);
        response.setExpires_at(expires_at);

        return response;
    }

    @PostMapping("/weblogin")
    public LoginResponseWebDTO loginWeb(@RequestBody LoginRequestDTO request) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Users user = userService.findUsersByUsername(request.getUsername());
        LoginResponseWebDTO response = new LoginResponseWebDTO();

        String token = jwtUtils.generateToken(authentication.getName());
        LocalDateTime expires_at = jwtUtils.getExpirationDateFromJwt(token);

        response.setId_user(user.getId());
        response.setUsername(user.getUsername());
        response.setToken(token);
        response.setExpires_at(expires_at);
        response.setPerson(PersonMapper.toWebDTO(user.getPerson()));

        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
        try {
            Users user = userService.registerUsers(request);
            return ResponseEntity.ok("Utilisateur créé avec succès. Username: " + user.getUsername());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'inscription: " + e.getMessage());
        }
    }

}