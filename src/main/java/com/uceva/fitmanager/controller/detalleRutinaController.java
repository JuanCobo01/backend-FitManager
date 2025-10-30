package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.model.DetalleRutina;
import com.uceva.fitmanager.service.IDetalleRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalle-rutina")
@CrossOrigin(origins = "*")
public class detalleRutinaController {

    @Autowired
    private IDetalleRutinaService detalleRutinaService;

    @GetMapping
    public List<DetalleRutina> getAllDetalleRutinas() {
        return detalleRutinaService.findAll();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<DetalleRutina> getDetalleRutinaById(@PathVariable Long id) {
        return detalleRutinaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetalleRutina createDetalleRutina(@RequestBody DetalleRutina detalleRutina) {
        return detalleRutinaService.save(detalleRutina);
    }

    @PutMapping("/actualizar/{id}")
    public DetalleRutina updateDetalleRutina(@PathVariable Long id, @RequestBody DetalleRutina detalleRutina) {
        return detalleRutinaService.update(id, detalleRutina);
    }

    @DeleteMapping("/borrar/{id}")
    public void deleteDetalleRutina(@PathVariable Long id) {
        detalleRutinaService.delete(id);
    }

    // Buscar detalles por rutina
    @GetMapping("/rutina/{rutinaId}")
    public List<DetalleRutina> getDetalleRutinasByRutina(@PathVariable Long rutinaId) {
        return detalleRutinaService.findByRutinaId(rutinaId);
    }

    // Buscar detalles por ejercicio
    @GetMapping("/ejercicio/{ejercicioId}")
    public List<DetalleRutina> getDetalleRutinasByEjercicio(@PathVariable Long ejercicioId) {
        return detalleRutinaService.findByEjercicioId(ejercicioId);
    }
}
