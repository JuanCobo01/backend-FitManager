package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Pago;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPagoService {
    List<Pago> findAll();
    Optional<Pago> findById(Long id);
    Pago save(Pago pago);
    void delete(Long id);
    Pago update(Long id, Pago pagoActualizado);
    List<Pago> findByUsuarioId(Long usuarioId);
    List<Pago> findByEntrenadorId(Long entrenadorId);
    List<Pago> findByFecha(LocalDate fecha);
    List<Pago> findByEstado(String estado);
}

