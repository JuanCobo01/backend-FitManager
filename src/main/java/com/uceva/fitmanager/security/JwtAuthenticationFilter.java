package com.uceva.fitmanager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Verificar si existe el header Authorization
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token
        jwt = authHeader.substring(7);

        try {
            // Validar token y verificar actividad
            if (jwtService.validateToken(jwt)) {
                userEmail = jwtService.extractEmail(jwt);

                // Si el usuario no está autenticado en el contexto de seguridad
                if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Obtener información del usuario desde el token
                    UserTokenInfo userInfo = jwtService.getCurrentUserInfo(jwt);

                    // Crear autoridades basadas en el tipo de usuario
                    List<SimpleGrantedAuthority> authorities = List.of(
                            new SimpleGrantedAuthority("ROLE_" + userInfo.getUserType().toUpperCase())
                    );

                    // Crear token de autenticación
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userInfo, null, authorities
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Establecer autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // En caso de error, limpiar el contexto de seguridad
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // Rutas que no requieren autenticación
        return path.contains("/auth/") ||
               path.contains("/public/") ||
               path.equals("/fitmanager/v1/usuarios/login") ||
               path.equals("/fitmanager/v1/entrenadores/login") ||
               path.equals("/fitmanager/v1/administradores/login");
    }
}
