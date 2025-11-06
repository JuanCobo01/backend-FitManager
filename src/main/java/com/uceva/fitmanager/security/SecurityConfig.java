package com.uceva.fitmanager.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Rutas completamente públicas (sin filtro JWT)
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/v1/auth/**").permitAll()
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/error").permitAll()

                // Rutas para administradores
                .requestMatchers("/v1/administradores/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/v1/gimnasios/**").hasAnyRole("ADMINISTRADOR", "ENTRENADOR")

                // Rutas para entrenadores
                .requestMatchers("/v1/entrenadores/**").hasAnyRole("ADMINISTRADOR", "ENTRENADOR")
                .requestMatchers("/v1/rutinas/**").hasAnyRole("ADMINISTRADOR", "ENTRENADOR", "USUARIO")
                .requestMatchers("/v1/ejercicios/**").hasAnyRole("ADMINISTRADOR", "ENTRENADOR")

                // Rutas para usuarios
                .requestMatchers("/v1/usuarios/**").hasAnyRole("ADMINISTRADOR", "ENTRENADOR", "USUARIO")
                .requestMatchers("/v1/progresos/**").hasAnyRole("ADMINISTRADOR", "ENTRENADOR", "USUARIO")
                .requestMatchers("/v1/detalle-rutinas/**").hasAnyRole("ADMINISTRADOR", "ENTRENADOR", "USUARIO")

                // Rutas financieras - solo admin y entrenadores
                .requestMatchers("/v1/pagos/**").hasAnyRole("ADMINISTRADOR", "ENTRENADOR")

                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
