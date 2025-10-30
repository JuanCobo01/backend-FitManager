package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.model.Administrador;
import com.uceva.fitmanager.repository.administradorRepository;
import com.uceva.fitmanager.service.IAdministradorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorServiceImpl implements IAdministradorService {

    private final administradorRepository administradorRepository;

    public AdministradorServiceImpl(administradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    @Override
    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    @Override
    public Optional<Administrador> findById(Long id) {
        return administradorRepository.findById(id);
    }

    @Override
    public Administrador save(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @Override
    public void delete(Long id) {
        administradorRepository.deleteById(id);
    }

    @Override
    public Administrador update(Long id, Administrador administradorActualizado) {
        return administradorRepository.findById(id)
                .map(a -> {
                    a.setNombre(administradorActualizado.getNombre());
                    a.setCorreo(administradorActualizado.getCorreo());
                    a.setContrasena(administradorActualizado.getContrasena());
                    a.setRol(administradorActualizado.getRol());
                    return administradorRepository.save(a);
                })
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado con id " + id));
    }

    @Override
    public Optional<Administrador> findByEmailAndPassword(String email, String password) {
        return administradorRepository.findByCorreoAndContrasena(email, password);
    }
}
