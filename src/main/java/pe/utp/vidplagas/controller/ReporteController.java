package pe.utp.vidplagas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import pe.utp.vidplagas.model.Reporte;
import pe.utp.vidplagas.repository.ReporteRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Recibe y expone los reportes de deteccion de plagas enviados desde
 * reportar.html.
 *
 * Cambios respecto a la version anterior:
 *  - Los reportes ahora se persisten con Spring Data JPA (ReporteRepository)
 *    en lugar de una lista en memoria.
 *  - "fechaDeteccion" ya NO viene del formulario: se asigna aqui con
 *    LocalDateTime.now() al momento de registrar el reporte.
 *  - El registro (POST) es multipart/form-data porque incluye hasta dos
 *    archivos de evidencia fotografica (plaga y racimo afectado).
 *
 * Probar el listado con curl:
 *   curl http://localhost:8080/api/reportes
 *
 * Probar el registro con curl (multipart, sin necesidad de fecha):
 *   curl -X POST http://localhost:8080/api/reportes ^
 *        -F "lote=Lote 1 - Sector Norte" ^
 *        -F "variedadUva=Red Globe" ^
 *        -F "plagaDetectada=Oidio" ^
 *        -F "nivelSeveridad=Nivel II - Medio" ^
 *        -F "racimosAfectados=12" ^
 *        -F "observaciones=Visto en hilera 3" ^
 *        -F "evidenciaPlaga=@C:\ruta\foto-plaga.jpg" ^
 *        -F "evidenciaRacimos=@C:\ruta\foto-racimo.jpg"
 */
@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteRepository reporteRepository;

    @Value("${app.upload-dir}")
    private String uploadDir;

    @GetMapping
    public List<Reporte> listarReportes() {
        return reporteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Reporte obtenerReporte(@PathVariable Long id) {
        return reporteRepository.findById(id).orElse(null);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Reporte registrarReporte(
            @RequestParam String lote,
            @RequestParam String variedadUva,
            @RequestParam String plagaDetectada,
            @RequestParam String nivelSeveridad,
            @RequestParam Integer racimosAfectados,
            @RequestParam(required = false) String observaciones,
            @RequestParam(required = false) MultipartFile evidenciaPlaga,
            @RequestParam(required = false) MultipartFile evidenciaRacimos) throws IOException {

        Reporte reporte = Reporte.builder()
                .lote(lote)
                .variedadUva(variedadUva)
                .plagaDetectada(plagaDetectada)
                .nivelSeveridad(nivelSeveridad)
                .racimosAfectados(racimosAfectados)
                .observaciones(observaciones)
                .fechaDeteccion(LocalDateTime.now())
                .evidenciaPlagaUrl(guardarArchivo(evidenciaPlaga))
                .evidenciaRacimosUrl(guardarArchivo(evidenciaRacimos))
                .build();

        return reporteRepository.save(reporte);
    }

    /** Guarda el archivo recibido en {app.upload-dir} y retorna su ruta publica (/uploads/...). */
    private String guardarArchivo(MultipartFile archivo) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }

        Path directorio = Paths.get(uploadDir);
        Files.createDirectories(directorio);

        String nombreOriginal = archivo.getOriginalFilename() != null ? archivo.getOriginalFilename() : "archivo";
        String nombreUnico = UUID.randomUUID() + "_" + nombreOriginal.replaceAll("\\s+", "_");
        Path destino = directorio.resolve(nombreUnico);

        archivo.transferTo(destino);
        return "/uploads/" + nombreUnico;
    }
}
