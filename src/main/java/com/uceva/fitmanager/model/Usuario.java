package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

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
    
    @Min(value = 1, message = "La edad debe ser mayor a 0")
    @Max(value = 120, message = "La edad debe ser menor a 120")
    private int edad;
    
    @DecimalMin(value = "0.1", message = "La altura debe ser mayor a 0")
    @DecimalMax(value = "3.0", message = "La altura debe ser menor a 3 metros")
    private double altura;
    
    @DecimalMin(value = "1.0", message = "El peso debe ser mayor a 0")
    @DecimalMax(value = "500.0", message = "El peso debe ser menor a 500 kg")
    private double pesoInicial;
    
    @DecimalMin(value = "1.0", message = "El peso actual debe ser mayor a 0")
    @DecimalMax(value = "500.0", message = "El peso actual debe ser menor a 500 kg")
    @Column(name = "peso_actual")
    private Double pesoActual; // Peso actual en kg, puede ser null si no se ha actualizado
    
    @Column(length = 5)
    private String idioma = "es"; // Idioma preferido: 'es' o 'en'
    
    private LocalDate fechaRegistro;
    
    @UpdateTimestamp
    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

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
    
    // Relación OneToOne con PreferenciasNotificaciones
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private PreferenciasNotificaciones preferenciasNotificaciones;
    
    // Relación OneToMany con Feedback
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Feedback> feedbacks;

    // Relación muchos a uno con Gimnasio (el usuario es cliente de un gimnasio)
    @ManyToOne
    @JoinColumn(name = "id_gimnasio")
    @JsonBackReference(value = "gim-usuario")
    private Gimnasio gimnasio;
    
    // Métodos de utilidad
    public double getPesoActualOInicial() {
        return pesoActual != null ? pesoActual : pesoInicial;
    }
    
    public double getAlturaEnCm() {
        return altura * 100;
    }
    
    public double getImc() {
        double peso = getPesoActualOInicial();
        return peso / (altura * altura);
    }
}
