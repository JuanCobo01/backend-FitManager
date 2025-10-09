package com.uceva.fitmanager.repository;

import com.uceva.fitmanager.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface pagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByUsuarioIdUsuario(Long idUsuario);
    List<Pago> findByEntrenadorIdEntrenador(Long idEntrenador);
    List<Pago> findByUsuarioIdUsuarioOrderByFechaPagoDesc(Long idUsuario);
    List<Pago> findByEntrenadorIdEntrenadorOrderByFechaPagoDesc(Long idEntrenador);
    List<Pago> findByEstado(String estado);
    List<Pago> findByTipoSuscripcion(String tipoSuscripcion);
    List<Pago> findByFechaPagoBetweenOrderByFechaPagoDesc(LocalDate fechaInicio, LocalDate fechaFin);
    List<Pago> findByFechaPago(LocalDate fecha);
}

