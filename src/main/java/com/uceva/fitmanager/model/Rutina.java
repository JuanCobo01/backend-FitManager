package com.uceva.fitmanager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "rutinas")
@Data
public class Rutina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRutina;

    private String nombreRutina;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_entrenador")
    private Entrenador entrenador;

    @OneToMany(mappedBy = "rutina")
    private List<DetalleRutina> detalles;
}

