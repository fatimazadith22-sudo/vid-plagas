package pe.utp.vidplagas.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteRequest {
    @NotNull(message = "El id del lote es obligatorio")
    private Long loteId;

    @NotNull(message = "El id de la plaga es obligatorio")
    private Long plagaId;

    @NotNull(message = "La variedad de uva es obligatoria")
    private String variedadUva;

    @NotNull(message = "El nivel de severidad es obligatorio")
    private String nivelSeveridad;

    @NotNull(message = "El número de racimos afectados es obligatorio")
    @Min(value = 0, message = "El número de racimos afectados debe ser mayor o igual a 0")
    private Integer racimosAfectados;

    private String observaciones;

    private MultipartFile evidenciaPlaga;
    private MultipartFile evidenciaRacimos;
}
