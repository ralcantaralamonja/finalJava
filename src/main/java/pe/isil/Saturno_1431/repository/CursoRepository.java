package pe.isil.Saturno_1431.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.isil.Saturno_1431.model.Curso;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    //SELECT * FROM cursos WHERE nombre  like '%?%'
    Page<Curso> findByNombreContaining(String nombre, Pageable pageable);

    //SELECT * FROM cursos WHERE nrc  like '%?%'
    Page<Curso> findByNrcContaining(String nrc, Pageable pageable);

    //listar los 5 cursos por fecha de creacion: SELECT TOP 5 * FROM curso ORDER BY fecha_creacion
    List<Curso> findTop5ByOrderByFechaCreacionDesc();

}
