package com.uceva.fitmanager.model.dto;

import lombok.Data;

@Data
public class EjercicioDTO {
    private Long idEjercicio;
    private String nombreEjercicio;
    private String descripcion;
    private String detalles;
    private String grupoMuscular;

    // Estadística de uso sin exponer información de usuarios específicos
    private Integer vecesUtilizado;

    // NO incluimos: lista completa de detalles de rutina (expondría datos de usuarios)
}
