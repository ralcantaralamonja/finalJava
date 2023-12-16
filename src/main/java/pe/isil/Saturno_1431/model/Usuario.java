package pe.isil.Saturno_1431.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer id;

    @NotBlank
    private String nombres;
    @NotBlank
    private String apellidos;
    @Column(name = "nom_completo")
    private String nombreCompleto;

    @NotEmpty
    @Email
    private String email;

    private String password;

    @NotBlank
    @Transient
    private String password1;

    @NotBlank
    @Transient
    private String password2;

    public enum Rol{
        ADMIN,
        ALUMNO
    }

    private Rol rol;

    @PrePersist // se ejecuta automaticamente antes de insertar un nuevo usuario
    @PreUpdate // se ejecuta automaticamente antes de actualizar un usuario
    void asignarNombreCompleto(){
        nombreCompleto = nombres + " " + apellidos;
    }

}
