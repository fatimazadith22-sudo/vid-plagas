package pe.utp.vidplagas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Representa un reporte de campo: la deteccion de una plaga en un lote,
 * enviado desde reportar.html. A diferencia de Plaga/Lote (catalogos
 * estaticos), Reporte SI se persiste, por eso es una entidad JPA
 * (se guarda en H2 en memoria hoy; en MySQL cuando se configure el
 * datasource correspondiente, sin tocar el resto del codigo).
 *
 * La autoria (agricultor) se omite deliberadamente: cuando se integre
 * autenticacion, se tomara del usuario logueado en vez de un campo libre
 * del formulario.
 */
@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String lote;

    @NotBlank
    private String variedadUva;

    @NotBlank
    private String plagaDetectada;

    @NotBlank
    private String nivelSeveridad; // "Nivel I - Bajo" ... "Nivel IV - Critico"

    @Min(0)
    private Integer racimosAfectados;

    @Column(length = 1000)
    private String observaciones;

    /**
     * Se asigna automaticamente en el backend (LocalDateTime.now()) al
     * momento de crear el reporte; el frontend ya NO la solicita.
     */
    private LocalDateTime fechaDeteccion;

    /** Ruta publica (bajo /uploads/...) de la foto de la plaga/enfermedad detectada. */
    private String evidenciaPlagaUrl;

    /** Ruta publica (bajo /uploads/...) de la foto del racimo afectado. */
    private String evidenciaRacimosUrl;
}
