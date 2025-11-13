package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalle_rutina")
@Data
public class DetalleRutina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    private Integer orden; // Orden del ejercicio en la rutina
    
    private Integer series;
    
    @Column(length = 20)
    private String repeticiones; // Ej: "8-10", "12", "3x30seg"
    
    private Integer descansoSegundos; // Tiempo de descanso en segundos

    @ManyToOne
    @JoinColumn(name = "id_rutina")
    @JsonBackReference(value = "rutina-detalles")
    private Rutina rutina;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio")
    @JsonBackReference(value = "ejercicio-detalles")
    private Ejercicio ejercicio;
}
