package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.model.Progreso;
import com.uceva.fitmanager.service.IProgresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/progresos")
@CrossOrigin(origins = "*")
public class progresoController {

    @Autowired
    private IProgresoService progresoService;

    @GetMapping
    public List<Progreso> getAllProgresos() {
        return progresoService.findAll();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Progreso> getProgresoById(@PathVariable Long id) {
        return progresoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Progreso createProgreso(@RequestBody Progreso progreso) {
        return progresoService.save(progreso);
    }

    @PutMapping("/actualizar/{id}")
    public Progreso updateProgreso(@PathVariable Long id, @RequestBody Progreso progreso) {
        return progresoService.update(id, progreso);
    }

    @DeleteMapping("/borrar/{id}")
    public void deleteProgreso(@PathVariable Long id) {
        progresoService.delete(id);
    }

    // Buscar progresos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Progreso> getProgresosByUsuario(@PathVariable Long usuarioId) {
        return progresoService.findByUsuarioId(usuarioId);
    }

    // Buscar progresos por fecha
    @GetMapping("/fecha/{fecha}")
    public List<Progreso> getProgresosByFecha(@PathVariable LocalDate fecha) {
        return progresoService.findByFecha(fecha);
    }

    // Buscar progresos por usuario y fecha
    @GetMapping("/usuario/{usuarioId}/fecha/{fecha}")
    public List<Progreso> getProgresosByUsuarioAndFecha(@PathVariable Long usuarioId, @PathVariable LocalDate fecha) {
        return progresoService.findByUsuarioIdAndFecha(usuarioId, fecha);
    }
}
