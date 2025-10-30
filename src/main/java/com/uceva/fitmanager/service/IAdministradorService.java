package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Administrador;

import java.util.List;
import java.util.Optional;

public interface IAdministradorService {
    List<Administrador> findAll();
    Optional<Administrador> findById(Long id);
    Administrador save(Administrador administrador);
    void delete(Long id);
    Administrador update(Long id, Administrador administradorActualizado);
    Optional<Administrador> findByEmailAndPassword(String email, String password);
}
