package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.model.Gimnasio;
import com.uceva.fitmanager.repository.gimnasioRepository;
import com.uceva.fitmanager.service.IGimnasioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GimnasioServiceImpl implements IGimnasioService {

    private final gimnasioRepository gimnasioRepository;

    public GimnasioServiceImpl(gimnasioRepository gimnasioRepository) {
        this.gimnasioRepository = gimnasioRepository;
    }

    @Override
    public List<Gimnasio> findAll() {
        return gimnasioRepository.findAll();
    }

    @Override
    public Optional<Gimnasio> findById(Long id) {
        return gimnasioRepository.findById(id);
    }

    @Override
    public Gimnasio save(Gimnasio gimnasio) {
        return gimnasioRepository.save(gimnasio);
    }

    @Override
    public void delete(Long id) {
        gimnasioRepository.deleteById(id);
    }

    @Override
    public Gimnasio update(Long id, Gimnasio gimnasioActualizado) {
        return gimnasioRepository.findById(id)
                .map(g -> {
                    g.setNombre(gimnasioActualizado.getNombre());
                    g.setDireccion(gimnasioActualizado.getDireccion());
                    g.setTelefono(gimnasioActualizado.getTelefono());
                    g.setAdministrador(gimnasioActualizado.getAdministrador());
                    return gimnasioRepository.save(g);
                })
                .orElseThrow(() -> new RuntimeException("Gimnasio no encontrado con id " + id));
    }

    @Override
    public List<Gimnasio> findByNombre(String nombre) {
        return gimnasioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Gimnasio> findByDireccion(String direccion) {
        return gimnasioRepository.findByDireccionContainingIgnoreCase(direccion);
    }
}
