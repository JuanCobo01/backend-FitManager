package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.exception.BadRequestException;
import com.uceva.fitmanager.exception.ResourceNotFoundException;
import com.uceva.fitmanager.model.Usuario;
import com.uceva.fitmanager.model.dto.UsuarioUpdateDTO;
import com.uceva.fitmanager.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios") // Removido /api ya que se maneja en WebConfig
@CrossOrigin(origins = "*")
public class usuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    // Endpoint con paginación
    @GetMapping("/paginado")
    public Page<Usuario> getAllUsuariosPaginado(Pageable pageable) {
        return usuarioService.findAllPaginated(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'ENTRENADOR', 'USUARIO')")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * PUT /usuarios/{id}
     * Actualizar información del perfil del usuario
     * Para uso desde EditProfilePage en Flutter
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    public ResponseEntity<Usuario> updateUsuarioPerfil(
            @PathVariable Long id, 
            @Valid @RequestBody UsuarioUpdateDTO dto) {
        try {
            Usuario usuario = usuarioService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
            
            // Actualizar campos
            usuario.setNombre(dto.getNombre());
            usuario.setCorreo(dto.getEmail());
            usuario.setEdad(dto.getEdad());
            usuario.setAltura(dto.getAltura()); // Ya viene en metros desde Flutter (cm/100)
            usuario.setPesoActual(dto.getPeso());
            
            Usuario usuarioActualizado = usuarioService.save(usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @PostMapping
    public Usuario createUsuario(@Valid @RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("/actualizar/{id}")
    public Usuario updateUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return usuarioService.update(id,usuario);
    }

    @DeleteMapping("/borrar/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
    }

    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<Usuario> loginUsuario(@RequestBody Usuario loginRequest) {
        return usuarioService.findByEmailAndPassword(loginRequest.getCorreo(), loginRequest.getContrasena())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }





}
