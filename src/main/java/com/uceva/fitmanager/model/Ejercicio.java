package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String detalles;
    private String grupoMuscular;

    @OneToMany(mappedBy = "ejercicio")
    @JsonManagedReference(value = "ejercicio-detalles")
    private List<DetalleRutina> detallesRutina;
}
