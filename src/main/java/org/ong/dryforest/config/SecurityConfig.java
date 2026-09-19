package org.ong.dryforest.config;

import org.ong.dryforest.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * Encodeur BCrypt utilisé pour les mots de passe.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Gestionnaire d'authentification.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    /**
     * Configuration de Spring Security.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http

                // Active la configuration CORS définie dans CorsConfig
                .cors(cors -> {})

                // Désactive CSRF car l'API utilise JWT
                .csrf(csrf -> csrf.disable())

                // API REST sans session serveur
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // Gestion des autorisations
                .authorizeHttpRequests(auth -> auth

                        // Autorise les requêtes preflight CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        // Autorise les endpoints d'authentification
                        .requestMatchers("/auth/**")
                        .permitAll()

                        // Toutes les autres routes nécessitent un JWT
                        .anyRequest()
                        .authenticated()
                )

                // Ajoute le filtre JWT
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}