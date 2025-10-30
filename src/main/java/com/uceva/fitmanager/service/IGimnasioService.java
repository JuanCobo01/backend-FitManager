package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Gimnasio;

import java.util.List;
import java.util.Optional;

public interface IGimnasioService {
    List<Gimnasio> findAll();
    Optional<Gimnasio> findById(Long id);
    Gimnasio save(Gimnasio gimnasio);
    void delete(Long id);
    Gimnasio update(Long id, Gimnasio gimnasioActualizado);
    List<Gimnasio> findByNombre(String nombre);
    List<Gimnasio> findByDireccion(String direccion);
}

