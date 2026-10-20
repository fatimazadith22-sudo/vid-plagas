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
import pe.utp.vidplagas.dto.request.LoteRequest;
import pe.utp.vidplagas.dto.response.LoteResponse;
import pe.utp.vidplagas.service.LoteService;

import java.util.List;

@RestController
@RequestMapping("/api/lotes")
@RequiredArgsConstructor
public class LoteController {

    private final LoteService loteService;

    @GetMapping
    public ResponseEntity<List<LoteResponse>> listarLotes() {
        return ResponseEntity.ok(loteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoteResponse> obtenerLote(@PathVariable Long id) {
        return ResponseEntity.ok(loteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<LoteResponse> crear(@Valid @RequestBody LoteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loteService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoteResponse> actualizar(@PathVariable Long id, @Valid @RequestBody LoteRequest request) {
        return ResponseEntity.ok(loteService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        loteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
