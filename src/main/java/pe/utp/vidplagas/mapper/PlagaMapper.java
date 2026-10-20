package pe.utp.vidplagas.mapper;

import org.springframework.stereotype.Component;
import pe.utp.vidplagas.dto.request.PlagaRequest;
import pe.utp.vidplagas.dto.response.PlagaResponse;
import pe.utp.vidplagas.entity.Plaga;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlagaMapper {

    public Plaga toEntity(PlagaRequest request) {
        if (request == null) {
            return null;
        }
        return Plaga.builder()
                .nombreComun(request.getNombreComun())
                .nombreCientifico(request.getNombreCientifico())
                .tipo(request.getTipo())
                .sintomas(request.getSintomas())
                .nivelDano(request.getNivelDano())
                .imagen(request.getImagen())
                .recomendacion(request.getRecomendacion())
                .build();
    }

    public PlagaResponse toResponse(Plaga plaga) {
        if (plaga == null) {
            return null;
        }
        return PlagaResponse.builder()
                .id(plaga.getId())
                .nombreComun(plaga.getNombreComun())
                .nombreCientifico(plaga.getNombreCientifico())
                .tipo(plaga.getTipo())
                .sintomas(plaga.getSintomas())
                .nivelDano(plaga.getNivelDano())
                .imagen(plaga.getImagen())
                .recomendacion(plaga.getRecomendacion())
                .build();
    }

    public List<PlagaResponse> toResponseList(List<Plaga> plagas) {
        return plagas == null ? List.of() : plagas.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
