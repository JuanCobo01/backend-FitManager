package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "entrenadores")
@Data
public class Entrenador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrenador;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Column(unique = true)
    private String correo;
    
    @JsonIgnore
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena;
    
    @NotBlank(message = "La especialidad es obligatoria")
    @Size(min = 2, max = 100, message = "La especialidad debe tener entre 2 y 100 caracteres")
    private String especialidad;

    @OneToMany(mappedBy = "entrenador")
    @JsonManagedReference(value = "entrenador-rutinas")
    private List<Rutina> Rutinas;

    @OneToMany(mappedBy = "entrenador")
    @JsonManagedReference(value = "entrenador-pagos")
    private List<Pago> pagos;

    // Relación muchos a uno con Gimnasio
    @ManyToOne
    @JoinColumn(name = "id_gimnasio")
    @JsonBackReference(value = "gim-entrenador")
    private Gimnasio gimnasio;
}
