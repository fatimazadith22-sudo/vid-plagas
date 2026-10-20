package pe.utp.vidplagas.mapper;

import org.springframework.stereotype.Component;
import pe.utp.vidplagas.dto.request.LoteRequest;
import pe.utp.vidplagas.dto.response.LoteResponse;
import pe.utp.vidplagas.entity.Lote;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoteMapper {

    public Lote toEntity(LoteRequest request) {
        if (request == null) {
            return null;
        }
        return Lote.builder()
                .nombre(request.getNombre())
                .variedadPredominante(request.getVariedadPredominante())
                .hectareas(request.getHectareas())
                .build();
    }

    public LoteResponse toResponse(Lote lote) {
        if (lote == null) {
            return null;
        }
        return LoteResponse.builder()
                .id(lote.getId())
                .nombre(lote.getNombre())
                .variedadPredominante(lote.getVariedadPredominante())
                .hectareas(lote.getHectareas())
                .build();
    }

    public List<LoteResponse> toResponseList(List<Lote> lotes) {
        return lotes == null ? List.of() : lotes.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
