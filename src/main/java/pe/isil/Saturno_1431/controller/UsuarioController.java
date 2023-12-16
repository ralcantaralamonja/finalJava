package pe.isil.Saturno_1431.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.isil.Saturno_1431.model.Curso;
import pe.isil.Saturno_1431.model.Inscripcion;
import pe.isil.Saturno_1431.model.Usuario;
import pe.isil.Saturno_1431.repository.CursoRepository;
import pe.isil.Saturno_1431.repository.InscripcionRepository;
import pe.isil.Saturno_1431.repository.UsuarioRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private InscripcionRepository inscripcionRepository;

    @GetMapping("/usuario/inscribir/{id}")
    String inscribir(@PathVariable("id") Integer id_curso, Principal principal){
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
        Curso curso = cursoRepository.getById(id_curso);
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setCurso(curso);
        inscripcion.setFechaInscripcion(LocalDateTime.now());
        inscripcionRepository.save(inscripcion);
        return "redirect:/cursos";
    }

    @GetMapping("/usuario/mis-cursos")
    String misCursos(Principal principal, Model model){
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).get();
        List<Inscripcion> inscripciones = inscripcionRepository.findByUsuario(usuario);
        model.addAttribute("inscripciones", inscripciones);
        return "mis-cursos";
    }
}
