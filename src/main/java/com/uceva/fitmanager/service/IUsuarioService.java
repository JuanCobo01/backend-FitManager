package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> findAll();
    Page<Usuario> findAllPaginated(Pageable pageable);
    Optional<Usuario> findById(Long id);
    Usuario save(Usuario usuario);
    void delete(Long id);
    Usuario update(Long id, Usuario usuarioActualizado);
    Optional<Usuario> findByEmailAndPassword(String email, String password);
}

