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
import pe.isil.Saturno_1431.model.Usuario;
import pe.isil.Saturno_1431.repository.UsuarioRepository;

import java.util.Optional;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;



    //mostar el listado de usuarios con paginacion
    @GetMapping("") //localhost:8080/admin/usuarios
    String index(Model model,
                 @PageableDefault(size = 5, sort = "nombres") Pageable pageable,
                 @RequestParam(required = false) String email)
    {
        Page<Usuario> usuarios;

        if (email != null && !email.trim().isEmpty())
        {
            usuarios = usuarioRepository.findByEmailContaining(email, pageable);
        }
        else
        {
            usuarios = usuarioRepository.findAll(pageable);
        }

        model.addAttribute("usuarios", usuarios);
        return "admin/usuarios/index";
    }

    //mostrar un formulario para el ingreso o registro de un nuevo usuario
    @GetMapping("/nuevo")
    String nuevo(Model model)
    {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuarios/nuevo";
    }

    //registrar un nuevo usuario en la base de datos
    @PostMapping("/registrar")
    String registrar(Model model,
                    @Validated Usuario usuario,
                    BindingResult bindingResult,
                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            model.addAttribute("usuario", usuario);
            return "admin/usuarios/nuevo";
        }
        //validar que el correo sea único
        String email = usuario.getEmail();
        boolean usuarioYaExiste = usuarioRepository.existsByEmail(email);

        if (usuarioYaExiste){
            bindingResult.rejectValue("email", "EmailYaExiste");
        }

        //validamos la coincidencia de los passwords
        if (!usuario.getPassword1().equals(usuario.getPassword2())){
            bindingResult.rejectValue("password1", "PasswordNoEsIgual");
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("usuario", usuario);
            return "admin/usuarios/nuevo";
        }

        usuario.setPassword(usuario.getPassword1()); //codificar o encripta la contaseña
        usuario.setRol(Usuario.Rol.ALUMNO);
        usuarioRepository.save(usuario);
        redirectAttributes.addFlashAttribute("msgExito", "Usuario registrado con éxito");
        return "redirect:/admin/usuarios";
    }

    //editar: donde mostrara los datos del usuario en un formulario para su edicion
    @GetMapping("/editar/{id}")
    String editar(Model model, @PathVariable Integer id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        model.addAttribute("usuario", usuario);
        return "admin/usuarios/editar";
    }

    //actualizar el usuario en la base de datos

    @PostMapping("/editar/{id}")
    String actualizar(Model model,
                      @PathVariable Integer id,
                      @Validated Usuario usuario,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            model.addAttribute("usuario", usuario);
            return "admin/usuarios/nuevo";
        }

        Optional<Usuario> usuarioFromDB = usuarioRepository.findById(id);

        usuarioFromDB.get().setNombres(usuario.getNombres());
        usuarioFromDB.get().setApellidos(usuario.getApellidos());
        usuarioFromDB.get().setEmail(usuario.getEmail());

        if (! usuario.getPassword().isEmpty()){
            usuarioFromDB.get().setPassword(usuario.getPassword());
        }

        usuarioRepository.save(usuarioFromDB.get());
        redirectAttributes.addFlashAttribute("msgExito", "Usuario actualizado exitosamente");
        return "redirect:/admin/usuarios";
    }

    //eliminar usuario de la base de datos por su ID
    @PostMapping("/eliminar/{id}")
    String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        usuarioRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("msgExito", "Usuario eliminado con éxito");
        return "redirect:/admin/usuarios";
    }
}
