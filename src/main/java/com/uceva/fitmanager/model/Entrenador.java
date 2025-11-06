package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "entrenadores")
@Data
public class Entrenador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrenador;

    private String nombre;
    private String correo;
    private String contrasena;
    private String especialidad;

    @OneToMany(mappedBy = "entrenador")
    @JsonManagedReference(value = "entrenador-rutinas")
    private List<Rutina> rutinas; // Corregido de "Rutinas" a "rutinas"

    @OneToMany(mappedBy = "entrenador")
    @JsonManagedReference(value = "entrenador-pagos")
    private List<Pago> pagos;

    // Relación muchos a uno con Gimnasio
    @ManyToOne
    @JoinColumn(name = "id_gimnasio")
    @JsonBackReference(value = "gim-entrenador")
    private Gimnasio gimnasio;
}
