package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.model.Rutina;
import com.uceva.fitmanager.model.dto.EjercicioDetalleDTO;
import com.uceva.fitmanager.model.dto.RutinaResponseDTO;
import com.uceva.fitmanager.model.dto.RutinaSimpleDTO;
import com.uceva.fitmanager.service.IRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rutinas") // Removido /api ya que se maneja en WebConfig
@CrossOrigin(origins = "*")
public class rutinaController {

    @Autowired
    private IRutinaService rutinaService;

    @GetMapping
    public List<Rutina> getAllRutinas() {
        return rutinaService.findAll();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Rutina> getRutinaById(@PathVariable Long id) {
        return rutinaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============ NUEVOS ENDPOINTS PARA FLUTTER ============
    
    /**
     * GET /rutinas/usuario/{userId}
     * Obtener todas las rutinas de un usuario específico
     * Response: Lista de RutinaSimpleDTO
     */
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<RutinaSimpleDTO>> getRutinasByUsuario(@PathVariable Long userId) {
        List<RutinaSimpleDTO> rutinas = rutinaService.findRutinasByUsuarioId(userId);
        return ResponseEntity.ok(rutinas);
    }
    
    /**
     * GET /rutinas/{rutinaId}
     * Obtener detalle completo de una rutina con sus ejercicios
     * Response: RutinaResponseDTO con ejercicios, instrucciones y tips
     */
    @GetMapping("/{rutinaId}")
    public ResponseEntity<RutinaResponseDTO> getRutinaDetalle(@PathVariable Long rutinaId) {
        try {
            RutinaResponseDTO rutina = rutinaService.findRutinaWithEjerciciosById(rutinaId);
            return ResponseEntity.ok(rutina);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /rutinas/{rutinaId}/ejercicios
     * Obtener solo los ejercicios de una rutina específica
     * Response: Lista de EjercicioDetalleDTO
     */
    @GetMapping("/{rutinaId}/ejercicios")
    public ResponseEntity<List<EjercicioDetalleDTO>> getEjerciciosByRutina(@PathVariable Long rutinaId) {
        try {
            List<EjercicioDetalleDTO> ejercicios = rutinaService.findEjerciciosByRutinaId(rutinaId);
            return ResponseEntity.ok(ejercicios);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ============ ENDPOINTS CRUD ORIGINALES ============

    @PostMapping
    public Rutina createRutina(@RequestBody Rutina rutina) {
        return rutinaService.save(rutina);
    }

    @PutMapping("/actualizar/{id}")
    public Rutina updateRutina(@PathVariable Long id, @RequestBody Rutina rutina) {
        return rutinaService.update(id, rutina);
    }

    @DeleteMapping("/borrar/{id}")
    public void deleteRutina(@PathVariable Long id) {
        rutinaService.delete(id);
    }

    // Buscar rutinas por entrenador
    @GetMapping("/entrenador/{entrenadorId}")
    public List<Rutina> getRutinasByEntrenador(@PathVariable Long entrenadorId) {
        return rutinaService.findByEntrenadorId(entrenadorId);
    }
}
