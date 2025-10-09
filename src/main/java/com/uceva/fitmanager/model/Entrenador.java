package com.uceva.fitmanager.model;

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
    private List<Rutina> Rutinas;

    @OneToMany(mappedBy = "entrenador")
    private List<Pago> pagos;
}


