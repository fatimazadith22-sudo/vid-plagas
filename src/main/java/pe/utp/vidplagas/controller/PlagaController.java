package pe.utp.vidplagas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.utp.vidplagas.model.Plaga;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Expone los datos de plagas de la vid como JSON.
 *
 * Cumple el punto 1.3 de la rubrica:
 *  - @RestController formal en la clase.
 *  - Metodos con @GetMapping que retornan directamente cadenas,
 *    objetos de entidad o listas de elementos.
 *
 * Probar con curl:
 *   curl http://localhost:8080/api/estado
 *   curl http://localhost:8080/api/plagas
 *   curl http://localhost:8080/api/plagas/3
 *   curl http://localhost:8080/api/plagas/tipo/Hongo
 */
@RestController
@RequestMapping("/api")
public class PlagaController {

    private final List<Plaga> plagas = cargarPlagas();

    @GetMapping("/estado")
    public String estado() {
        return "API vid-plagas activa. Endpoints: /api/plagas, /api/plagas/{id}, /api/plagas/tipo/{tipo}, /api/lotes, /api/reportes";
    }

    @GetMapping("/plagas")
    public List<Plaga> listarPlagas() {
        return plagas;
    }

    @GetMapping("/plagas/{id}")
    public Plaga obtenerPlaga(@PathVariable Long id) {
        Optional<Plaga> encontrada = plagas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return encontrada.orElse(null);
    }

    @GetMapping("/plagas/tipo/{tipo}")
    public List<Plaga> listarPorTipo(@PathVariable String tipo) {
        List<Plaga> resultado = new ArrayList<>();
        for (Plaga p : plagas) {
            if (p.getTipo().equalsIgnoreCase(tipo)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    private static List<Plaga> cargarPlagas() {
        List<Plaga> lista = new ArrayList<>();

        lista.add(Plaga.builder()
                .id(1L).nombreComun("Polilla de la vid").nombreCientifico("Lobesia botrana")
                .tipo("Insecto")
                .sintomas("Larvas que perforan los granos de uva y tejen hilos de seda entre ellos; " +
                        "los racimos afectados muestran granos vaciados y excrementos visibles.")
                .nivelDano("Alto").imagen("polilla-vid.jpg")
                .recomendacion("Instalar trampas de feromonas, monitorear vuelo de adultos y aplicar " +
                        "control biologico con Bacillus thuringiensis en los primeros estadios larvales.")
                .build());

        lista.add(Plaga.builder()
                .id(2L).nombreComun("Oidio").nombreCientifico("Erysiphe necator")
                .tipo("Hongo")
                .sintomas("Polvo blanco grisaceo sobre hojas, brotes y granos; los granos afectados se " +
                        "agrietan y detienen su crecimiento.")
                .nivelDano("Alto").imagen("oidio.jpg")
                .recomendacion("Aplicar azufre mojable o fungicidas sistemicos preventivos, mejorar la " +
                        "aireacion del canopy mediante deshoje.")
                .build());

        lista.add(Plaga.builder()
                .id(3L).nombreComun("Mildiu").nombreCientifico("Plasmopara viticola")
                .tipo("Hongo")
                .sintomas("Manchas aceitosas amarillentas en el haz de la hoja y pelusa blanca en el " +
                        "envés; en racimos jóvenes provoca podredumbre parda.")
                .nivelDano("Alto").imagen("mildiu.jpg")
                .recomendacion("Aplicar fungicidas cupricos o sistemicos antes de lluvias, evitar exceso " +
                        "de humedad en el follaje.")
                .build());

        lista.add(Plaga.builder()
                .id(4L).nombreComun("Botrytis / Moho gris").nombreCientifico("Botrytis cinerea")
                .tipo("Hongo")
                .sintomas("Micelio gris aterciopelado que cubre los granos, ablandandolos y provocando " +
                        "su caida; avanza rapido con humedad alta.")
                .nivelDano("Medio").imagen("botrytis.jpg")
                .recomendacion("Deshoje en la zona de racimos para mejorar ventilacion, controlar el " +
                        "riego y aplicar fungicidas especificos en floracion y envero.")
                .build());

        lista.add(Plaga.builder()
                .id(5L).nombreComun("Chanchito blanco").nombreCientifico("Planococcus ficus")
                .tipo("Insecto")
                .sintomas("Colonias algodonosas en la base del racimo y axilas; excretan melaza que " +
                        "favorece la aparicion de fumagina (hongo negro).")
                .nivelDano("Medio").imagen("chanchito-blanco.jpg")
                .recomendacion("Control biologico con parasitoides (Anagyrus sp.), aplicaciones de " +
                        "aceite mineral en reposo invernal.")
                .build());

        lista.add(Plaga.builder()
                .id(6L).nombreComun("Arana roja").nombreCientifico("Tetranychus urticae")
                .tipo("Acaro")
                .sintomas("Punteado clorotico en el haz de la hoja y finas telarañas en el envés; en " +
                        "ataques severos las hojas se broncean y caen.")
                .nivelDano("Bajo").imagen("arana-roja.jpg")
                .recomendacion("Favorecer fauna benefica (acaros depredadores), aplicar acaricidas solo " +
                        "si se supera el umbral de daño economico.")
                .build());

        lista.add(Plaga.builder()
                .id(7L).nombreComun("Trips").nombreCientifico("Frankliniella occidentalis")
                .tipo("Insecto")
                .sintomas("Cicatrices corchosas y deformaciones en la piel del grano; raspaduras " +
                        "plateadas visibles en hojas jovenes.")
                .nivelDano("Bajo").imagen("trips.jpg")
                .recomendacion("Uso de trampas cromaticas azules, control biologico con Orius sp. y " +
                        "monitoreo en floracion.")
                .build());

        return lista;
    }
}
