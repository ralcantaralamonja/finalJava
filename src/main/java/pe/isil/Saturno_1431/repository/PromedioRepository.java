package pe.isil.Saturno_1431.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.isil.Saturno_1431.model.Promedio;

@Repository
public interface PromedioRepository extends JpaRepository<Promedio,Long> {
}
