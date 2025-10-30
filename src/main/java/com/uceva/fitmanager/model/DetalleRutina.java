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

    private int repeticiones;
    private int series;

    @ManyToOne
    @JoinColumn(name = "id_rutina")
    @JsonBackReference(value = "rutina-detalles")
    private Rutina rutina;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio")
    @JsonBackReference(value = "ejercicio-detalles")
    private Ejercicio ejercicio;
}
