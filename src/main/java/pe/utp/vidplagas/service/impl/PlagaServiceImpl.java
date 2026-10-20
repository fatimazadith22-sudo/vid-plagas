package pe.utp.vidplagas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.utp.vidplagas.dto.request.PlagaRequest;
import pe.utp.vidplagas.dto.response.PlagaResponse;
import pe.utp.vidplagas.entity.Plaga;
import pe.utp.vidplagas.exception.ResourceNotFoundException;
import pe.utp.vidplagas.mapper.PlagaMapper;
import pe.utp.vidplagas.repository.PlagaRepository;
import pe.utp.vidplagas.service.PlagaService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlagaServiceImpl implements PlagaService {

    private final PlagaRepository plagaRepository;
    private final PlagaMapper plagaMapper;

    @Override
    public List<PlagaResponse> listarTodas() {
        return plagaMapper.toResponseList(plagaRepository.findAll());
    }

    @Override
    public PlagaResponse obtenerPorId(Long id) {
        Plaga plaga = plagaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plaga", "id", id));
        return plagaMapper.toResponse(plaga);
    }

    @Override
    public List<PlagaResponse> listarPorTipo(String tipo) {
        return plagaMapper.toResponseList(plagaRepository.findByTipoIgnoreCase(tipo));
    }

    @Override
    @Transactional
    public PlagaResponse crear(PlagaRequest request) {
        Plaga plaga = plagaMapper.toEntity(request);
        return plagaMapper.toResponse(plagaRepository.save(plaga));
    }

    @Override
    @Transactional
    public PlagaResponse actualizar(Long id, PlagaRequest request) {
        Plaga plaga = plagaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plaga", "id", id));
        plaga.setNombreComun(request.getNombreComun());
        plaga.setNombreCientifico(request.getNombreCientifico());
        plaga.setTipo(request.getTipo());
        plaga.setSintomas(request.getSintomas());
        plaga.setNivelDano(request.getNivelDano());
        plaga.setImagen(request.getImagen());
        plaga.setRecomendacion(request.getRecomendacion());
        return plagaMapper.toResponse(plagaRepository.save(plaga));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!plagaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plaga", "id", id);
        }
        plagaRepository.deleteById(id);
    }
}
