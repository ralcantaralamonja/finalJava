package pe.isil.Saturno_1431.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.isil.Saturno_1431.model.Curso;
import pe.isil.Saturno_1431.model.Promedio;
import pe.isil.Saturno_1431.model.Usuario;
import pe.isil.Saturno_1431.repository.CursoRepository;
import pe.isil.Saturno_1431.repository.PromedioRepository;
import pe.isil.Saturno_1431.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Controller

public class PromedioController {
    @Autowired
    PromedioRepository promedioRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CursoRepository cursoRepository;
    @GetMapping("/promedio")
    public String InsertarNotas(Model model){
        model.addAttribute("promedio",new Promedio());
        return "promedio";
    }
    @PostMapping("/promedio")
    public String calcular(Model model,
                           @Validated Promedio promedio,
                           @RequestParam("idUsuario") Integer idUsuario,
                           @RequestParam("idCurso") Integer idCurso) {
        // Cargar Usuario y Curso desde la base de datos utilizando los IDs
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        Optional<Curso> cursoOptional = cursoRepository.findById(idCurso);

        if (usuarioOptional.isPresent() && cursoOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Curso curso = cursoOptional.get();

            // Establecer Usuario y Curso en el objeto Promedio
            promedio.setUsuario(usuario);
            promedio.setCurso(curso);

            // Calcular promedios
            float promEps = (promedio.getEp1() + promedio.getEp2() + promedio.getEp3() + promedio.getEp4()) / 4;
            promedio.setPromedio_ep(promEps);

            float promTotal = (promedio.getEp1() + promedio.getEp2() + promedio.getEp3() +
                    promedio.getEp4() + (promedio.getParcial() * 3) + (promedio.getFinall() * 3)) / 10;
            promedio.setNotafinal(promTotal);

            // Guardar el objeto Promedio con las asociaciones de Usuario y Curso
            promedioRepository.save(promedio);
        } else {
            // Manejo de error si no se encuentran Usuario o Curso
            // Puedes redirigir a una página de error o realizar alguna acción apropiada
        }

        return "redirect:/promedio";
    }

    @GetMapping("/detalle/{id}")
    public String mostrarNotas(@PathVariable Integer id ,Model model) {
        List<Promedio> promedios = promedioRepository.findByCurso_Id(id);
        model.addAttribute("promedios", promedios); // Asegúrate de usar el nombre correcto

        return "DetalleNotas"; // Nombre más descriptivo para la vista
    }

}
