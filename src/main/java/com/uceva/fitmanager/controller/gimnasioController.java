package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.model.Gimnasio;
import com.uceva.fitmanager.service.IGimnasioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gimnasios") // Removido /api ya que se maneja en WebConfig
@CrossOrigin(origins = "*")
public class gimnasioController {

    @Autowired
    private IGimnasioService gimnasioService;

    @GetMapping
    public List<Gimnasio> getAllGimnasios() {
        return gimnasioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gimnasio> getGimnasioById(@PathVariable Long id) {
        return gimnasioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Gimnasio createGimnasio(@RequestBody Gimnasio gimnasio) {
        return gimnasioService.save(gimnasio);
    }

    @PutMapping("/actualizar/{id}")
    public Gimnasio updateGimnasio(@PathVariable Long id, @RequestBody Gimnasio gimnasio) {
        return gimnasioService.update(id, gimnasio);
    }

    @DeleteMapping("/borrar/{id}")
    public void deleteGimnasio(@PathVariable Long id) {
        gimnasioService.delete(id);
    }

    @GetMapping("/buscar/nombre")
    public List<Gimnasio> getGimnasiosByNombre(@RequestParam String nombre) {
        return gimnasioService.findByNombre(nombre);
    }

    @GetMapping("/buscar/direccion")
    public List<Gimnasio> getGimnasiosByDireccion(@RequestParam String direccion) {
        return gimnasioService.findByDireccion(direccion);
    }
}
