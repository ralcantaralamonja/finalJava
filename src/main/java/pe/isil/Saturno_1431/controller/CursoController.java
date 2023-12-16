package pe.isil.Saturno_1431.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.isil.Saturno_1431.model.Curso;
import pe.isil.Saturno_1431.repository.CursoRepository;

import java.util.List;

@Controller
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository; //DAO-Curso

    @GetMapping("")
    String index(Model model){
        //obtener los ultimos 5 cursos creados
        List<Curso> ultimosCursos = cursoRepository.findTop5ByOrderByFechaCreacionDesc();

        model.addAttribute("ultimosCursos", ultimosCursos);
        return "index";
    }
    //tarea crear un carrusel en la vista index con boostrap


    @GetMapping("/cursos")
    String getCursos(Model model,
                     @PageableDefault(size = 8, sort = "nombre") Pageable pageable
                     )
    {
        Page<Curso> cursos = cursoRepository.findAll(pageable);
        model.addAttribute("cursos", cursos);

        return "lista-cursos";
    }

    @GetMapping("/cursos/{id}")
    String getCurso(@PathVariable Integer id, Model model ){
        Curso curso = cursoRepository.getById(id);
        model.addAttribute("curso", curso);
        return "detalle-curso";
    }
}
