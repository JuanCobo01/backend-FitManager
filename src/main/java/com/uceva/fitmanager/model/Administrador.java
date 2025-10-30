package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    // Relación uno a uno con Gimnasio (lado inverso)
    @OneToOne(mappedBy = "administrador")
    @JsonBackReference(value = "admin-gim")
    private Gimnasio gimnasio;
}
