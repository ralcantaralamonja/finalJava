package pe.isil.Saturno_1431.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinscripcion")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idcurso", referencedColumnName = "id")
    private Curso curso;

    private LocalDateTime fechaInscripcion;

}
