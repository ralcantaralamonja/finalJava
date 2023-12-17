package pe.isil.Saturno_1431.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.isil.Saturno_1431.model.Inscripcion;
import pe.isil.Saturno_1431.model.Promedio;

import java.util.List;

@Repository
public interface PromedioRepository extends JpaRepository<Promedio,Long> {

    List<Promedio> findByCurso_Id(Integer idCurso);

}
