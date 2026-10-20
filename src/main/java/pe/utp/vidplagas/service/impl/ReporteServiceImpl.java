package pe.utp.vidplagas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pe.utp.vidplagas.dto.request.ReporteRequest;
import pe.utp.vidplagas.dto.response.ReporteResponse;
import pe.utp.vidplagas.entity.Lote;
import pe.utp.vidplagas.entity.Plaga;
import pe.utp.vidplagas.entity.Reporte;
import pe.utp.vidplagas.exception.ResourceNotFoundException;
import pe.utp.vidplagas.mapper.ReporteMapper;
import pe.utp.vidplagas.repository.LoteRepository;
import pe.utp.vidplagas.repository.PlagaRepository;
import pe.utp.vidplagas.repository.ReporteRepository;
import pe.utp.vidplagas.service.ReporteService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;
    private final LoteRepository loteRepository;
    private final PlagaRepository plagaRepository;
    private final ReporteMapper reporteMapper;

    @Value("${app.upload-dir}")
    private String uploadDir;

    @Override
    public List<ReporteResponse> listarTodos() {
        return reporteMapper.toResponseList(reporteRepository.findAllWithRelations());
    }

    @Override
    public ReporteResponse obtenerPorId(Long id) {
        Reporte reporte = reporteRepository.findByIdWithRelations(id);
        if (reporte == null) {
            throw new ResourceNotFoundException("Reporte", "id", id);
        }
        return reporteMapper.toResponse(reporte);
    }

    @Override
    @Transactional
    public ReporteResponse registrar(ReporteRequest request) throws IOException {
        Lote lote = loteRepository.findById(request.getLoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Lote", "id", request.getLoteId()));

        Plaga plaga = plagaRepository.findById(request.getPlagaId())
                .orElseThrow(() -> new ResourceNotFoundException("Plaga", "id", request.getPlagaId()));

        Reporte reporte = Reporte.builder()
                .lote(lote)
                .plaga(plaga)
                .variedadUva(request.getVariedadUva())
                .nivelSeveridad(request.getNivelSeveridad())
                .racimosAfectados(request.getRacimosAfectados())
                .observaciones(request.getObservaciones())
                .fechaDeteccion(LocalDateTime.now())
                .evidenciaPlagaUrl(guardarArchivo(request.getEvidenciaPlaga()))
                .evidenciaRacimosUrl(guardarArchivo(request.getEvidenciaRacimos()))
                .build();

        return reporteMapper.toResponse(reporteRepository.save(reporte));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!reporteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reporte", "id", id);
        }
        reporteRepository.deleteById(id);
    }

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
