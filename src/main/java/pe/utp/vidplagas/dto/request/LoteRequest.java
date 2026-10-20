package pe.utp.vidplagas.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoteRequest {
    @NotBlank(message = "El nombre del lote es obligatorio")
    private String nombre;

    @NotBlank(message = "La variedad predominante es obligatoria")
    private String variedadPredominante;

    @NotNull(message = "Las hectáreas son obligatorias")
    @Min(value = 0, message = "Las hectáreas deben ser mayores o iguales a 0")
    private Double hectareas;
}
