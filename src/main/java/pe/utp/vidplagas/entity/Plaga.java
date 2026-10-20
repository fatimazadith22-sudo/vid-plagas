package pe.utp.vidplagas.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "plagas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "reportes")
public class Plaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nombre_comun", nullable = false, unique = true)
    private String nombreComun;

    @NotBlank
    @Column(name = "nombre_cientifico", nullable = false)
    private String nombreCientifico;

    @NotBlank
    @Column(nullable = false)
    private String tipo;

    @NotBlank
    @Column(length = 1000, nullable = false)
    private String sintomas;

    @NotBlank
    @Column(name = "nivel_dano", nullable = false)
    private String nivelDano;

    private String imagen;

    @NotBlank
    @Column(length = 1000, nullable = false)
    private String recomendacion;

    @Builder.Default
    @OneToMany(mappedBy = "plaga", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reporte> reportes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plaga plaga)) {
            return false;
        }
        return id != null && Objects.equals(id, plaga.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
