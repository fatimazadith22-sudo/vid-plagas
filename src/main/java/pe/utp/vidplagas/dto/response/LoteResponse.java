package pe.utp.vidplagas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoteResponse {
    private Long id;
    private String nombre;
    private String variedadPredominante;
    private Double hectareas;
}
