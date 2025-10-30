package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.model.Ejercicio;
import com.uceva.fitmanager.repository.ejercicioRepository;
import com.uceva.fitmanager.service.IEjercicioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EjercicioServiceImpl implements IEjercicioService {

    private final ejercicioRepository ejercicioRepository;

    public EjercicioServiceImpl(ejercicioRepository ejercicioRepository) {
        this.ejercicioRepository = ejercicioRepository;
    }

    @Override
    public List<Ejercicio> findAll() {
        return ejercicioRepository.findAll();
    }

    @Override
    public Optional<Ejercicio> findById(Long id) {
        return ejercicioRepository.findById(id);
    }

    @Override
    public Ejercicio save(Ejercicio ejercicio) {
        return ejercicioRepository.save(ejercicio);
    }

    @Override
    public void delete(Long id) {
        ejercicioRepository.deleteById(id);
    }

    @Override
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

    @Override
    public List<Ejercicio> findByGrupoMuscular(String categoria) {
        return ejercicioRepository.findByGrupoMuscular(categoria);
    }
}
