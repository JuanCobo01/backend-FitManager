package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.Pago;
import com.uceva.fitmanager.model.Usuario;
import com.uceva.fitmanager.model.Entrenador;
import com.uceva.fitmanager.repository.pagoRepository;
import com.uceva.fitmanager.repository.usuarioRepository;
import com.uceva.fitmanager.repository.entrenadorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class pagoService {

    private final pagoRepository pagoRepository;
    private final usuarioRepository usuarioRepository;
    private final entrenadorRepository entrenadorRepository;

    public pagoService(pagoRepository pagoRepository, usuarioRepository usuarioRepository, entrenadorRepository entrenadorRepository) {
        this.pagoRepository = pagoRepository;
        this.usuarioRepository = usuarioRepository;
        this.entrenadorRepository = entrenadorRepository;
    }

    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> findById(Long id) {
        return pagoRepository.findById(id);
    }

    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void delete(Long id) {
        pagoRepository.deleteById(id);
    }

    public Pago update(Long id, Pago pagoActualizado) {
        return pagoRepository.findById(id)
                .map(p -> {
                    p.setMonto(pagoActualizado.getMonto());
                    p.setFechaPago(pagoActualizado.getFechaPago());
                    p.setMetodoPago(pagoActualizado.getMetodoPago());
                    p.setEstado(pagoActualizado.getEstado());
                    p.setUsuario(pagoActualizado.getUsuario());

                    return pagoRepository.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con id " + id));
    }

    public List<Pago> findByUsuarioId(Long usuarioId) {
        return pagoRepository.findByUsuarioIdUsuario(usuarioId);
    }

    public List<Pago> findByEntrenadorId(Long entrenadorId) {
        return pagoRepository.findByEntrenadorIdEntrenador(entrenadorId);
    }

    public List<Pago> findByFecha(LocalDate fecha) {
        return pagoRepository.findByFechaPago(fecha);
    }

    public List<Pago> findByEstado(String estado) {
        return pagoRepository.findByEstado(estado);
    }
}

