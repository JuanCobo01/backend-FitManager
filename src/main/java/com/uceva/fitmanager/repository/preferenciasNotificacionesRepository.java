package com.uceva.fitmanager.repository;

import com.uceva.fitmanager.model.PreferenciasNotificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface preferenciasNotificacionesRepository extends JpaRepository<PreferenciasNotificaciones, Long> {
    
    Optional<PreferenciasNotificaciones> findByUsuarioIdUsuario(Long usuarioId);
    
    boolean existsByUsuarioIdUsuario(Long usuarioId);
}
