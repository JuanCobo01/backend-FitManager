package com.uceva.fitmanager.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    /**
     * Obtiene la información del usuario autenticado actualmente
     */
    public UserTokenInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserTokenInfo) {
            return (UserTokenInfo) authentication.getPrincipal();
        }

        throw new RuntimeException("Usuario no autenticado");
    }

    /**
     * Verifica si el usuario actual tiene un rol específico
     */
    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role.toUpperCase()));
        }

        return false;
    }

    /**
     * Verifica si el usuario actual es propietario del recurso
     */
    public boolean isResourceOwner(Long resourceUserId) {
        try {
            UserTokenInfo currentUser = getCurrentUser();
            return currentUser.getUserId().equals(resourceUserId);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica si el usuario actual puede acceder al recurso
     * (es propietario o tiene rol de administrador/entrenador)
     */
    public boolean canAccessResource(Long resourceUserId) {
        return isResourceOwner(resourceUserId) ||
               hasRole("ADMINISTRADOR") ||
               hasRole("ENTRENADOR");
    }
}
