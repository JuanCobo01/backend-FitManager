package com.uceva.fitmanager.repository;

import com.uceva.fitmanager.model.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ejercicioRepository extends JpaRepository<Ejercicio, Long> {
    List<Ejercicio> findByGrupoMuscular(String grupoMuscular);
    List<Ejercicio> findByNombreEjercicioContainingIgnoreCase(String nombre);
}
