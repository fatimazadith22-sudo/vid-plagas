package pe.utp.vidplagas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.utp.vidplagas.model.Lote;

import java.util.List;
@RestController
@RequestMapping("/api/lotes")
public class LoteController {

    private final List<Lote> lotes = List.of(
            Lote.builder().id(1L).nombre("Lote 1 - Sector Norte").variedadPredominante("Red Globe").hectareas(3.2).build(),
            Lote.builder().id(2L).nombre("Lote 2 - Sector Sur").variedadPredominante("Quebranta").hectareas(2.8).build(),
            Lote.builder().id(3L).nombre("Lote 3 - Ladera Este").variedadPredominante("Malbec").hectareas(1.9).build(),
            Lote.builder().id(4L).nombre("Lote 4 - Sector Central").variedadPredominante("Italia").hectareas(4.0).build(),
            Lote.builder().id(5L).nombre("Lote 5 - Vivero").variedadPredominante("Sultanina").hectareas(1.1).build()
    );

    @GetMapping
    public List<Lote> listarLotes() {
        return lotes;
    }
}
