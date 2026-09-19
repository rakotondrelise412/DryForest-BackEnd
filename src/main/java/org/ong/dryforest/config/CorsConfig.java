package org.ong.dryforest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    /**
     * Configuration CORS pour les différents frontends
     * de l'application Dry Forest.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // Frontends autorisés
        configuration.setAllowedOrigins(List.of(
                "http://localhost:8081",
                "http://localhost:4200"
        ));

        // Méthodes HTTP autorisées
        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "PATCH",
                "OPTIONS"
        ));

        // Headers HTTP autorisés
        configuration.setAllowedHeaders(List.of("*"));

        // Autorise les cookies / credentials
        configuration.setAllowCredentials(true);

        // Applique CORS à toutes les routes
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}
