package pe.isil.Saturno_1431.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.isil.Saturno_1431.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //select * from usuario where email like '%email:%'
    Page<Usuario> findByEmailContaining(String email, Pageable pageable);

    //select * from usuario where exist(email)
    boolean existsByEmail(String email);

    //select * from usuario where email = ?
    Optional<Usuario> findByEmail(String email);
}
