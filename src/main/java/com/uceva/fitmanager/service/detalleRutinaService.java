package com.uceva.fitmanager.service;

import com.uceva.fitmanager.model.DetalleRutina;
import com.uceva.fitmanager.model.Rutina;
import com.uceva.fitmanager.model.Ejercicio;
import com.uceva.fitmanager.repository.detalleRutinaRepository;
import com.uceva.fitmanager.repository.rutinaRepository;
import com.uceva.fitmanager.repository.ejercicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class detalleRutinaService {

    private final detalleRutinaRepository detalleRutinaRepository;
    private final rutinaRepository rutinaRepository;
    private final ejercicioRepository ejercicioRepository;

    public detalleRutinaService(detalleRutinaRepository detalleRutinaRepository, rutinaRepository rutinaRepository, ejercicioRepository ejercicioRepository) {
        this.detalleRutinaRepository = detalleRutinaRepository;
        this.rutinaRepository = rutinaRepository;
        this.ejercicioRepository = ejercicioRepository;
    }

    public List<DetalleRutina> findAll() {
        return detalleRutinaRepository.findAll();
    }

    public Optional<DetalleRutina> findById(Long id) {
        return detalleRutinaRepository.findById(id);
    }

    public DetalleRutina save(DetalleRutina detalleRutina) {
        return detalleRutinaRepository.save(detalleRutina);
    }

    public void delete(Long id) {
        detalleRutinaRepository.deleteById(id);
    }

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

    public List<DetalleRutina> findByRutinaId(Long rutinaId) {
        return detalleRutinaRepository.findByRutinaIdRutina(rutinaId);
    }

    public List<DetalleRutina> findByEjercicioId(Long ejercicioId) {
        return detalleRutinaRepository.findByEjercicioIdEjercicio(ejercicioId);
    }
}
