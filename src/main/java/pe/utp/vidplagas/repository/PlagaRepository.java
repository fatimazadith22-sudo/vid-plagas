package pe.utp.vidplagas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.vidplagas.entity.Plaga;

import java.util.List;
import java.util.Optional;

public interface PlagaRepository extends JpaRepository<Plaga, Long> {
    Optional<Plaga> findByNombreComun(String nombreComun);
    List<Plaga> findByTipoIgnoreCase(String tipo);
}
