package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Progreso;
import com.uceva.fitmanager.repository.progresoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class progresoService {

    private final progresoRepository progresoRepository;

    public progresoService(progresoRepository progresoRepository) {
        this.progresoRepository = progresoRepository;
    }

    public List<Progreso> findAll() {
        return progresoRepository.findAll();
    }

    public Optional<Progreso> findById(Long id) {
        return progresoRepository.findById(id);
    }

    public Progreso save(Progreso progreso) {
        return progresoRepository.save(progreso);
    }

    public void delete(Long id) {
        progresoRepository.deleteById(id);
    }

    public Progreso update(Long id, Progreso progresoActualizado) {
        return progresoRepository.findById(id)
                .map(p -> {
                    p.setPeso(p.getPeso());
                    p.setFecha(p.getFecha());
                    p.setMedidaBrazo(p.getMedidaBrazo());
                    p.setMedidaCintura(p.getMedidaCintura());
                    p.setMedidaPecho(p.getMedidaPecho());


                    return progresoRepository.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Progreso no encontrado con id " + id));
    }

    public List<Progreso> findByUsuarioId(Long usuarioId) {
        return progresoRepository.findByUsuarioIdUsuario(usuarioId);
    }

    public List<Progreso> findByFecha(LocalDate fecha) {
        return progresoRepository.findByFecha(fecha);
    }

    public List<Progreso> findByUsuarioIdAndFecha(Long usuarioId, LocalDate fecha) {
        return progresoRepository.findByUsuarioIdUsuarioAndFecha(usuarioId, fecha);
    }
}
