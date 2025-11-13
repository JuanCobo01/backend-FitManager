package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ejercicio_instrucciones")
@Data
public class EjercicioInstruccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer orden; // Orden de la instrucción (1, 2, 3...)

    @Column(columnDefinition = "TEXT", nullable = false)
    private String texto; // Texto de la instrucción

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ejercicio", nullable = false)
    @JsonBackReference(value = "ejercicio-instrucciones")
    private Ejercicio ejercicio;
}
