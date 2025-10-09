package com.uceva.fitmanager.repository;

import com.uceva.fitmanager.model.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface rutinaRepository extends JpaRepository<Rutina, Long> {
    List<Rutina> findByUsuarioIdUsuario(Long idUsuario);
    List<Rutina> findByEntrenadorIdEntrenador(Long idEntrenador);
}

