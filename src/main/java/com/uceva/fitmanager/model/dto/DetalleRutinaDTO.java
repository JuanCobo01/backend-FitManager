package com.uceva.fitmanager.model.dto;

import lombok.Data;

@Data
public class DetalleRutinaDTO {
    private Long idDetalle;
    private int repeticiones;
    private int series;

    // Información del ejercicio
    private String nombreEjercicio;
    private String descripcionEjercicio;
    private String grupoMuscular;

    // NO incluimos: referencias completas a rutina para evitar recursión
    // La información de la rutina se maneja desde RutinaDTO
}
