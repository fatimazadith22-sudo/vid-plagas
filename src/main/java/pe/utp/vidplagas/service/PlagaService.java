package pe.utp.vidplagas.service;

import pe.utp.vidplagas.dto.request.PlagaRequest;
import pe.utp.vidplagas.dto.response.PlagaResponse;

import java.util.List;

public interface PlagaService {
    List<PlagaResponse> listarTodas();
    PlagaResponse obtenerPorId(Long id);
    List<PlagaResponse> listarPorTipo(String tipo);
    PlagaResponse crear(PlagaRequest request);
    PlagaResponse actualizar(Long id, PlagaRequest request);
    void eliminar(Long id);
}
