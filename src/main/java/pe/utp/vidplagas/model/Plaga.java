package pe.utp.vidplagas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una plaga o enfermedad que afecta los racimos de uva.
 * Es un catalogo estatico en memoria (no se persiste en BD), por eso no
 * lleva anotaciones JPA. Lombok genera getters, setters, constructores,
 * equals/hashCode y toString a partir de las anotaciones de clase.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plaga {

    private Long id;
    private String nombreComun;
    private String nombreCientifico;
    private String tipo;          // Hongo, Insecto, Acaro
    private String sintomas;
    private String nivelDano;     // Bajo, Medio, Alto
    private String imagen;        // nombre de archivo en /img
    private String recomendacion;
}
