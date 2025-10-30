package com.uceva.fitmanager.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UsuarioDTO {
    private Long idUsuario;
    private String nombre;
    private String correo;
    private int edad;
    private double altura;
    private double pesoInicial;
    private LocalDate fechaRegistro;

    // Información del gimnasio al que pertenece
    private String nombreGimnasio;

    // Estadísticas generales sin exponer detalles
    private Integer totalRutinas;
    private Integer totalProgresos;

    // NO incluimos: contrasena (información sensible)
    // NO incluimos: lista completa de rutinas (podría exponer datos del entrenador)
    // NO incluimos: lista de progresos (datos médicos sensibles)
    // NO incluimos: lista de pagos (información financiera sensible)
}
