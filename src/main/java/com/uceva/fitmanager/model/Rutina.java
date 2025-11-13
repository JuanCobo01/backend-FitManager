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
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(length = 50)
    private String dificultad; // Principiante, Intermedio, Avanzado
    
    private Integer duracionMinutos;
    
    @Column(length = 100)
    private String objetivo; // Ej: "Hipertrofia y Fuerza", "Resistencia", etc.
    
    @Column(length = 50)
    private String icono; // Nombre del icono para el frontend

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference(value = "usuario-rutinas")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_entrenador")
    @JsonBackReference(value = "entrenador-rutinas")
    private Entrenador entrenador;

    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "rutina-detalles")
    private List<DetalleRutina> detalles;
}
