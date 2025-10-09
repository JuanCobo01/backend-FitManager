package com.uceva.fitmanager.repository;

import com.uceva.fitmanager.model.Progreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface progresoRepository extends JpaRepository<Progreso, Long> {
    List<Progreso> findByUsuarioIdUsuario(Long idUsuario);
    List<Progreso> findByUsuarioIdUsuarioOrderByFechaDesc(Long idUsuario);
    List<Progreso> findByUsuarioIdUsuarioAndFechaBetweenOrderByFechaDesc(Long idUsuario, LocalDate fechaInicio, LocalDate fechaFin);
    Optional<Progreso> findTopByUsuarioIdUsuarioOrderByFechaDesc(Long idUsuario);
    List<Progreso> findByFecha(LocalDate fecha);
    List<Progreso> findByUsuarioIdUsuarioAndFecha(Long idUsuario, LocalDate fecha);
}
