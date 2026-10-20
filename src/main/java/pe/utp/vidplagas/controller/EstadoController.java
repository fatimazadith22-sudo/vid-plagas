package pe.utp.vidplagas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class EstadoController {

    @GetMapping("/estado")
    public Map<String, Object> estado() {
        return Map.of(
                "mensaje", "API vid-plagas activa",
                "endpoints", new String[]{
                        "/api/plagas", "/api/plagas/{id}", "/api/plagas/tipo/{tipo}",
                        "/api/lotes", "/api/lotes/{id}",
                        "/api/reportes", "/api/reportes/{id}"
                },
                "version", "1.0.0"
        );
    }
}
