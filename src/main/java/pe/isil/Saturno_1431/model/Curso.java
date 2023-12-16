package pe.isil.Saturno_1431.model;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Curso {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincremental
    private Integer id;

    @Column(unique = true)
    @NotBlank
    @NotNull
    @Size(max = 4, min = 4)
    private String nrc;

    @NotNull
    @Size(max = 50, min = 2)
    private String nombre;

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String descripcion;

    @NotNull
    @Min(1)
    @Max(4)
    private Integer creditos;

    @NotNull
    @Min(1)
    @Max(4)
    private Integer horas;

    @NotNull
    @NotBlank
    private String modalidad;

    @NotNull
    @NotBlank
    private String area;
    private LocalDateTime fechaCreacion; //fechaCreacion
    private LocalDateTime fechaActualizacion; //fechaActualizacion

    @PrePersist //pre(antes de insertar asignar el valor en la fecha de creacion)
    void prePersist(){
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate //pre(antes de actualizar asignar el valor en la fecha de actualizacion)
    void preUpdate(){
        fechaActualizacion = LocalDateTime.now();
    }

    private String rutaImagen;

    @Transient //esta anatocion realiza el campo solo sea a nivel de aplicacion
    private MultipartFile imagen;

    @NotNull
    @Min(10)
    @Max(500)
    private float precio;

}
