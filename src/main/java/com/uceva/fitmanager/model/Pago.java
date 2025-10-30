package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@Data
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private LocalDate fechaPago;
    private double monto;
    private String metodoPago;
    private String estado;
    private String tipoSuscripcion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference(value = "usuario-pagos")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_entrenador")
    @JsonBackReference(value = "entrenador-pagos")
    private Entrenador entrenador;
}
