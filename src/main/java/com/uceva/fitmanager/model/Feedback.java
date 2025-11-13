package com.uceva.fitmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Data
public class Feedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;
    
    @NotBlank(message = "El mensaje no puede estar vacío")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String mensaje;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EstadoFeedback estado = EstadoFeedback.PENDIENTE;
    
    @Column(columnDefinition = "TEXT")
    private String respuesta;
    
    @Column(name = "fecha_respuesta")
    private LocalDateTime fechaRespuesta;
    
    public enum EstadoFeedback {
        PENDIENTE,
        REVISADO,
        RESUELTO
    }
}
