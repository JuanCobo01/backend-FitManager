package com.uceva.fitmanager.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.uceva.fitmanager.exception.UnauthorizedException;
import com.uceva.fitmanager.model.Usuario;
import com.uceva.fitmanager.repository.usuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final usuarioRepository usuarioRepository;

    @Value("${oauth.google.client-id:}")
    private String googleClientId;

    /**
     * Verificar token de Google y extraer información del usuario
     */
    public GoogleUserInfo verifyGoogleToken(String idToken) {
        try {
            // Si no hay client ID configurado, modo desarrollo
            if (googleClientId == null || googleClientId.isEmpty()) {
                System.out.println("⚠️ MODO DESARROLLO: Google Client ID no configurado");
                return extractGoogleInfoFromTokenDev(idToken);
            }

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), 
                    GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken token = verifier.verify(idToken);
            
            if (token != null) {
                Payload payload = token.getPayload();
                
                return GoogleUserInfo.builder()
                        .email(payload.getEmail())
                        .name((String) payload.get("name"))
                        .photoUrl((String) payload.get("picture"))
                        .emailVerified(payload.getEmailVerified())
                        .googleId(payload.getSubject())
                        .build();
            } else {
                throw new UnauthorizedException("Token de Google inválido");
            }
        } catch (Exception e) {
            throw new UnauthorizedException("Error al verificar token de Google: " + e.getMessage());
        }
    }

    /**
     * Modo desarrollo: extraer info básica del token sin verificación completa
     * SOLO PARA DESARROLLO - En producción siempre verificar con Google
     */
    private GoogleUserInfo extractGoogleInfoFromTokenDev(String idToken) {
        System.out.println("🔓 DESARROLLO: Usando modo sin verificación estricta");
        
        try {
            // Decodificar payload del JWT (sin verificar firma)
            String[] parts = idToken.split("\\.");
            if (parts.length < 2) {
                throw new UnauthorizedException("Formato de token inválido");
            }
            
            // Parsear JSON básico (simplificado para desarrollo)
            // En producción, esto NO debe usarse
            return GoogleUserInfo.builder()
                    .email("dev@google.com")
                    .name("Google Dev User")
                    .photoUrl("")
                    .emailVerified(true)
                    .googleId("dev-google-id")
                    .build();
            
        } catch (Exception e) {
            throw new UnauthorizedException("Error en modo desarrollo: " + e.getMessage());
        }
    }

    /**
     * Verificar token de Apple (simplificado)
     * En producción, esto requiere verificación con las claves públicas de Apple
     */
    public AppleUserInfo verifyAppleToken(String identityToken, String authorizationCode) {
        try {
            System.out.println("⚠️ MODO DESARROLLO: Apple Sign In sin verificación completa");
            
            // En producción, aquí se debe:
            // 1. Verificar el identityToken con las claves públicas de Apple
            // 2. Validar el authorizationCode
            // 3. Hacer request a Apple para obtener el refresh token
            
            // Por ahora, modo desarrollo simplificado
            return AppleUserInfo.builder()
                    .email("dev@apple.com")
                    .name("Apple Dev User")
                    .appleId("dev-apple-id")
                    .emailVerified(true)
                    .build();
            
        } catch (Exception e) {
            throw new UnauthorizedException("Error al verificar token de Apple: " + e.getMessage());
        }
    }

    /**
     * Información del usuario de Google
     */
    @lombok.Data
    @lombok.Builder
    public static class GoogleUserInfo {
        private String email;
        private String name;
        private String photoUrl;
        private Boolean emailVerified;
        private String googleId;
    }

    /**
     * Información del usuario de Apple
     */
    @lombok.Data
    @lombok.Builder
    public static class AppleUserInfo {
        private String email;
        private String name;
        private String appleId;
        private Boolean emailVerified;
    }

    /**
     * Encontrar o crear usuario basado en OAuth
     */
    public Usuario findOrCreateUser(String email, String name, String provider) {
        Optional<Usuario> existingUser = usuarioRepository.findByCorreo(email);
        
        if (existingUser.isPresent()) {
            return existingUser.get();
        }
        
        // Crear nuevo usuario
        Usuario newUser = new Usuario();
        newUser.setCorreo(email);
        newUser.setNombre(name != null ? name : "Usuario " + provider);
        // Contraseña especial para usuarios OAuth - no pueden usar login tradicional
        newUser.setContrasena("OAUTH_" + provider + "_" + System.currentTimeMillis());
        newUser.setEdad(25);
        newUser.setAltura(1.70);
        newUser.setPesoInicial(70.0);
        newUser.setFechaRegistro(LocalDate.now());
        
        return usuarioRepository.save(newUser);
    }
}
