package com.uceva.fitmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenciasNotificacionesDTO {
    
    private Boolean recordatoriosEntrenamiento;
    private Boolean actualizacionesProgreso;
    private Boolean nuevasRutinas;
    private Boolean logros;
    private Boolean notificacionesSistema;
    private Boolean notificacionesEmail;
}
