package com.uceva.fitmanager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "ejercicios")
@Data
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEjercicio;

    private String nombreEjercicio;
    private String descripcion;
    private String grupoMuscular;

    @OneToMany(mappedBy = "ejercicio")
    private List<DetalleRutina> detalles;
}

