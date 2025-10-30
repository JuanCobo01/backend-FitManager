package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "gimnasios")
@Data
public class Gimnasio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGimnasio;

    private String nombre;
    private String direccion;
    private String telefono;

    // Un gimnasio tiene un administrador
    @OneToOne
    @JoinColumn(name = "id_admin", unique = true)
    @JsonManagedReference(value = "admin-gim")
    private Administrador administrador;

    // Un gimnasio tiene muchos entrenadores
    @OneToMany(mappedBy = "gimnasio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "gim-entrenador")
    private List<Entrenador> entrenadores;

    @OneToMany(mappedBy = "gimnasio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "gim-usuario")
    private List<Usuario> clientes;
}

