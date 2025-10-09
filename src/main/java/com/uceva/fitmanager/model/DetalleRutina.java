package com.uceva.fitmanager.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalle_rutina")
@Data
public class DetalleRutina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    private int repeticiones;
    private int series;

    @ManyToOne
    @JoinColumn(name = "id_rutina")
    private Rutina rutina;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio")
    private Ejercicio ejercicio;
}

