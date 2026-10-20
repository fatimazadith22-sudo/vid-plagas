package pe.utp.vidplagas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.vidplagas.entity.Lote;

import java.util.Optional;

public interface LoteRepository extends JpaRepository<Lote, Long> {
    Optional<Lote> findByNombre(String nombre);
}
