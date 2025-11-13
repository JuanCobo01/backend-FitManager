package com.uceva.fitmanager.repository;

import com.uceva.fitmanager.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface feedbackRepository extends JpaRepository<Feedback, Long> {
    
    List<Feedback> findByUsuarioIdUsuario(Long usuarioId);
    
    List<Feedback> findByEstado(Feedback.EstadoFeedback estado);
    
    List<Feedback> findByUsuarioIdUsuarioOrderByFechaDesc(Long usuarioId);
}
