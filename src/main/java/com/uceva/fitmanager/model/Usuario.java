package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nombre;
    private String correo;
    private String contrasena;
    private int edad;
    private double altura;
    private double pesoInicial;
    private LocalDate fechaRegistro;

    // Relación con Rutinas
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "usuario-rutinas")
    private List<Rutina> rutinas;

    // Relación con Progresos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "usuario-progresos")
    private List<Progreso> progresos;

    // Relación con Pagos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "usuario-pagos")
    private List<Pago> pagos;

    // Relación muchos a uno con Gimnasio (el usuario es cliente de un gimnasio)
    @ManyToOne
    @JoinColumn(name = "id_gimnasio")
    @JsonBackReference(value = "gim-usuario")
    private Gimnasio gimnasio;
}
