package pe.isil.Saturno_1431.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.isil.Saturno_1431.model.Inscripcion;
import pe.isil.Saturno_1431.model.Usuario;

import java.util.List;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {

    List<Inscripcion> findByUsuario(Usuario usuario);
}
