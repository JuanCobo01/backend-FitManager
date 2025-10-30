package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.model.Administrador;
import com.uceva.fitmanager.model.Entrenador;
import com.uceva.fitmanager.model.Usuario;
import com.uceva.fitmanager.security.JwtService;
import com.uceva.fitmanager.service.IAdministradorService;
import com.uceva.fitmanager.service.IEntrenadorService;
import com.uceva.fitmanager.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final IUsuarioService usuarioService;
    private final IEntrenadorService entrenadorService;
    private final IAdministradorService administradorService;

    @PostMapping("/usuario/login")
    public ResponseEntity<?> loginUsuario(@RequestBody LoginRequest loginRequest) {
        return authenticateUser(loginRequest, "USUARIO");
    }

    @PostMapping("/entrenador/login")
    public ResponseEntity<?> loginEntrenador(@RequestBody LoginRequest loginRequest) {
        return authenticateUser(loginRequest, "ENTRENADOR");
    }

    @PostMapping("/administrador/login")
    public ResponseEntity<?> loginAdministrador(@RequestBody LoginRequest loginRequest) {
        return authenticateUser(loginRequest, "ADMINISTRADOR");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = jwtService.extractEmail(token);
                jwtService.invalidateUserToken(email);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Logout exitoso");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.badRequest().body("Token inválido");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al cerrar sesión");
        }
    }

    @PostMapping("/refresh-activity")
    public ResponseEntity<?> refreshActivity(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (jwtService.validateToken(token)) {
                    String email = jwtService.extractEmail(token);
                    jwtService.updateUserActivity(email);

                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Actividad actualizada");
                    return ResponseEntity.ok(response);
                }
            }
            return ResponseEntity.badRequest().body("Token inválido");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar actividad");
        }
    }

    private ResponseEntity<?> authenticateUser(LoginRequest loginRequest, String userType) {
        try {
            switch (userType) {
                case "USUARIO":
                    return authenticateUsuario(loginRequest);
                case "ENTRENADOR":
                    return authenticateEntrenador(loginRequest);
                case "ADMINISTRADOR":
                    return authenticateAdministrador(loginRequest);
                default:
                    return ResponseEntity.badRequest().body("Tipo de usuario inválido");
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error de autenticación");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    private ResponseEntity<?> authenticateUsuario(LoginRequest loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioService.findByEmailAndPassword(
                loginRequest.getEmail(), loginRequest.getPassword()
        );

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String token = jwtService.generateToken(
                    usuario.getCorreo(),
                    "USUARIO",
                    usuario.getIdUsuario()
            );

            AuthResponse response = AuthResponse.builder()
                    .token(token)
                    .userType("USUARIO")
                    .userId(usuario.getIdUsuario())
                    .userName(usuario.getNombre())
                    .email(usuario.getCorreo())
                    .message("Login exitoso")
                    .build();

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body("Credenciales inválidas");
    }

    private ResponseEntity<?> authenticateEntrenador(LoginRequest loginRequest) {
        Optional<Entrenador> entrenadorOpt = entrenadorService.findByEmailAndPassword(
                loginRequest.getEmail(), loginRequest.getPassword()
        );

        if (entrenadorOpt.isPresent()) {
            Entrenador entrenador = entrenadorOpt.get();
            String token = jwtService.generateToken(
                    entrenador.getCorreo(),
                    "ENTRENADOR",
                    entrenador.getIdEntrenador()
            );

            AuthResponse response = AuthResponse.builder()
                    .token(token)
                    .userType("ENTRENADOR")
                    .userId(entrenador.getIdEntrenador())
                    .userName(entrenador.getNombre())
                    .email(entrenador.getCorreo())
                    .especialidad(entrenador.getEspecialidad())
                    .message("Login exitoso")
                    .build();

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body("Credenciales inválidas");
    }

    private ResponseEntity<?> authenticateAdministrador(LoginRequest loginRequest) {
        Optional<Administrador> administradorOpt = administradorService.findByEmailAndPassword(
                loginRequest.getEmail(), loginRequest.getPassword()
        );

        if (administradorOpt.isPresent()) {
            Administrador administrador = administradorOpt.get();
            String token = jwtService.generateToken(
                    administrador.getCorreo(),
                    "ADMINISTRADOR",
                    administrador.getIdAdmin()
            );

            AuthResponse response = AuthResponse.builder()
                    .token(token)
                    .userType("ADMINISTRADOR")
                    .userId(administrador.getIdAdmin())
                    .userName(administrador.getNombre())
                    .email(administrador.getCorreo())
                    .message("Login exitoso")
                    .build();

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body("Credenciales inválidas");
    }

    // DTOs internos
    @lombok.Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @lombok.Data
    @lombok.Builder
    public static class AuthResponse {
        private String token;
        private String userType;
        private Long userId;
        private String userName;
        private String email;
        private String especialidad; // Solo para entrenadores
        private String message;
    }
}
