package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Progreso;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IProgresoService {
    List<Progreso> findAll();
    Optional<Progreso> findById(Long id);
    Progreso save(Progreso progreso);
    void delete(Long id);
    Progreso update(Long id, Progreso progresoActualizado);
    List<Progreso> findByUsuarioId(Long usuarioId);
    List<Progreso> findByFecha(LocalDate fecha);
    List<Progreso> findByUsuarioIdAndFecha(Long usuarioId, LocalDate fecha);
}

