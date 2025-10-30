package com.uceva.fitmanager.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProgresoDTO {
    private Long idProgreso;
    private LocalDate fecha;
    private double peso;
    private double medidaPecho;
    private double medidaCintura;
    private double medidaBrazo;

    // Solo para el usuario propietario - no se expone en consultas generales
    private String nombreUsuario; // Solo cuando es necesario

    // NO incluimos: referencia completa al usuario (información personal sensible)
    // Este DTO solo debe ser visible para el usuario propietario o su entrenador
}
