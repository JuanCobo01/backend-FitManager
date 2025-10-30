package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.model.Administrador;
import com.uceva.fitmanager.service.IAdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores") // Removido /api ya que se maneja en WebConfig
@CrossOrigin(origins = "*")
public class administradorController {

    @Autowired
    private IAdministradorService administradorService;

    @GetMapping
    public List<Administrador> getAllAdministradores() {
        return administradorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> getAdministradorById(@PathVariable Long id) {
        return administradorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Administrador createAdministrador(@RequestBody Administrador administrador) {
        return administradorService.save(administrador);
    }

    @PutMapping("/actualizar/{id}")
    public Administrador updateAdministrador(@PathVariable Long id, @RequestBody Administrador administrador) {
        return administradorService.update(id, administrador);
    }

    @DeleteMapping("/borrar/{id}")
    public void deleteAdministrador(@PathVariable Long id) {
        administradorService.delete(id);
    }
}
