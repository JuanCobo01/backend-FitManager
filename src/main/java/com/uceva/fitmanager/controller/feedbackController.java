package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.exception.BadRequestException;
import com.uceva.fitmanager.exception.ResourceNotFoundException;
import com.uceva.fitmanager.model.Feedback;
import com.uceva.fitmanager.model.Usuario;
import com.uceva.fitmanager.model.dto.FeedbackDTO;
import com.uceva.fitmanager.repository.feedbackRepository;
import com.uceva.fitmanager.repository.usuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/feedback")
@CrossOrigin(origins = "*")
public class feedbackController {

    @Autowired
    private feedbackRepository feedbackRepository;
    
    @Autowired
    private usuarioRepository usuarioRepository;

    /**
     * POST /feedback
     * Crear nuevo feedback/retroalimentación
     * Para uso desde HelpSupportPage en Flutter
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'ENTRENADOR')")
    public ResponseEntity<?> createFeedback(@Valid @RequestBody FeedbackDTO dto) {
        try {
            // Verificar que el usuario existe
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + dto.getUsuarioId()));
            
            // Crear feedback
            Feedback feedback = new Feedback();
            feedback.setUsuario(usuario);
            feedback.setMensaje(dto.getMensaje());
            feedback.setEstado(Feedback.EstadoFeedback.PENDIENTE);
            
            Feedback feedbackGuardado = feedbackRepository.save(feedback);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Feedback enviado exitosamente");
            response.put("id", feedbackGuardado.getId());
            response.put("estado", feedbackGuardado.getEstado().toString());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al crear feedback: " + e.getMessage());
        }
    }

    /**
     * GET /feedback
     * Obtener todos los feedbacks (solo administradores)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return ResponseEntity.ok(feedbacks);
    }
    
    /**
     * GET /feedback/usuario/{usuarioId}
     * Obtener feedbacks de un usuario específico
     */
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    public ResponseEntity<List<Feedback>> getFeedbackByUsuario(@PathVariable Long usuarioId) {
        List<Feedback> feedbacks = feedbackRepository.findByUsuarioIdUsuarioOrderByFechaDesc(usuarioId);
        return ResponseEntity.ok(feedbacks);
    }
    
    /**
     * GET /feedback/estado/{estado}
     * Obtener feedbacks por estado (solo administradores)
     */
    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Feedback>> getFeedbackByEstado(@PathVariable String estado) {
        try {
            Feedback.EstadoFeedback estadoEnum = Feedback.EstadoFeedback.valueOf(estado.toUpperCase());
            List<Feedback> feedbacks = feedbackRepository.findByEstado(estadoEnum);
            return ResponseEntity.ok(feedbacks);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Estado inválido. Use: PENDIENTE, REVISADO o RESUELTO");
        }
    }
    
    /**
     * PUT /feedback/{id}/estado
     * Actualizar estado del feedback (solo administradores)
     */
    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Feedback> updateEstadoFeedback(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        try {
            Feedback feedback = feedbackRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Feedback no encontrado con id: " + id));
            
            String estadoStr = request.get("estado");
            if (estadoStr == null) {
                throw new BadRequestException("El campo 'estado' es requerido");
            }
            
            Feedback.EstadoFeedback nuevoEstado = Feedback.EstadoFeedback.valueOf(estadoStr.toUpperCase());
            feedback.setEstado(nuevoEstado);
            
            // Si hay respuesta, guardarla
            if (request.containsKey("respuesta")) {
                feedback.setRespuesta(request.get("respuesta"));
                feedback.setFechaRespuesta(java.time.LocalDateTime.now());
            }
            
            Feedback feedbackActualizado = feedbackRepository.save(feedback);
            return ResponseEntity.ok(feedbackActualizado);
            
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Estado inválido. Use: PENDIENTE, REVISADO o RESUELTO");
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar feedback: " + e.getMessage());
        }
    }
    
    /**
     * DELETE /feedback/{id}
     * Eliminar feedback (solo administradores)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id) {
        try {
            if (!feedbackRepository.existsById(id)) {
                throw new ResourceNotFoundException("Feedback no encontrado con id: " + id);
            }
            
            feedbackRepository.deleteById(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Feedback eliminado exitosamente");
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al eliminar feedback: " + e.getMessage());
        }
    }
}
