package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.exception.ResourceNotFoundException;
import com.uceva.fitmanager.model.Usuario;
import com.uceva.fitmanager.repository.usuarioRepository;
import com.uceva.fitmanager.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final usuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Page<Usuario> findAllPaginated(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        // Encriptar la contraseña antes de guardar
        if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }
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
                    // Solo actualizar contraseña si se proporciona una nueva
                    if (usuarioActualizado.getContrasena() != null && !usuarioActualizado.getContrasena().isEmpty()) {
                        u.setContrasena(passwordEncoder.encode(usuarioActualizado.getContrasena()));
                    }
                    u.setEdad(usuarioActualizado.getEdad());
                    u.setAltura(usuarioActualizado.getAltura());
                    u.setPesoInicial(usuarioActualizado.getPesoInicial());
                    if (usuarioActualizado.getFechaRegistro() != null) {
                        u.setFechaRegistro(usuarioActualizado.getFechaRegistro());
                    }
                    return usuarioRepository.save(u);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
    }

    @Override
    public Optional<Usuario> findByEmailAndPassword(String email, String password) {
        // Buscar usuario por email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(email);
        
        // Verificar si existe y si la contraseña coincide usando BCrypt
        if (usuarioOpt.isPresent() && passwordEncoder.matches(password, usuarioOpt.get().getContrasena())) {
            return usuarioOpt;
        }
        
        return Optional.empty();
    }
}

