package pe.utp.vidplagas.service;

import pe.utp.vidplagas.dto.request.LoteRequest;
import pe.utp.vidplagas.dto.response.LoteResponse;

import java.util.List;

public interface LoteService {
    List<LoteResponse> listarTodos();
    LoteResponse obtenerPorId(Long id);
    LoteResponse crear(LoteRequest request);
    LoteResponse actualizar(Long id, LoteRequest request);
    void eliminar(Long id);
}
