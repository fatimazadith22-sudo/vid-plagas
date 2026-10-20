package pe.utp.vidplagas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.utp.vidplagas.entity.Reporte;

import java.util.List;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    @Query("SELECT r FROM Reporte r LEFT JOIN FETCH r.lote LEFT JOIN FETCH r.plaga")
    List<Reporte> findAllWithRelations();

    @Query("SELECT r FROM Reporte r LEFT JOIN FETCH r.lote LEFT JOIN FETCH r.plaga WHERE r.id = :id")
    Reporte findByIdWithRelations(@Param("id") Long id);
}
