package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference(value = "usuario-rutinas")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_entrenador")
    @JsonBackReference(value = "entrenador-rutinas")
    private Entrenador entrenador;

    @OneToMany(mappedBy = "rutina")
    @JsonManagedReference(value = "rutina-detalles")
    private List<DetalleRutina> detalles;
}
