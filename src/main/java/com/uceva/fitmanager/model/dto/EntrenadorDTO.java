package com.uceva.fitmanager.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class EntrenadorDTO {
    private Long idEntrenador;
    private String nombre;
    private String correo;
    private String especialidad;

    // Información del gimnasio donde trabaja
    private String nombreGimnasio;

    // Cantidad de rutinas asignadas sin exponer detalles de usuarios
    private Integer totalRutinasAsignadas;

    // NO incluimos: contrasena (información sensible)
    // NO incluimos: lista completa de rutinas (datos de usuarios)
    // NO incluimos: lista de pagos (información financiera sensible)
}
