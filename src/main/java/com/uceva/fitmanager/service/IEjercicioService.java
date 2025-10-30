package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Ejercicio;

import java.util.List;
import java.util.Optional;

public interface IEjercicioService {
    List<Ejercicio> findAll();
    Optional<Ejercicio> findById(Long id);
    Ejercicio save(Ejercicio ejercicio);
    void delete(Long id);
    Ejercicio update(Long id, Ejercicio ejercicioActualizado);
    List<Ejercicio> findByGrupoMuscular(String categoria);
}

