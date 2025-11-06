package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "progresos")
@Data
public class Progreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProgreso;

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fecha;
    
    @DecimalMin(value = "1.0", message = "El peso debe ser mayor a 0")
    @DecimalMax(value = "500.0", message = "El peso debe ser menor a 500 kg")
    private double peso;
    
    @DecimalMin(value = "10.0", message = "La medida del pecho debe ser válida")
    @DecimalMax(value = "200.0", message = "La medida del pecho debe ser válida")
    private double medidaPecho;
    
    @DecimalMin(value = "10.0", message = "La medida de la cintura debe ser válida")
    @DecimalMax(value = "200.0", message = "La medida de la cintura debe ser válida")
    private double medidaCintura;
    
    @DecimalMin(value = "10.0", message = "La medida del brazo debe ser válida")
    @DecimalMax(value = "100.0", message = "La medida del brazo debe ser válida")
    private double medidaBrazo;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference(value = "usuario-progresos")
    private Usuario usuario;
}
