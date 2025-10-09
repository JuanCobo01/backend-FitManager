package com.uceva.fitmanager.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "administradores")
@Data
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdmin;

    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;
}

