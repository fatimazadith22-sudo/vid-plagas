package pe.utp.vidplagas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlagaResponse {
    private Long id;
    private String nombreComun;
    private String nombreCientifico;
    private String tipo;
    private String sintomas;
    private String nivelDano;
    private String imagen;
    private String recomendacion;
}
