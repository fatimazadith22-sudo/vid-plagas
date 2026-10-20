package pe.utp.vidplagas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.utp.vidplagas.dto.request.LoteRequest;
import pe.utp.vidplagas.dto.response.LoteResponse;
import pe.utp.vidplagas.entity.Lote;
import pe.utp.vidplagas.exception.ResourceNotFoundException;
import pe.utp.vidplagas.mapper.LoteMapper;
import pe.utp.vidplagas.repository.LoteRepository;
import pe.utp.vidplagas.service.LoteService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoteServiceImpl implements LoteService {

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;

    @Override
    public List<LoteResponse> listarTodos() {
        return loteMapper.toResponseList(loteRepository.findAll());
    }

    @Override
    public LoteResponse obtenerPorId(Long id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote", "id", id));
        return loteMapper.toResponse(lote);
    }

    @Override
    @Transactional
    public LoteResponse crear(LoteRequest request) {
        Lote lote = loteMapper.toEntity(request);
        return loteMapper.toResponse(loteRepository.save(lote));
    }

    @Override
    @Transactional
    public LoteResponse actualizar(Long id, LoteRequest request) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote", "id", id));
        lote.setNombre(request.getNombre());
        lote.setVariedadPredominante(request.getVariedadPredominante());
        lote.setHectareas(request.getHectareas());
        return loteMapper.toResponse(loteRepository.save(lote));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!loteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lote", "id", id);
        }
        loteRepository.deleteById(id);
    }
}
