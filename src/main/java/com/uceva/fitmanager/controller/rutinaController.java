package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.model.Rutina;
import com.uceva.fitmanager.service.IRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    // Buscar rutinas por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Rutina> getRutinasByUsuario(@PathVariable Long usuarioId) {
        return rutinaService.findByUsuarioId(usuarioId);
    }

    // Buscar rutinas por entrenador
    @GetMapping("/entrenador/{entrenadorId}")
    public List<Rutina> getRutinasByEntrenador(@PathVariable Long entrenadorId) {
        return rutinaService.findByEntrenadorId(entrenadorId);
    }
}
