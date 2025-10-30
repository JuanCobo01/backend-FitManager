package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Entrenador;

import java.util.List;
import java.util.Optional;

public interface IEntrenadorService {
    List<Entrenador> findAll();
    Optional<Entrenador> findById(Long id);
    Entrenador save(Entrenador entrenador);
    void delete(Long id);
    Entrenador update(Long id, Entrenador entrenadorActualizado);
    Optional<Entrenador> findByEmailAndPassword(String email, String password);
}

