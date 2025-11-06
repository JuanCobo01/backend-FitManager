package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.exception.BadRequestException;
import com.uceva.fitmanager.exception.DuplicateResourceException;
import com.uceva.fitmanager.exception.UnauthorizedException;
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

import java.time.LocalDate;
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
        validateLoginRequest(loginRequest);
        return authenticateUser(loginRequest, "USUARIO");
    }

    @PostMapping("/entrenador/login")
    public ResponseEntity<?> loginEntrenador(@RequestBody LoginRequest loginRequest) {
        validateLoginRequest(loginRequest);
        return authenticateUser(loginRequest, "ENTRENADOR");
    }

    @PostMapping("/administrador/login")
    public ResponseEntity<?> loginAdministrador(@RequestBody LoginRequest loginRequest) {
        validateLoginRequest(loginRequest);
        return authenticateUser(loginRequest, "ADMINISTRADOR");
    }

    // Endpoints de Registro
    @PostMapping("/usuario/register")
    public ResponseEntity<?> registerUsuario(@RequestBody RegisterUsuarioRequest registerRequest) {
        try {
            validateRegisterUsuarioRequest(registerRequest);
            
            // Verificar si el email ya existe
            if (usuarioService.findByEmailAndPassword(registerRequest.getEmail(), "").isPresent()) {
                throw new DuplicateResourceException("Usuario", "email", registerRequest.getEmail());
            }
            
            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(registerRequest.getNombre());
            nuevoUsuario.setCorreo(registerRequest.getEmail());
            nuevoUsuario.setContrasena(registerRequest.getPassword());
            nuevoUsuario.setEdad(registerRequest.getEdad());
            nuevoUsuario.setAltura(registerRequest.getAltura());
            nuevoUsuario.setPesoInicial(registerRequest.getPesoInicial());
            nuevoUsuario.setFechaRegistro(LocalDate.now());
            
            // Guardar usuario (la contraseña se encriptará automáticamente en el servicio)
            Usuario usuarioGuardado = usuarioService.save(nuevoUsuario);
            
            // Generar token
            String token = jwtService.generateToken(
                    usuarioGuardado.getCorreo(),
                    "USUARIO",
                    usuarioGuardado.getIdUsuario()
            );
            
            AuthResponse response = AuthResponse.builder()
                    .token(token)
                    .userType("USUARIO")
                    .userId(usuarioGuardado.getIdUsuario())
                    .userName(usuarioGuardado.getNombre())
                    .email(usuarioGuardado.getCorreo())
                    .message("Usuario registrado exitosamente")
                    .build();
            
            return ResponseEntity.ok(response);
        } catch (DuplicateResourceException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al registrar usuario: " + e.getMessage());
        }
    }

    @PostMapping("/entrenador/register")
    public ResponseEntity<?> registerEntrenador(@RequestBody RegisterEntrenadorRequest registerRequest) {
        try {
            validateRegisterEntrenadorRequest(registerRequest);
            
            // Crear nuevo entrenador
            Entrenador nuevoEntrenador = new Entrenador();
            nuevoEntrenador.setNombre(registerRequest.getNombre());
            nuevoEntrenador.setCorreo(registerRequest.getEmail());
            nuevoEntrenador.setContrasena(registerRequest.getPassword());
            nuevoEntrenador.setEspecialidad(registerRequest.getEspecialidad());
            
            Entrenador entrenadorGuardado = entrenadorService.save(nuevoEntrenador);
            
            String token = jwtService.generateToken(
                    entrenadorGuardado.getCorreo(),
                    "ENTRENADOR",
                    entrenadorGuardado.getIdEntrenador()
            );
            
            AuthResponse response = AuthResponse.builder()
                    .token(token)
                    .userType("ENTRENADOR")
                    .userId(entrenadorGuardado.getIdEntrenador())
                    .userName(entrenadorGuardado.getNombre())
                    .email(entrenadorGuardado.getCorreo())
                    .especialidad(entrenadorGuardado.getEspecialidad())
                    .message("Entrenador registrado exitosamente")
                    .build();
            
            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al registrar entrenador: " + e.getMessage());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String authHeader,
                                          @RequestBody ChangePasswordRequest request) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Token no proporcionado");
            }
            
            String token = authHeader.substring(7);
            if (!jwtService.validateToken(token)) {
                throw new UnauthorizedException("Token inválido o expirado");
            }
            
            String email = jwtService.extractEmail(token);
            String userType = jwtService.extractUserType(token);
            
            validateChangePasswordRequest(request);
            
            // Cambiar contraseña según el tipo de usuario
            switch (userType.toUpperCase()) {
                case "USUARIO":
                    Optional<Usuario> usuarioOpt = usuarioService.findByEmailAndPassword(email, request.getCurrentPassword());
                    if (usuarioOpt.isEmpty()) {
                        throw new UnauthorizedException("Contraseña actual incorrecta");
                    }
                    Usuario usuario = usuarioOpt.get();
                    usuario.setContrasena(request.getNewPassword());
                    usuarioService.update(usuario.getIdUsuario(), usuario);
                    break;
                    
                case "ENTRENADOR":
                    Optional<Entrenador> entrenadorOpt = entrenadorService.findByEmailAndPassword(email, request.getCurrentPassword());
                    if (entrenadorOpt.isEmpty()) {
                        throw new UnauthorizedException("Contraseña actual incorrecta");
                    }
                    Entrenador entrenador = entrenadorOpt.get();
                    entrenador.setContrasena(request.getNewPassword());
                    entrenadorService.update(entrenador.getIdEntrenador(), entrenador);
                    break;
                    
                case "ADMINISTRADOR":
                    Optional<Administrador> adminOpt = administradorService.findByEmailAndPassword(email, request.getCurrentPassword());
                    if (adminOpt.isEmpty()) {
                        throw new UnauthorizedException("Contraseña actual incorrecta");
                    }
                    Administrador admin = adminOpt.get();
                    admin.setContrasena(request.getNewPassword());
                    administradorService.update(admin.getIdAdmin(), admin);
                    break;
                    
                default:
                    throw new BadRequestException("Tipo de usuario inválido");
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Contraseña cambiada exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (UnauthorizedException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al cambiar contraseña: " + e.getMessage());
        }
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
                    throw new BadRequestException("Tipo de usuario inválido");
            }
        } catch (UnauthorizedException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error de autenticación: " + e.getMessage());
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

        throw new UnauthorizedException("Credenciales inválidas");
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

        throw new UnauthorizedException("Credenciales inválidas");
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

        throw new UnauthorizedException("Credenciales inválidas");
    }

    // Métodos de validación
    private void validateLoginRequest(LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new BadRequestException("El email es requerido");
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new BadRequestException("La contraseña es requerida");
        }
        if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BadRequestException("Formato de email inválido");
        }
    }

    private void validateRegisterUsuarioRequest(RegisterUsuarioRequest request) {
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre es requerido");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new BadRequestException("El email es requerido");
        }
        if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BadRequestException("Formato de email inválido");
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new BadRequestException("La contraseña debe tener al menos 6 caracteres");
        }
        if (request.getEdad() <= 0 || request.getEdad() > 120) {
            throw new BadRequestException("La edad debe estar entre 1 y 120 años");
        }
        if (request.getAltura() <= 0 || request.getAltura() > 3.0) {
            throw new BadRequestException("La altura debe ser un valor válido");
        }
        if (request.getPesoInicial() <= 0 || request.getPesoInicial() > 500) {
            throw new BadRequestException("El peso debe ser un valor válido");
        }
    }

    private void validateRegisterEntrenadorRequest(RegisterEntrenadorRequest request) {
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre es requerido");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new BadRequestException("El email es requerido");
        }
        if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BadRequestException("Formato de email inválido");
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new BadRequestException("La contraseña debe tener al menos 6 caracteres");
        }
        if (request.getEspecialidad() == null || request.getEspecialidad().trim().isEmpty()) {
            throw new BadRequestException("La especialidad es requerida");
        }
    }

    private void validateChangePasswordRequest(ChangePasswordRequest request) {
        if (request.getCurrentPassword() == null || request.getCurrentPassword().trim().isEmpty()) {
            throw new BadRequestException("La contraseña actual es requerida");
        }
        if (request.getNewPassword() == null || request.getNewPassword().length() < 6) {
            throw new BadRequestException("La nueva contraseña debe tener al menos 6 caracteres");
        }
        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            throw new BadRequestException("La nueva contraseña debe ser diferente a la actual");
        }
    }

    // DTOs internos
    @lombok.Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @lombok.Data
    public static class RegisterUsuarioRequest {
        private String nombre;
        private String email;
        private String password;
        private int edad;
        private double altura;
        private double pesoInicial;
    }

    @lombok.Data
    public static class RegisterEntrenadorRequest {
        private String nombre;
        private String email;
        private String password;
        private String especialidad;
    }

    @lombok.Data
    public static class ChangePasswordRequest {
        private String currentPassword;
        private String newPassword;
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
