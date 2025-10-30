package com.uceva.fitmanager.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class GimnasioDTO {
    private Long idGimnasio;
    private String nombre;
    private String direccion;
    private String telefono;

    // Solo información básica del administrador
    private AdministradorDTO administrador;

    // Lista de entrenadores (sin datos sensibles)
    private List<EntrenadorDTO> entrenadores;

    // Cantidad de clientes sin exponer datos personales
    private Integer totalClientes;
}
