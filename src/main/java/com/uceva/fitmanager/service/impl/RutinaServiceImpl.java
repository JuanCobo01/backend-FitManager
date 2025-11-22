package com.uceva.fitmanager.service.impl;

import com.uceva.fitmanager.model.DetalleRutina;
import com.uceva.fitmanager.model.Ejercicio;
import com.uceva.fitmanager.model.Rutina;
import com.uceva.fitmanager.model.dto.EjercicioDetalleDTO;
import com.uceva.fitmanager.model.dto.RutinaResponseDTO;
import com.uceva.fitmanager.model.dto.RutinaSimpleDTO;
import com.uceva.fitmanager.repository.rutinaRepository;
import com.uceva.fitmanager.service.IRutinaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RutinaServiceImpl implements IRutinaService {

    private final rutinaRepository rutinaRepository;

    public RutinaServiceImpl(rutinaRepository rutinaRepository) {
        this.rutinaRepository = rutinaRepository;
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
                    r.setDificultad(rutinaActualizada.getDificultad());
                    r.setDuracionMinutos(rutinaActualizada.getDuracionMinutos());
                    r.setObjetivo(rutinaActualizada.getObjetivo());
                    r.setIcono(rutinaActualizada.getIcono());
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

    @Override
    @Transactional(readOnly = true)
    public List<RutinaSimpleDTO> findRutinasByUsuarioId(Long usuarioId) {
        List<Rutina> rutinas = rutinaRepository.findByUsuarioIdUsuario(usuarioId);
        return rutinas.stream()
                .map(this::convertToSimpleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RutinaResponseDTO findRutinaWithEjerciciosById(Long rutinaId) {
        Rutina rutina = rutinaRepository.findById(rutinaId)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada con id " + rutinaId));
        
        return convertToResponseDTO(rutina);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EjercicioDetalleDTO> findEjerciciosByRutinaId(Long rutinaId) {
        Rutina rutina = rutinaRepository.findById(rutinaId)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada con id " + rutinaId));
        
        return rutina.getDetalles().stream()
                .sorted((d1, d2) -> Integer.compare(
                    d1.getOrden() != null ? d1.getOrden() : 0,
                    d2.getOrden() != null ? d2.getOrden() : 0
                ))
                .map(this::convertToEjercicioDetalleDTO)
                .collect(Collectors.toList());
    }

    private RutinaSimpleDTO convertToSimpleDTO(Rutina rutina) {
        return new RutinaSimpleDTO(
                rutina.getIdRutina().toString(),
                rutina.getNombreRutina(),
                rutina.getDescripcion(),
                rutina.getDificultad(),
                rutina.getDuracionMinutos(),
                rutina.getObjetivo(),
                rutina.getIcono()
        );
    }

    private RutinaResponseDTO convertToResponseDTO(Rutina rutina) {
        List<EjercicioDetalleDTO> ejercicios = rutina.getDetalles().stream()
                .sorted((d1, d2) -> Integer.compare(
                    d1.getOrden() != null ? d1.getOrden() : 0,
                    d2.getOrden() != null ? d2.getOrden() : 0
                ))
                .map(this::convertToEjercicioDetalleDTO)
                .collect(Collectors.toList());

        return new RutinaResponseDTO(
                rutina.getIdRutina().toString(),
                rutina.getNombreRutina(),
                rutina.getDescripcion(),
                rutina.getDificultad(),
                rutina.getDuracionMinutos(),
                rutina.getObjetivo(),
                rutina.getIcono(),
                ejercicios
        );
    }

    private EjercicioDetalleDTO convertToEjercicioDetalleDTO(DetalleRutina detalle) {
        Ejercicio ejercicio = detalle.getEjercicio();
        
        List<String> instrucciones = ejercicio.getInstrucciones().stream()
                .sorted((i1, i2) -> Integer.compare(i1.getOrden(), i2.getOrden()))
                .map(i -> i.getTexto())
                .collect(Collectors.toList());
        
        List<String> tips = ejercicio.getTips().stream()
                .map(t -> t.getTexto())
                .collect(Collectors.toList());

        return new EjercicioDetalleDTO(
                ejercicio.getIdEjercicio().toString(),
                ejercicio.getNombreEjercicio(),
                ejercicio.getMaquina(),
                ejercicio.getGrupoMuscular(),
                instrucciones,
                detalle.getSeries(),
                detalle.getRepeticiones(),
                detalle.getDescansoSegundos(),
                tips,
                detalle.getOrden()
        );
    }
}
