package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.model.Pago;
import com.uceva.fitmanager.repository.pagoRepository;
import com.uceva.fitmanager.repository.usuarioRepository;
import com.uceva.fitmanager.repository.entrenadorRepository;
import com.uceva.fitmanager.service.IPagoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements IPagoService {

    private final pagoRepository pagoRepository;
    private final usuarioRepository usuarioRepository;
    private final entrenadorRepository entrenadorRepository;

    public PagoServiceImpl(pagoRepository pagoRepository, usuarioRepository usuarioRepository, entrenadorRepository entrenadorRepository) {
        this.pagoRepository = pagoRepository;
        this.usuarioRepository = usuarioRepository;
        this.entrenadorRepository = entrenadorRepository;
    }

    @Override
    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    @Override
    public Optional<Pago> findById(Long id) {
        return pagoRepository.findById(id);
    }

    @Override
    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public void delete(Long id) {
        pagoRepository.deleteById(id);
    }

    @Override
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

    @Override
    public List<Pago> findByUsuarioId(Long usuarioId) {
        return pagoRepository.findByUsuarioIdUsuario(usuarioId);
    }

    @Override
    public List<Pago> findByEntrenadorId(Long entrenadorId) {
        return pagoRepository.findByEntrenadorIdEntrenador(entrenadorId);
    }

    @Override
    public List<Pago> findByFecha(LocalDate fecha) {
        return pagoRepository.findByFechaPago(fecha);
    }

    @Override
    public List<Pago> findByEstado(String estado) {
        return pagoRepository.findByEstado(estado);
    }
}
