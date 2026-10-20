package pe.utp.vidplagas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.utp.vidplagas.dto.request.PlagaRequest;
import pe.utp.vidplagas.dto.response.PlagaResponse;
import pe.utp.vidplagas.service.PlagaService;

import java.util.List;

@RestController
@RequestMapping("/api/plagas")
@RequiredArgsConstructor
public class PlagaController {

    private final PlagaService plagaService;

    @GetMapping
    public ResponseEntity<List<PlagaResponse>> listarPlagas() {
        return ResponseEntity.ok(plagaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlagaResponse> obtenerPlaga(@PathVariable Long id) {
        return ResponseEntity.ok(plagaService.obtenerPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<PlagaResponse>> listarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(plagaService.listarPorTipo(tipo));
    }

    @PostMapping
    public ResponseEntity<PlagaResponse> crear(@Valid @RequestBody PlagaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plagaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlagaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody PlagaRequest request) {
        return ResponseEntity.ok(plagaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        plagaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
