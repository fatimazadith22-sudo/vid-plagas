package pe.utp.vidplagas.service;

import pe.utp.vidplagas.dto.request.ReporteRequest;
import pe.utp.vidplagas.dto.response.ReporteResponse;

import java.io.IOException;
import java.util.List;

public interface ReporteService {
    List<ReporteResponse> listarTodos();
    ReporteResponse obtenerPorId(Long id);
    ReporteResponse registrar(ReporteRequest request) throws IOException;
    void eliminar(Long id);
}
