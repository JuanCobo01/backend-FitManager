package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.DetalleRutina;

import java.util.List;
import java.util.Optional;

public interface IDetalleRutinaService {
    List<DetalleRutina> findAll();
    Optional<DetalleRutina> findById(Long id);
    DetalleRutina save(DetalleRutina detalleRutina);
    void delete(Long id);
    DetalleRutina update(Long id, DetalleRutina detalleRutinaActualizada);
    List<DetalleRutina> findByRutinaId(Long rutinaId);
    List<DetalleRutina> findByEjercicioId(Long ejercicioId);
}
