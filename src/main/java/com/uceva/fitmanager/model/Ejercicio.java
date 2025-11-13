package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "ejercicios")
@Data
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEjercicio;

    @NotBlank(message = "El nombre del ejercicio es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombreEjercicio;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
    
    @Size(max = 1000, message = "Los detalles no pueden exceder 1000 caracteres")
    private String detalles;
    
    @NotBlank(message = "El grupo muscular es obligatorio")
    @Pattern(regexp = "Pecho|Espalda|Piernas|Hombros|Brazos|Core|Cardio", 
             message = "El grupo muscular debe ser: Pecho, Espalda, Piernas, Hombros, Brazos, Core o Cardio")
    private String grupoMuscular;
    
    @Column(length = 100)
    private String maquina; // Nombre de la máquina o equipo utilizado
    
    @Column(length = 255)
    private String imagenUrl; // URL o path de la imagen del ejercicio

    @OneToMany(mappedBy = "ejercicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "ejercicio-detalles")
    private List<DetalleRutina> detallesRutina;
    
    @OneToMany(mappedBy = "ejercicio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "ejercicio-instrucciones")
    private List<EjercicioInstruccion> instrucciones;
    
    @OneToMany(mappedBy = "ejercicio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "ejercicio-tips")
    private List<EjercicioTip> tips;
}
