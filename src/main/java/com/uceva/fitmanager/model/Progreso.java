package com.uceva.fitmanager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "progresos")
@Data
public class Progreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProgreso;

    private LocalDate fecha;
    private double peso;
    private double medidaPecho;
    private double medidaCintura;
    private double medidaBrazo;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}

