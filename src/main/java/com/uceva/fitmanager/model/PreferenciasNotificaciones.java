package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "preferencias_notificaciones")
@Data
public class PreferenciasNotificaciones {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "recordatorios_entrenamiento")
    private Boolean recordatoriosEntrenamiento = true;
    
    @Column(name = "actualizaciones_progreso")
    private Boolean actualizacionesProgreso = true;
    
    @Column(name = "nuevas_rutinas")
    private Boolean nuevasRutinas = false;
    
    @Column(name = "logros")
    private Boolean logros = true;
    
    @Column(name = "notificaciones_sistema")
    private Boolean notificacionesSistema = true;
    
    @Column(name = "notificaciones_email")
    private Boolean notificacionesEmail = false;
    
    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    @JsonBackReference
    private Usuario usuario;
}
