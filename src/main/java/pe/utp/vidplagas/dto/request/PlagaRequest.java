package pe.utp.vidplagas.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlagaRequest {
    @NotBlank(message = "El nombre común es obligatorio")
    private String nombreComun;

    @NotBlank(message = "El nombre científico es obligatorio")
    private String nombreCientifico;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotBlank(message = "Los síntomas son obligatorios")
    private String sintomas;

    @NotBlank(message = "El nivel de daño es obligatorio")
    private String nivelDano;

    private String imagen;

    @NotBlank(message = "La recomendación es obligatoria")
    private String recomendacion;
}
