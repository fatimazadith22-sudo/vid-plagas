package pe.utp.vidplagas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.utp.vidplagas.model.Reporte;

/**
 * Spring Data JPA genera la implementacion en tiempo de ejecucion:
 * save(), findAll(), findById(), deleteById(), etc.
 */
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
}
