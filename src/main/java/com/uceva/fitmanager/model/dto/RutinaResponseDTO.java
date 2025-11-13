package com.uceva.fitmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RutinaResponseDTO {
    private String id;
    private String nombre;
    private String descripcion;
    private String dificultad;
    private Integer duracionMinutos;
    private String objetivo;
    private String icono;
    private List<EjercicioDetalleDTO> ejercicios;
}
