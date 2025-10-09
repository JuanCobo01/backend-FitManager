package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.model.Pago;
import com.uceva.fitmanager.service.pagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class pagoController {

    @Autowired
    private pagoService pagoService;

    @GetMapping
    public List<Pago> getAllPagos() {
        return pagoService.findAll();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long id) {
        return pagoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pago createPago(@RequestBody Pago pago) {
        return pagoService.save(pago);
    }


    // Buscar pagos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Pago> getPagosByUsuario(@PathVariable Long usuarioId) {
        return pagoService.findByUsuarioId(usuarioId);
    }

    // Buscar pagos por entrenador
    @GetMapping("/entrenador/{entrenadorId}")
    public List<Pago> getPagosByEntrenador(@PathVariable Long entrenadorId) {
        return pagoService.findByEntrenadorId(entrenadorId);
    }

    // Buscar pagos por fecha
    @GetMapping("/fecha/{fecha}")
    public List<Pago> getPagosByFecha(@PathVariable LocalDate fecha) {
        return pagoService.findByFecha(fecha);
    }

    // Buscar pagos por estado
    @GetMapping("/estado/{estado}")
    public List<Pago> getPagosByEstado(@PathVariable String estado) {
        return pagoService.findByEstado(estado);
    }
}
