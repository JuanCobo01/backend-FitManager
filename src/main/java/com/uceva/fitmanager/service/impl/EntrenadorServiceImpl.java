package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.exception.ResourceNotFoundException;
import com.uceva.fitmanager.model.Entrenador;
import com.uceva.fitmanager.repository.entrenadorRepository;
import com.uceva.fitmanager.service.IEntrenadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntrenadorServiceImpl implements IEntrenadorService {

    private final entrenadorRepository entrenadorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Entrenador> findAll() {
        return entrenadorRepository.findAll();
    }

    @Override
    public Optional<Entrenador> findById(Long id) {
        return entrenadorRepository.findById(id);
    }

    @Override
    public Entrenador save(Entrenador entrenador) {
        // Encriptar la contraseña antes de guardar
        if (entrenador.getContrasena() != null && !entrenador.getContrasena().isEmpty()) {
            entrenador.setContrasena(passwordEncoder.encode(entrenador.getContrasena()));
        }
        return entrenadorRepository.save(entrenador);
    }

    @Override
    public void delete(Long id) {
        entrenadorRepository.deleteById(id);
    }

    @Override
    public Entrenador update(Long id, Entrenador entrenadorActualizado) {
        return entrenadorRepository.findById(id)
                .map(e -> {
                    e.setNombre(entrenadorActualizado.getNombre());
                    e.setCorreo(entrenadorActualizado.getCorreo());
                    // Solo actualizar contraseña si se proporciona una nueva
                    if (entrenadorActualizado.getContrasena() != null && !entrenadorActualizado.getContrasena().isEmpty()) {
                        e.setContrasena(passwordEncoder.encode(entrenadorActualizado.getContrasena()));
                    }
                    e.setEspecialidad(entrenadorActualizado.getEspecialidad());
                    return entrenadorRepository.save(e);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Entrenador", "id", id));
    }

    @Override
    public Optional<Entrenador> findByEmailAndPassword(String email, String password) {
        // Buscar entrenador por email
        Optional<Entrenador> entrenadorOpt = entrenadorRepository.findByCorreo(email);
        
        // Verificar si existe y si la contraseña coincide usando BCrypt
        if (entrenadorOpt.isPresent() && passwordEncoder.matches(password, entrenadorOpt.get().getContrasena())) {
            return entrenadorOpt;
        }
        
        return Optional.empty();
    }
}
