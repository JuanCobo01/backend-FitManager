package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.model.Progreso;
import com.uceva.fitmanager.repository.progresoRepository;
import com.uceva.fitmanager.service.IProgresoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProgresoServiceImpl implements IProgresoService {

    private final progresoRepository progresoRepository;

    public ProgresoServiceImpl(progresoRepository progresoRepository) {
        this.progresoRepository = progresoRepository;
    }

    @Override
    public List<Progreso> findAll() {
        return progresoRepository.findAll();
    }

    @Override
    public Optional<Progreso> findById(Long id) {
        return progresoRepository.findById(id);
    }

    @Override
    public Progreso save(Progreso progreso) {
        return progresoRepository.save(progreso);
    }

    @Override
    public void delete(Long id) {
        progresoRepository.deleteById(id);
    }

    @Override
    public Progreso update(Long id, Progreso progresoActualizado) {
        return progresoRepository.findById(id)
                .map(p -> {
                    p.setPeso(progresoActualizado.getPeso());
                    p.setFecha(progresoActualizado.getFecha());
                    p.setMedidaBrazo(progresoActualizado.getMedidaBrazo());
                    p.setMedidaCintura(progresoActualizado.getMedidaCintura());
                    p.setMedidaPecho(progresoActualizado.getMedidaPecho());
                    return progresoRepository.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Progreso no encontrado con id " + id));
    }

    @Override
    public List<Progreso> findByUsuarioId(Long usuarioId) {
        return progresoRepository.findByUsuarioIdUsuario(usuarioId);
    }

    @Override
    public List<Progreso> findByFecha(LocalDate fecha) {
        return progresoRepository.findByFecha(fecha);
    }

    @Override
    public List<Progreso> findByUsuarioIdAndFecha(Long usuarioId, LocalDate fecha) {
        return progresoRepository.findByUsuarioIdUsuarioAndFecha(usuarioId, fecha);
    }
}
