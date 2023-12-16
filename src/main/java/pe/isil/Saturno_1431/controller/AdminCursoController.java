package pe.isil.Saturno_1431.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.isil.Saturno_1431.model.Curso;
import pe.isil.Saturno_1431.repository.CursoRepository;
import pe.isil.Saturno_1431.service.FileSystemStorageService;

@Controller
@RequestMapping("/admin/cursos")
public class AdminCursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @GetMapping("") //localhost:8080/admin/cursos
        // mostrar la pagina inicial administrativa de los cursos
    String index(Model model,
                 @PageableDefault(size = 5, sort = "nombre") Pageable pageable,
                 @RequestParam(required = false) String nombre)
    {
        Page<Curso> cursos;
        if (nombre != null && !nombre.trim().isEmpty())
        {
            cursos = cursoRepository.findByNombreContaining(nombre, pageable);
        }else{
            cursos = cursoRepository.findAll(pageable);
        }
        model.addAttribute("cursos", cursos);
        return "admin/cursos/index";
    }

    //NUEVO: Mostrar un formulario para registrar un nuevo curso
    //GET
    @GetMapping("/nuevo") //localhost:8080/cursos/nuevo
    String nuevo(Model model){
        //se agrega como atributo un nuave instancia o objecto de de la clase Curso
        model.addAttribute("curso", new Curso());
        return "admin/cursos/nuevo"; //hacia una VISTA o HTML
    }

    //CREAR: REGISTRAR UN NUEVO CURSO: INSERT INTO curso
    //POST
    @PostMapping("/nuevo") //localhost:8080/cursos/nuevo
    String crear(Model model, @Validated Curso curso, BindingResult bindingResult, RedirectAttributes ra){
        if (curso.getImagen().isEmpty()){
            bindingResult.rejectValue("imagen", "MultipartNotEmpty");
        }
        if (bindingResult.hasErrors()){
            model.addAttribute("curso", curso);
            return "admin/cursos/nuevo";
        }
        String rutaImagen = fileSystemStorageService.store(curso.getImagen());
        curso.setRutaImagen(rutaImagen);
        curso.setId(0);
        cursoRepository.save(curso);
        ra.addFlashAttribute("msgExito", "El curso se creó exitosamente");
        return "redirect:/admin/cursos"; //hacia una RUTA URL
    }

    //EDITAR: OBTENER LOS DATOS DEL CURSO POR SU ID
    @GetMapping("/editar/{id}")
    String editar(Model model, @PathVariable("id") Integer id){
        Curso curso = cursoRepository.getById(id);
        model.addAttribute("curso", curso);
        return "admin/cursos/editar";
    }

    //ACTUALIZAR UN CURSO: UPDATE curso
    //POST
    @PostMapping("/editar/{id}")
    String actualizar(Model model,@Validated Curso curso, BindingResult bindingResult, @PathVariable("id") Integer id, RedirectAttributes ra){
        if (bindingResult.hasErrors()){
            model.addAttribute("curso", curso);
            return "admin/cursos/editar";
        }

        //1. obtener el curso por el id de la base de datos
        Curso cursoFromDB = cursoRepository.getById(id);

        //2. setear los campos a modificar
        cursoFromDB.setArea(curso.getArea());
        cursoFromDB.setCreditos(curso.getCreditos());
        cursoFromDB.setHoras(curso.getHoras());
        cursoFromDB.setNombre(curso.getNombre());
        cursoFromDB.setDescripcion(curso.getDescripcion());
        cursoFromDB.setNrc(curso.getNrc());
        cursoFromDB.setModalidad(curso.getModalidad());
        cursoFromDB.setPrecio(curso.getPrecio());

        //3. guardamos en bd
        cursoRepository.save(cursoFromDB);
        ra.addFlashAttribute("msgExito", "El curso se actualizó exitosamente");
        return "redirect:/admin/cursos";
    }

    //ELIMINAR UN CURSO: DELETE FROM curso
    //POST
    @PostMapping("/eliminar/{id}")
    String eliminar(@PathVariable("id") Integer id, RedirectAttributes ra){
        cursoRepository.deleteById(id);
        ra.addFlashAttribute("msgExito", "El curso fué eliminado exitosamente");
        return "redirect:/admin/cursos";
    }

}
