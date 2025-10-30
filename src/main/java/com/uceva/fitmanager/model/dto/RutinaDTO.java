package com.uceva.fitmanager.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class RutinaDTO {
    private Long idRutina;
    private String nombreRutina;
    private String descripcion;

    // Información básica del usuario (sin datos sensibles)
    private String nombreUsuario;
    private Long idUsuario;

    // Información del entrenador asignado
    private String nombreEntrenador;
    private String especialidadEntrenador;

    // Lista de ejercicios de la rutina
    private List<DetalleRutinaDTO> detalles;

    // Estadísticas
    private Integer totalEjercicios;
}
