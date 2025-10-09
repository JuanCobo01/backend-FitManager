package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Entrenador;
import com.uceva.fitmanager.repository.entrenadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class entrenadorService {

    private final entrenadorRepository entrenadorRepository;

    public entrenadorService(entrenadorRepository entrenadorRepository) {
        this.entrenadorRepository = entrenadorRepository;
    }

    public List<Entrenador> findAll() {
        return entrenadorRepository.findAll();
    }

    public Optional<Entrenador> findById(Long id) {
        return entrenadorRepository.findById(id);
    }

    public Entrenador save(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    public void delete(Long id) {
        entrenadorRepository.deleteById(id);
    }

    public Entrenador update(Long id, Entrenador entrenadorActualizado) {
        return entrenadorRepository.findById(id)
                .map(e -> {
                    e.setNombre(entrenadorActualizado.getNombre());
                    e.setCorreo(entrenadorActualizado.getCorreo());
                    e.setContrasena(entrenadorActualizado.getContrasena());
                    e.setEspecialidad(entrenadorActualizado.getEspecialidad());

                    return entrenadorRepository.save(e);
                })
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado con id " + id));
    }

    public Optional<Entrenador> findByEmailAndPassword(String email, String password) {
        return entrenadorRepository.findByCorreoAndContrasena(email, password);
    }
}

