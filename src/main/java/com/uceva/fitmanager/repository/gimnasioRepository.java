package com.uceva.fitmanager.repository;

import com.uceva.fitmanager.model.Gimnasio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface gimnasioRepository extends JpaRepository<Gimnasio, Long> {
    List<Gimnasio> findByNombreContainingIgnoreCase(String nombre);
    Optional<Gimnasio> findByNombre(String nombre);
    List<Gimnasio> findByDireccionContainingIgnoreCase(String direccion);
}

