package com.uceva.fitmanager.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PagoDTO {
    private Long idPago;
    private LocalDate fechaPago;
    private double monto;
    private String metodoPago;
    private String estado;
    private String tipoSuscripcion;

    // Información básica del usuario (solo nombre, no datos sensibles)
    private String nombreUsuario;

    // Información del entrenador si aplica
    private String nombreEntrenador;

    // NO incluimos: referencias completas a usuario/entrenador (información personal)
    // Este DTO debe tener restricciones de acceso según el rol del usuario
}
