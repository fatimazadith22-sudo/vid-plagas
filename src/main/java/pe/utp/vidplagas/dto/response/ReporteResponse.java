package pe.utp.vidplagas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteResponse {
    private Long id;
    private String lote;
    private String variedadUva;
    private String plagaDetectada;
    private String nivelSeveridad;
    private Integer racimosAfectados;
    private String observaciones;
    private LocalDateTime fechaDeteccion;
    private String evidenciaPlagaUrl;
    private String evidenciaRacimosUrl;
}
