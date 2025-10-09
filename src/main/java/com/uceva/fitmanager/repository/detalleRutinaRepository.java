package com.uceva.fitmanager.repository;

import com.uceva.fitmanager.model.DetalleRutina;
import com.uceva.fitmanager.model.Rutina;
import com.uceva.fitmanager.model.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface detalleRutinaRepository extends JpaRepository<DetalleRutina, Long> {
    List<DetalleRutina> findByRutinaIdRutina(Long idRutina);
    List<DetalleRutina> findByEjercicioIdEjercicio(Long idEjercicio);
    Optional<DetalleRutina> findByRutinaAndEjercicio(Rutina rutina, Ejercicio ejercicio);
}
