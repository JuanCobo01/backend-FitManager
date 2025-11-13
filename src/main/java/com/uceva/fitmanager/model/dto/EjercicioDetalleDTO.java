package com.uceva.fitmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EjercicioDetalleDTO {
    private String id;
    private String nombre;
    private String maquina;
    private String grupoMuscular;
    private List<String> instrucciones;
    private Integer series;
    private String repeticiones;
    private Integer descansoSegundos;
    private List<String> tips;
    private Integer orden;
}
