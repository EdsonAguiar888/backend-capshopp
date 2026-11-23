package com.capshopp.backend_capshopp.Repository;
import com.capshopp.backend_capshopp.Entity.Usuario;
import com.capshopp.backend_capshopp.Enums.PerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailIgnoreCase(String email);
    List<Usuario> findByPerfil(PerfilUsuario perfil);
}
