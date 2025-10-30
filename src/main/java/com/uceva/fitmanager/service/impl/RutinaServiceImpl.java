package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.model.Rutina;
import com.uceva.fitmanager.repository.rutinaRepository;
import com.uceva.fitmanager.repository.usuarioRepository;
import com.uceva.fitmanager.repository.entrenadorRepository;
import com.uceva.fitmanager.service.IRutinaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutinaServiceImpl implements IRutinaService {

    private final rutinaRepository rutinaRepository;
    private final usuarioRepository usuarioRepository;
    private final entrenadorRepository entrenadorRepository;

    public RutinaServiceImpl(rutinaRepository rutinaRepository, usuarioRepository usuarioRepository, entrenadorRepository entrenadorRepository) {
        this.rutinaRepository = rutinaRepository;
        this.usuarioRepository = usuarioRepository;
        this.entrenadorRepository = entrenadorRepository;
    }

    @Override
    public List<Rutina> findAll() {
        return rutinaRepository.findAll();
    }

    @Override
    public Optional<Rutina> findById(Long id) {
        return rutinaRepository.findById(id);
    }

    @Override
    public Rutina save(Rutina rutina) {
        return rutinaRepository.save(rutina);
    }

    @Override
    public void delete(Long id) {
        rutinaRepository.deleteById(id);
    }

    @Override
    public Rutina update(Long id, Rutina rutinaActualizada) {
        return rutinaRepository.findById(id)
                .map(r -> {
                    r.setNombreRutina(rutinaActualizada.getNombreRutina());
                    r.setDescripcion(rutinaActualizada.getDescripcion());
                    r.setUsuario(rutinaActualizada.getUsuario());
                    r.setEntrenador(rutinaActualizada.getEntrenador());
                    return rutinaRepository.save(r);
                })
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada con id " + id));
    }

    @Override
    public List<Rutina> findByUsuarioId(Long usuarioId) {
        return rutinaRepository.findByUsuarioIdUsuario(usuarioId);
    }

    @Override
    public List<Rutina> findByEntrenadorId(Long entrenadorId) {
        return rutinaRepository.findByEntrenadorIdEntrenador(entrenadorId);
    }
}
