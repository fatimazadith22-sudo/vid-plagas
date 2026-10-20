package pe.utp.vidplagas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.utp.vidplagas.entity.Lote;
import pe.utp.vidplagas.entity.Plaga;
import pe.utp.vidplagas.entity.Usuario;
import pe.utp.vidplagas.entity.enums.Role;
import pe.utp.vidplagas.repository.LoteRepository;
import pe.utp.vidplagas.repository.PlagaRepository;
import pe.utp.vidplagas.repository.UsuarioRepository;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final PlagaRepository plagaRepository;
    private final LoteRepository loteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (plagaRepository.count() == 0) {
                plagaRepository.saveAll(cargarPlagas());
            }
            if (loteRepository.count() == 0) {
                loteRepository.saveAll(cargarLotes());
            }
            if (usuarioRepository.count() == 0 && !usuarioRepository.existsByEmail("admin@vidplagas.com")) {
                usuarioRepository.save(cargarAdmin());
            }
        };
    }

    private Usuario cargarAdmin() {
        return Usuario.builder()
                .username("admin")
                .email("admin@vidplagas.com")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .enabled(true)
                .build();
    }

    private List<Plaga> cargarPlagas() {
        return List.of(
                Plaga.builder()
                        .nombreComun("Polilla de la vid")
                        .nombreCientifico("Lobesia botrana")
                        .tipo("Insecto")
                        .sintomas("Larvas que perforan los granos de uva y tejen hilos de seda entre ellos; los racimos afectados muestran granos vaciados y excrementos visibles.")
                        .nivelDano("Alto")
                        .imagen("polilla-vid.jpg")
                        .recomendacion("Instalar trampas de feromonas, monitorear vuelo de adultos y aplicar control biologico con Bacillus thuringiensis en los primeros estadios larvales.")
                        .build(),
                Plaga.builder()
                        .nombreComun("Oidio")
                        .nombreCientifico("Erysiphe necator")
                        .tipo("Hongo")
                        .sintomas("Polvo blanco grisaceo sobre hojas, brotes y granos; los granos afectados se agrietan y detienen su crecimiento.")
                        .nivelDano("Alto")
                        .imagen("oidio.jpg")
                        .recomendacion("Aplicar azufre mojable o fungicidas sistemicos preventivos, mejorar la aireacion del canopy mediante deshoje.")
                        .build(),
                Plaga.builder()
                        .nombreComun("Mildiu")
                        .nombreCientifico("Plasmopara viticola")
                        .tipo("Hongo")
                        .sintomas("Manchas aceitosas amarillentas en el haz de la hoja y pelusa blanca en el envés; en racimos jóvenes provoca podredumbre parda.")
                        .nivelDano("Alto")
                        .imagen("mildiu.jpg")
                        .recomendacion("Aplicar fungicidas cupricos o sistemicos antes de lluvias, evitar exceso de humedad en el follaje.")
                        .build(),
                Plaga.builder()
                        .nombreComun("Botrytis / Moho gris")
                        .nombreCientifico("Botrytis cinerea")
                        .tipo("Hongo")
                        .sintomas("Micelio gris aterciopelado que cubre los granos, ablandandolos y provocando su caida; avanza rapido con humedad alta.")
                        .nivelDano("Medio")
                        .imagen("botrytis.jpg")
                        .recomendacion("Deshoje en la zona de racimos para mejorar ventilacion, controlar el riego y aplicar fungicidas especificos en floracion y envero.")
                        .build(),
                Plaga.builder()
                        .nombreComun("Chanchito blanco")
                        .nombreCientifico("Planococcus ficus")
                        .tipo("Insecto")
                        .sintomas("Colonias algodonosas en la base del racimo y axilas; excretan melaza que favorece la aparicion de fumagina (hongo negro).")
                        .nivelDano("Medio")
                        .imagen("chanchito-blanco.jpg")
                        .recomendacion("Control biologico con parasitoides (Anagyrus sp.), aplicaciones de aceite mineral en reposo invernal.")
                        .build(),
                Plaga.builder()
                        .nombreComun("Arana roja")
                        .nombreCientifico("Tetranychus urticae")
                        .tipo("Acaro")
                        .sintomas("Punteado clorotico en el haz de la hoja y finas telarañas en el envés; en ataques severos las hojas se broncean y caen.")
                        .nivelDano("Bajo")
                        .imagen("arana-roja.jpg")
                        .recomendacion("Favorecer fauna benefica (acaros depredadores), aplicar acaricidas solo si se supera el umbral de daño economico.")
                        .build(),
                Plaga.builder()
                        .nombreComun("Trips")
                        .nombreCientifico("Frankliniella occidentalis")
                        .tipo("Insecto")
                        .sintomas("Cicatrices corchosas y deformaciones en la piel del grano; raspaduras plateadas visibles en hojas jovenes.")
                        .nivelDano("Bajo")
                        .imagen("trips.jpg")
                        .recomendacion("Uso de trampas cromaticas azules, control biologico con Orius sp. y monitoreo en floracion.")
                        .build()
        );
    }

    private List<Lote> cargarLotes() {
        return List.of(
                Lote.builder().nombre("Lote 1 - Sector Norte").variedadPredominante("Red Globe").hectareas(3.2).build(),
                Lote.builder().nombre("Lote 2 - Sector Sur").variedadPredominante("Quebranta").hectareas(2.8).build(),
                Lote.builder().nombre("Lote 3 - Ladera Este").variedadPredominante("Malbec").hectareas(1.9).build(),
                Lote.builder().nombre("Lote 4 - Sector Central").variedadPredominante("Italia").hectareas(4.0).build(),
                Lote.builder().nombre("Lote 5 - Vivero").variedadPredominante("Sultanina").hectareas(1.1).build()
        );
    }
}
