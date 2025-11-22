package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.model.DetalleRutina;
import com.uceva.fitmanager.repository.detalleRutinaRepository;
import com.uceva.fitmanager.service.IDetalleRutinaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleRutinaServiceImpl implements IDetalleRutinaService {

    private final detalleRutinaRepository detalleRutinaRepository;

    public DetalleRutinaServiceImpl(detalleRutinaRepository detalleRutinaRepository) {
        this.detalleRutinaRepository = detalleRutinaRepository;
    }

    @Override
    public List<DetalleRutina> findAll() {
        return detalleRutinaRepository.findAll();
    }

    @Override
    public Optional<DetalleRutina> findById(Long id) {
        return detalleRutinaRepository.findById(id);
    }

    @Override
    public DetalleRutina save(DetalleRutina detalleRutina) {
        return detalleRutinaRepository.save(detalleRutina);
    }

    @Override
    public void delete(Long id) {
        detalleRutinaRepository.deleteById(id);
    }

    @Override
    public DetalleRutina update(Long id, DetalleRutina detalleRutinaActualizada) {
        return detalleRutinaRepository.findById(id)
                .map(d -> {
                    d.setSeries(detalleRutinaActualizada.getSeries());
                    d.setRepeticiones(detalleRutinaActualizada.getRepeticiones());
                    d.setRutina(detalleRutinaActualizada.getRutina());
                    d.setEjercicio(detalleRutinaActualizada.getEjercicio());
                    return detalleRutinaRepository.save(d);
                })
                .orElseThrow(() -> new RuntimeException("Detalle de rutina no encontrado con id " + id));
    }

    @Override
    public List<DetalleRutina> findByRutinaId(Long rutinaId) {
        return detalleRutinaRepository.findByRutinaIdRutina(rutinaId);
    }

    @Override
    public List<DetalleRutina> findByEjercicioId(Long ejercicioId) {
        return detalleRutinaRepository.findByEjercicioIdEjercicio(ejercicioId);
    }
}
