package pe.utp.vidplagas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reportes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"lote", "plaga"})
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String variedadUva;

    @NotBlank
    @Column(name = "nivel_severidad", nullable = false)
    private String nivelSeveridad;

    @NotNull
    @Min(0)
    @Column(name = "racimos_afectados", nullable = false)
    private Integer racimosAfectados;

    @Column(length = 1000)
    private String observaciones;

    @Column(name = "fecha_deteccion", nullable = false)
    private LocalDateTime fechaDeteccion;

    @Column(name = "evidencia_plaga_url")
    private String evidenciaPlagaUrl;

    @Column(name = "evidencia_racimos_url")
    private String evidenciaRacimosUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lote_id", nullable = false)
    private Lote lote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plaga_id", nullable = false)
    private Plaga plaga;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reporte reporte)) {
            return false;
        }
        return id != null && Objects.equals(id, reporte.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
