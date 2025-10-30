package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.model.Entrenador;
import com.uceva.fitmanager.repository.entrenadorRepository;
import com.uceva.fitmanager.service.IEntrenadorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorServiceImpl implements IEntrenadorService {

    private final entrenadorRepository entrenadorRepository;

    public EntrenadorServiceImpl(entrenadorRepository entrenadorRepository) {
        this.entrenadorRepository = entrenadorRepository;
    }

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
                    e.setContrasena(entrenadorActualizado.getContrasena());
                    e.setEspecialidad(entrenadorActualizado.getEspecialidad());
                    return entrenadorRepository.save(e);
                })
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado con id " + id));
    }

    @Override
    public Optional<Entrenador> findByEmailAndPassword(String email, String password) {
        return entrenadorRepository.findByCorreoAndContrasena(email, password);
    }
}
