package com.uceva.fitmanager.repository;

import com.uceva.fitmanager.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface administradorRepository extends JpaRepository<Administrador, Long> {
    List<Administrador> findByRol(String rol);
    List<Administrador> findByNombreContainingIgnoreCase(String nombre);
    Optional<Administrador> findByCorreo(String correo);
    Optional<Administrador> findByCorreoAndContrasena(String correo, String contrasena);
}
