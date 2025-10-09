package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Ejercicio;
import com.uceva.fitmanager.repository.ejercicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ejercicioService {

    private final ejercicioRepository ejercicioRepository;

    public ejercicioService(ejercicioRepository ejercicioRepository) {
        this.ejercicioRepository = ejercicioRepository;
    }

    public List<Ejercicio> findAll() {
        return ejercicioRepository.findAll();
    }

    public Optional<Ejercicio> findById(Long id) {
        return ejercicioRepository.findById(id);
    }

    public Ejercicio save(Ejercicio ejercicio) {
        return ejercicioRepository.save(ejercicio);
    }

    public void delete(Long id) {
        ejercicioRepository.deleteById(id);
    }

    public Ejercicio update(Long id, Ejercicio ejercicioActualizado) {
        return ejercicioRepository.findById(id)
                .map(e -> {
                    e.setDescripcion(ejercicioActualizado.getDescripcion());
                    e.setDetalles(ejercicioActualizado.getDetalles());
                    e.setGrupoMuscular(ejercicioActualizado.getGrupoMuscular());
                    e.setIdEjercicio(ejercicioActualizado.getIdEjercicio());
                    e.setNombreEjercicio(ejercicioActualizado.getNombreEjercicio());

                    return ejercicioRepository.save(e);
                })
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado con id " + id));
    }

    public List<Ejercicio> findByGrupoMuscular(String categoria) {
        return ejercicioRepository.findByGrupoMuscular(categoria);
    }
}
