package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Rutina;

import java.util.List;
import java.util.Optional;

public interface IRutinaService {
    List<Rutina> findAll();
    Optional<Rutina> findById(Long id);
    Rutina save(Rutina rutina);
    void delete(Long id);
    Rutina update(Long id, Rutina rutinaActualizada);
    List<Rutina> findByUsuarioId(Long usuarioId);
    List<Rutina> findByEntrenadorId(Long entrenadorId);
}

