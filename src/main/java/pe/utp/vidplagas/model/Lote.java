package pe.utp.vidplagas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un lote (parcela) del viñedo. Catalogo estatico en memoria,
 * igual que Plaga; se muestra como <select> en el formulario de reporte.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lote {

    private Long id;
    private String nombre;               // Ej. "Lote 1 - Sector Norte"
    private String variedadPredominante; // Ej. "Red Globe"
    private Double hectareas;
}
