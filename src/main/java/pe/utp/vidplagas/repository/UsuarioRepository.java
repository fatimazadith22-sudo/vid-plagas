package pe.utp.vidplagas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.vidplagas.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
