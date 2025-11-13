package com.uceva.fitmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RutinaSimpleDTO {
    private String id;
    private String nombre;
    private String descripcion;
    private String dificultad;
    private Integer duracionMinutos;
    private String objetivo;
    private String icono;
}
