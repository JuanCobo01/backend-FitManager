package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.exception.ResourceNotFoundException;
import com.uceva.fitmanager.model.Administrador;
import com.uceva.fitmanager.repository.administradorRepository;
import com.uceva.fitmanager.service.IAdministradorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministradorServiceImpl implements IAdministradorService {

    private final administradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    @Override
    public Optional<Administrador> findById(Long id) {
        return administradorRepository.findById(id);
    }

    @Override
    public Administrador save(Administrador administrador) {
        // Encriptar la contraseña antes de guardar
        if (administrador.getContrasena() != null && !administrador.getContrasena().isEmpty()) {
            administrador.setContrasena(passwordEncoder.encode(administrador.getContrasena()));
        }
        return administradorRepository.save(administrador);
    }

    @Override
    public void delete(Long id) {
        administradorRepository.deleteById(id);
    }

    @Override
    public Administrador update(Long id, Administrador administradorActualizado) {
        return administradorRepository.findById(id)
                .map(a -> {
                    a.setNombre(administradorActualizado.getNombre());
                    a.setCorreo(administradorActualizado.getCorreo());
                    // Solo actualizar contraseña si se proporciona una nueva
                    if (administradorActualizado.getContrasena() != null && !administradorActualizado.getContrasena().isEmpty()) {
                        a.setContrasena(passwordEncoder.encode(administradorActualizado.getContrasena()));
                    }
                    a.setRol(administradorActualizado.getRol());
                    return administradorRepository.save(a);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Administrador", "id", id));
    }

    @Override
    public Optional<Administrador> findByEmailAndPassword(String email, String password) {
        // Buscar administrador por email
        Optional<Administrador> administradorOpt = administradorRepository.findByCorreo(email);
        
        // Verificar si existe y si la contraseña coincide usando BCrypt
        if (administradorOpt.isPresent() && passwordEncoder.matches(password, administradorOpt.get().getContrasena())) {
            return administradorOpt;
        }
        
        return Optional.empty();
    }
}
