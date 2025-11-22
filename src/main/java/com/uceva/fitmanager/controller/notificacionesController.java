package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.exception.ResourceNotFoundException;
import com.uceva.fitmanager.model.PreferenciasNotificaciones;
import com.uceva.fitmanager.model.Usuario;
import com.uceva.fitmanager.model.dto.PreferenciasNotificacionesDTO;
import com.uceva.fitmanager.repository.preferenciasNotificacionesRepository;
import com.uceva.fitmanager.repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuarios/{userId}/notificaciones")
@CrossOrigin(origins = "*")
public class notificacionesController {

    @Autowired
    private preferenciasNotificacionesRepository preferenciasRepository;
    
    @Autowired
    private usuarioRepository usuarioRepository;

    /**
     * GET /usuarios/{userId}/notificaciones
     * Obtener preferencias de notificaciones del usuario
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    public ResponseEntity<PreferenciasNotificacionesDTO> getPreferencias(@PathVariable Long userId) {
        // Buscar o crear preferencias
        PreferenciasNotificaciones preferencias = preferenciasRepository
                .findByUsuarioIdUsuario(userId)
                .orElseGet(() -> crearPreferenciasDefault(userId));
        
        return ResponseEntity.ok(convertToDTO(preferencias));
    }

    /**
     * PUT /usuarios/{userId}/notificaciones
     * Actualizar preferencias de notificaciones
     */
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    public ResponseEntity<PreferenciasNotificacionesDTO> updatePreferencias(
            @PathVariable Long userId,
            @RequestBody PreferenciasNotificacionesDTO dto) {
        
        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + userId));
        
        // Buscar o crear preferencias
        PreferenciasNotificaciones preferencias = preferenciasRepository
                .findByUsuarioIdUsuario(userId)
                .orElse(new PreferenciasNotificaciones());
        
        // Actualizar valores
        preferencias.setUsuario(usuario);
        preferencias.setRecordatoriosEntrenamiento(dto.getRecordatoriosEntrenamiento());
        preferencias.setActualizacionesProgreso(dto.getActualizacionesProgreso());
        preferencias.setNuevasRutinas(dto.getNuevasRutinas());
        preferencias.setLogros(dto.getLogros());
        preferencias.setNotificacionesSistema(dto.getNotificacionesSistema());
        preferencias.setNotificacionesEmail(dto.getNotificacionesEmail());
        
        PreferenciasNotificaciones saved = preferenciasRepository.save(preferencias);
        
        return ResponseEntity.ok(convertToDTO(saved));
    }
    
    // Métodos auxiliares
    
    private PreferenciasNotificaciones crearPreferenciasDefault(Long userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + userId));
        
        PreferenciasNotificaciones preferencias = new PreferenciasNotificaciones();
        preferencias.setUsuario(usuario);
        // Los valores por defecto ya están en la entidad
        
        return preferenciasRepository.save(preferencias);
    }
    
    private PreferenciasNotificacionesDTO convertToDTO(PreferenciasNotificaciones preferencias) {
        return new PreferenciasNotificacionesDTO(
                preferencias.getRecordatoriosEntrenamiento(),
                preferencias.getActualizacionesProgreso(),
                preferencias.getNuevasRutinas(),
                preferencias.getLogros(),
                preferencias.getNotificacionesSistema(),
                preferencias.getNotificacionesEmail()
        );
    }
}
