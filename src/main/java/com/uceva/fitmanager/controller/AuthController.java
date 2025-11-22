package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.exception.BadRequestException;
import com.uceva.fitmanager.exception.DuplicateResourceException;
import com.uceva.fitmanager.exception.ResourceNotFoundException;
import com.uceva.fitmanager.exception.UnauthorizedException;
import com.uceva.fitmanager.model.Administrador;
import com.uceva.fitmanager.model.Entrenador;
import com.uceva.fitmanager.model.PasswordResetToken;
import com.uceva.fitmanager.model.Usuario;
import com.uceva.fitmanager.model.dto.*;
import com.uceva.fitmanager.repository.PasswordResetTokenRepository;
import com.uceva.fitmanager.security.JwtService;
import com.uceva.fitmanager.service.IAdministradorService;
import com.uceva.fitmanager.service.IEntrenadorService;
import com.uceva.fitmanager.service.IUsuarioService;
import com.uceva.fitmanager.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final IUsuarioService usuarioService;
    private final IEntrenadorService entrenadorService;
    private final IAdministradorService administradorService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final OAuthService oAuthService;

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

    /**
     * POST /auth/change-password
     * Cambiar contraseña del usuario autenticado
     * Para uso desde ChangePasswordPage en Flutter
     * Soporta usuarios OAuth (permite establecer contraseña si no tienen una)
     */
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String authHeader,
                                          @Valid @RequestBody ChangePasswordDTO request) {
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
            
            // Validación de que la nueva contraseña sea diferente
            if (request.getCurrentPassword().equals(request.getNewPassword())) {
                throw new BadRequestException("La nueva contraseña debe ser diferente a la actual");
            }
            
            // Cambiar contraseña según el tipo de usuario
            switch (userType.toUpperCase()) {
                case "USUARIO":
                    Usuario usuario = usuarioService.findByCorreo(email)
                        .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
                    
                    // Si es usuario OAuth (contraseña empieza con "OAUTH_"), permitir establecer contraseña
                    boolean isOAuthUser = usuario.getContrasena().startsWith("OAUTH_");
                    
                    if (!isOAuthUser) {
                        // Usuario normal: verificar contraseña actual
                        Optional<Usuario> usuarioOpt = usuarioService.findByEmailAndPassword(email, request.getCurrentPassword());
                        if (usuarioOpt.isEmpty()) {
                            throw new UnauthorizedException("Contraseña actual incorrecta");
                        }
                    }
                    
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

    @PostMapping("/google")
    public ResponseEntity<?> googleAuth(@Valid @RequestBody GoogleAuthDTO request) {
        try {
            // Verify Google token
            OAuthService.GoogleUserInfo googleData = oAuthService.verifyGoogleToken(request.getIdToken());
            
            // Find or create user
            Usuario usuario = oAuthService.findOrCreateUser(
                googleData.getEmail(),
                googleData.getName(),
                "GOOGLE"
            );
            
            // Generate JWT token
            String token = jwtService.generateToken(
                usuario.getCorreo(), 
                "USUARIO", 
                usuario.getIdUsuario()
            );
            
            return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .userType("USUARIO")
                .userId(usuario.getIdUsuario())
                .userName(usuario.getNombre())
                .email(usuario.getCorreo())
                .message("Login con Google exitoso")
                .build());
                
        } catch (Exception e) {
            throw new UnauthorizedException("Token de Google inválido: " + e.getMessage());
        }
    }

    @PostMapping("/apple")
    public ResponseEntity<?> appleAuth(@Valid @RequestBody AppleAuthDTO request) {
        try {
            // Verify Apple token
            OAuthService.AppleUserInfo appleData = oAuthService.verifyAppleToken(
                request.getIdentityToken(),
                request.getAuthorizationCode()
            );
            
            // Find or create user
            Usuario usuario = oAuthService.findOrCreateUser(
                appleData.getEmail(),
                appleData.getName(),
                "APPLE"
            );
            
            // Generate JWT token
            String token = jwtService.generateToken(
                usuario.getCorreo(), 
                "USUARIO", 
                usuario.getIdUsuario()
            );
            
            return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .userType("USUARIO")
                .userId(usuario.getIdUsuario())
                .userName(usuario.getNombre())
                .email(usuario.getCorreo())
                .message("Login con Apple exitoso")
                .build());
                
        } catch (Exception e) {
            throw new UnauthorizedException("Token de Apple inválido: " + e.getMessage());
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

    /**
     * POST /auth/verify-token
     * Verificar si un token JWT es válido
     * Endpoint para el frontend validar tokens antes de hacer requests
     */
    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.ok(TokenVerificationResponse.builder()
                        .valid(false)
                        .message("Token no proporcionado")
                        .build());
            }

            String token = authHeader.substring(7);
            
            if (jwtService.validateToken(token)) {
                // Token válido, extraer información
                String email = jwtService.extractEmail(token);
                String userType = jwtService.extractUserType(token);
                Long userId = jwtService.extractUserId(token);
                
                return ResponseEntity.ok(TokenVerificationResponse.builder()
                        .valid(true)
                        .userId(userId)
                        .userType(userType)
                        .email(email)
                        .build());
            } else {
                // Token inválido o expirado
                return ResponseEntity.ok(TokenVerificationResponse.builder()
                        .valid(false)
                        .message("Token inválido o expirado")
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.ok(TokenVerificationResponse.builder()
                    .valid(false)
                    .message("Error al verificar token: " + e.getMessage())
                    .build());
        }
    }

    /**
     * POST /auth/forgot-password
     * Solicitar código de recuperación de contraseña
     * Genera código de 6 dígitos y lo envía por email (simulado por ahora)
     */
    @PostMapping("/forgot-password")
    @Transactional
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDTO request) {
        try {
            String email = request.getEmail();
            
            // Verificar si el email existe en alguna tabla
            boolean emailExists = usuarioService.findByEmailAndPassword(email, "").isPresent() ||
                                 entrenadorService.findByEmailAndPassword(email, "").isPresent() ||
                                 administradorService.findByEmailAndPassword(email, "").isPresent();
            
            if (!emailExists) {
                throw new ResourceNotFoundException("No se encontró ninguna cuenta con ese email");
            }
            
            // Invalidar tokens anteriores del mismo email
            passwordResetTokenRepository.deleteByEmail(email);
            
            // Generar código de 6 dígitos
            String code = generateVerificationCode();
            
            // Crear token con expiración de 15 minutos
            PasswordResetToken token = new PasswordResetToken();
            token.setEmail(email);
            token.setCode(code);
            token.setExpiresAt(LocalDateTime.now().plusMinutes(15));
            token.setUsed(false);
            
            passwordResetTokenRepository.save(token);
            
            // NOTA: En producción, implementar envío de email con servicio SMTP
            // Por ahora, en desarrollo, retornamos el código en la respuesta
            // En producción, esto debe removerse y solo enviar el email
            System.out.println("=== CÓDIGO DE RECUPERACIÓN ===");
            System.out.println("Email: " + email);
            System.out.println("Código: " + code);
            System.out.println("Expira: " + token.getExpiresAt());
            System.out.println("==============================");
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Se ha enviado un código de verificación a tu correo");
            response.put("expiresIn", "15 minutos");
            // Solo para desarrollo - REMOVER EN PRODUCCIÓN
            response.put("code", code); // SOLO PARA PRUEBAS
            
            return ResponseEntity.ok(response);
            
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al solicitar recuperación de contraseña: " + e.getMessage());
        }
    }

    /**
     * POST /auth/reset-password
     * Restablecer contraseña usando código de verificación
     */
    @PostMapping("/reset-password")
    @Transactional
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO request) {
        try {
            // Buscar token válido
            PasswordResetToken token = passwordResetTokenRepository
                    .findByEmailAndCodeAndUsedFalse(request.getEmail(), request.getCode())
                    .orElseThrow(() -> new BadRequestException("Código inválido o ya utilizado"));
            
            // Verificar si expiró
            if (token.isExpired()) {
                throw new BadRequestException("El código ha expirado. Solicita uno nuevo");
            }
            
            // Cambiar contraseña según el tipo de usuario
            Optional<Usuario> usuarioOpt = usuarioService.findByEmailAndPassword(request.getEmail(), "");
            Optional<Entrenador> entrenadorOpt = entrenadorService.findByEmailAndPassword(request.getEmail(), "");
            Optional<Administrador> administradorOpt = administradorService.findByEmailAndPassword(request.getEmail(), "");
            
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                usuario.setContrasena(request.getNewPassword());
                usuarioService.update(usuario.getIdUsuario(), usuario);
            } else if (entrenadorOpt.isPresent()) {
                Entrenador entrenador = entrenadorOpt.get();
                entrenador.setContrasena(request.getNewPassword());
                entrenadorService.update(entrenador.getIdEntrenador(), entrenador);
            } else if (administradorOpt.isPresent()) {
                Administrador administrador = administradorOpt.get();
                administrador.setContrasena(request.getNewPassword());
                administradorService.update(administrador.getIdAdmin(), administrador);
            } else {
                throw new ResourceNotFoundException("No se encontró el usuario");
            }
            
            // Marcar token como usado
            token.setUsed(true);
            passwordResetTokenRepository.save(token);
            
            // Invalidar sesiones activas del usuario
            jwtService.invalidateUserToken(request.getEmail());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Contraseña restablecida exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al restablecer contraseña: " + e.getMessage());
        }
    }

    /**
     * Generar código de verificación de 6 dígitos
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Genera número entre 100000 y 999999
        return String.valueOf(code);
    }

    /**
     * POST /auth/request-password-reset-code
     * Solicitar código de recuperación desde el perfil (usuario autenticado)
     * Alternativa a "Olvidé mi contraseña" cuando el usuario está en ChangePasswordPage
     */
    @PostMapping("/request-password-reset-code")
    @Transactional
    public ResponseEntity<?> requestPasswordResetCode(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Token no proporcionado");
            }
            
            String token = authHeader.substring(7);
            if (!jwtService.validateToken(token)) {
                throw new UnauthorizedException("Token inválido o expirado");
            }
            
            String email = jwtService.extractEmail(token);
            
            // Invalidar tokens anteriores del mismo email
            passwordResetTokenRepository.deleteByEmail(email);
            
            // Generar código de 6 dígitos
            String code = generateVerificationCode();
            
            // Crear token con expiración de 15 minutos
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setEmail(email);
            resetToken.setCode(code);
            resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
            resetToken.setUsed(false);
            
            passwordResetTokenRepository.save(resetToken);
            
            // Log para desarrollo
            System.out.println("=== CÓDIGO DE RECUPERACIÓN (DESDE PERFIL) ===");
            System.out.println("Email: " + email);
            System.out.println("Código: " + code);
            System.out.println("Expira: " + resetToken.getExpiresAt());
            System.out.println("===========================================");
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Se ha enviado un código de verificación a tu correo");
            response.put("expiresIn", "15 minutos");
            response.put("email", email);
            // Solo para desarrollo - REMOVER EN PRODUCCIÓN
            response.put("code", code); // SOLO PARA PRUEBAS
            
            return ResponseEntity.ok(response);
            
        } catch (UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al solicitar código de recuperación: " + e.getMessage());
        }
    }

    /**
     * POST /auth/reset-password-with-code
     * Restablecer contraseña usando código (desde perfil, sin necesidad de contraseña actual)
     * Alternativa al change-password cuando el usuario olvidó su contraseña actual
     */
    @PostMapping("/reset-password-with-code")
    @Transactional
    public ResponseEntity<?> resetPasswordWithCode(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody ResetPasswordDTO request) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Token no proporcionado");
            }
            
            String token = authHeader.substring(7);
            if (!jwtService.validateToken(token)) {
                throw new UnauthorizedException("Token inválido o expirado");
            }
            
            String authenticatedEmail = jwtService.extractEmail(token);
            
            // Verificar que el email del request coincide con el del token
            if (!authenticatedEmail.equalsIgnoreCase(request.getEmail())) {
                throw new BadRequestException("El email no coincide con el usuario autenticado");
            }
            
            // Buscar token válido
            PasswordResetToken resetToken = passwordResetTokenRepository
                    .findByEmailAndCodeAndUsedFalse(request.getEmail(), request.getCode())
                    .orElseThrow(() -> new BadRequestException("Código inválido o ya utilizado"));
            
            // Verificar si expiró
            if (resetToken.isExpired()) {
                throw new BadRequestException("El código ha expirado. Solicita uno nuevo");
            }
            
            // Cambiar contraseña según el tipo de usuario
            String userType = jwtService.extractUserType(token);
            
            switch (userType.toUpperCase()) {
                case "USUARIO":
                    Optional<Usuario> usuarioOpt = usuarioService.findByEmailAndPassword(request.getEmail(), "");
                    if (usuarioOpt.isPresent()) {
                        Usuario usuario = usuarioOpt.get();
                        usuario.setContrasena(request.getNewPassword());
                        usuarioService.update(usuario.getIdUsuario(), usuario);
                    }
                    break;
                    
                case "ENTRENADOR":
                    Optional<Entrenador> entrenadorOpt = entrenadorService.findByEmailAndPassword(request.getEmail(), "");
                    if (entrenadorOpt.isPresent()) {
                        Entrenador entrenador = entrenadorOpt.get();
                        entrenador.setContrasena(request.getNewPassword());
                        entrenadorService.update(entrenador.getIdEntrenador(), entrenador);
                    }
                    break;
                    
                case "ADMINISTRADOR":
                    Optional<Administrador> administradorOpt = administradorService.findByEmailAndPassword(request.getEmail(), "");
                    if (administradorOpt.isPresent()) {
                        Administrador administrador = administradorOpt.get();
                        administrador.setContrasena(request.getNewPassword());
                        administradorService.update(administrador.getIdAdmin(), administrador);
                    }
                    break;
            }
            
            // Marcar token como usado
            resetToken.setUsed(true);
            passwordResetTokenRepository.save(resetToken);
            
            // Invalidar sesiones activas del usuario
            jwtService.invalidateUserToken(request.getEmail());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Contraseña restablecida exitosamente. Por favor, vuelve a iniciar sesión");
            return ResponseEntity.ok(response);
            
        } catch (UnauthorizedException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al restablecer contraseña: " + e.getMessage());
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

    @lombok.Data
    @lombok.Builder
    public static class TokenVerificationResponse {
        private boolean valid;
        private Long userId;
        private String userType;
        private String email;
        private String message;
    }
}
