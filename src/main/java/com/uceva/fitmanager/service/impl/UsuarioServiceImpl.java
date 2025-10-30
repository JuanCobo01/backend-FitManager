package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.model.Usuario;
import com.uceva.fitmanager.repository.usuarioRepository;
import com.uceva.fitmanager.service.IUsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final usuarioRepository usuarioRepository;

    public UsuarioServiceImpl(usuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario update(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(u -> {
                    u.setNombre(usuarioActualizado.getNombre());
                    u.setCorreo(usuarioActualizado.getCorreo());
                    u.setContrasena(usuarioActualizado.getContrasena());
                    u.setEdad(usuarioActualizado.getEdad());
                    u.setAltura(usuarioActualizado.getAltura());
                    u.setPesoInicial(usuarioActualizado.getPesoInicial());
                    u.setFechaRegistro(usuarioActualizado.getFechaRegistro());
                    return usuarioRepository.save(u);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + id));
    }

    @Override
    public Optional<Usuario> findByEmailAndPassword(String email, String password) {
        return usuarioRepository.findByCorreoAndContrasena(email, password);
    }
}

