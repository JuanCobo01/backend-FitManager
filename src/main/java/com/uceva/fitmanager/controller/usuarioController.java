package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.model.Usuario;
import com.uceva.fitmanager.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios") // Removido /api ya que se maneja en WebConfig
@CrossOrigin(origins = "*")
public class usuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    // Endpoint con paginación
    @GetMapping("/paginado")
    public Page<Usuario> getAllUsuariosPaginado(Pageable pageable) {
        return usuarioService.findAllPaginated(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario createUsuario(@Valid @RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("/actualizar/{id}")
    public Usuario updateUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return usuarioService.update(id,usuario);
    }

    @DeleteMapping("/borrar/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
    }

    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<Usuario> loginUsuario(@RequestBody Usuario loginRequest) {
        return usuarioService.findByEmailAndPassword(loginRequest.getCorreo(), loginRequest.getContrasena())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }





}
