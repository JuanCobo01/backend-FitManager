package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.model.Entrenador;
import com.uceva.fitmanager.service.IEntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entrenadores") // Removido /api ya que se maneja en WebConfig
@CrossOrigin(origins = "*")
public class entrenadorController {

    @Autowired
    private IEntrenadorService entrenadorService;

    @GetMapping
    public List<Entrenador> getAllEntrenadores() {
        return entrenadorService.findAll();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Entrenador> getEntrenadorById(@PathVariable Long id) {
        return entrenadorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Entrenador createEntrenador(@RequestBody Entrenador entrenador) {
        return entrenadorService.save(entrenador);
    }

    @PutMapping("/actualizar/{id}")
    public Entrenador updateEntrenador(@PathVariable Long id, @RequestBody Entrenador entrenador) {
        return entrenadorService.update(id, entrenador);
    }

    @DeleteMapping("/borrar/{id}")
    public void deleteEntrenador(@PathVariable Long id) {
        entrenadorService.delete(id);
    }

    // Login de entrenador
    @PostMapping("/login")
    public ResponseEntity<Entrenador> loginEntrenador(@RequestBody Entrenador loginRequest) {
        return entrenadorService.findByEmailAndPassword(loginRequest.getCorreo(), loginRequest.getContrasena())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
