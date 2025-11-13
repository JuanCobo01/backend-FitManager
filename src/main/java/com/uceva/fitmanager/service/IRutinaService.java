package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Rutina;
import com.uceva.fitmanager.model.dto.EjercicioDetalleDTO;
import com.uceva.fitmanager.model.dto.RutinaResponseDTO;
import com.uceva.fitmanager.model.dto.RutinaSimpleDTO;

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
    
    // Nuevos métodos para DTOs
    List<RutinaSimpleDTO> findRutinasByUsuarioId(Long usuarioId);
    RutinaResponseDTO findRutinaWithEjerciciosById(Long rutinaId);
    List<EjercicioDetalleDTO> findEjerciciosByRutinaId(Long rutinaId);
}


