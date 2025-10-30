package com.uceva.fitmanager.model.dto;

import lombok.Data;

@Data
public class AdministradorDTO {
    private Long idAdmin;
    private String nombre;
    private String correo;
    private String rol;

    // NO incluimos: contrasena (información sensible)
    // Información del gimnasio se maneja por separado para evitar referencia circular
}
