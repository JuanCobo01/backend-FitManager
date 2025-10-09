package com.uceva.fitmanager.repository;

import com.uceva.fitmanager.model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface entrenadorRepository extends JpaRepository<Entrenador, Long> {
    List<Entrenador> findByEspecialidad(String especialidad);
    List<Entrenador> findByNombreContainingIgnoreCase(String nombre);
    Optional<Entrenador> findByCorreoAndContrasena(String correo, String contrasena);
    

}
