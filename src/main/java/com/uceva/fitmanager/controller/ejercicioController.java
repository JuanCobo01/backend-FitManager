package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.model.Ejercicio;
import com.uceva.fitmanager.service.ejercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ejercicios")
@CrossOrigin(origins = "*")
public class ejercicioController {

    @Autowired
    private ejercicioService ejercicioService;

    @GetMapping
    public List<Ejercicio> getAllEjercicios() {
        return ejercicioService.findAll();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Ejercicio> getEjercicioById(@PathVariable Long id) {
        return ejercicioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ejercicio createEjercicio(@RequestBody Ejercicio ejercicio) {
        return ejercicioService.save(ejercicio);
    }

    @PutMapping("/actualizar/{id}")
    public Ejercicio updateEjercicio(@PathVariable Long id, @RequestBody Ejercicio ejercicio) {
        return ejercicioService.update(id, ejercicio);
    }

    @DeleteMapping("/borrar/{id}")
    public void deleteEjercicio(@PathVariable Long id) {
        ejercicioService.delete(id);
    }

    // Buscar ejercicios por categoría
    @GetMapping("/categoria/{grupoMuscular}")
    public List<Ejercicio> getEjerciciosGrupoMuscular(@PathVariable String grupoMuscular) {
        return ejercicioService.findByGrupoMuscular(grupoMuscular);
    }
}
