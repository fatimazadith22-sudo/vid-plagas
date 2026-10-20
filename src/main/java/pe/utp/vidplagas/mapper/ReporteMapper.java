package pe.utp.vidplagas.mapper;

import org.springframework.stereotype.Component;
import pe.utp.vidplagas.dto.response.ReporteResponse;
import pe.utp.vidplagas.entity.Reporte;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReporteMapper {

    public ReporteResponse toResponse(Reporte reporte) {
        if (reporte == null) {
            return null;
        }
        return ReporteResponse.builder()
                .id(reporte.getId())
                .lote(reporte.getLote() != null ? reporte.getLote().getNombre() : null)
                .variedadUva(reporte.getVariedadUva())
                .plagaDetectada(reporte.getPlaga() != null ? reporte.getPlaga().getNombreComun() : null)
                .nivelSeveridad(reporte.getNivelSeveridad())
                .racimosAfectados(reporte.getRacimosAfectados())
                .observaciones(reporte.getObservaciones())
                .fechaDeteccion(reporte.getFechaDeteccion())
                .evidenciaPlagaUrl(reporte.getEvidenciaPlagaUrl())
                .evidenciaRacimosUrl(reporte.getEvidenciaRacimosUrl())
                .build();
    }

    public List<ReporteResponse> toResponseList(List<Reporte> reportes) {
        return reportes == null ? List.of() : reportes.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
