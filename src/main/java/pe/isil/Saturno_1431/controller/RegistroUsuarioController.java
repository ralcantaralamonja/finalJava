package pe.isil.Saturno_1431.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.isil.Saturno_1431.model.Usuario;
import pe.isil.Saturno_1431.repository.UsuarioRepository;

@Controller
public class RegistroUsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String index(Model model)
    {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registro(Model model,
                           @Validated Usuario usuario,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes)
    {
        if (bindingResult.hasErrors()){
            model.addAttribute("usuario", usuario);
            return "registro";
        }

        //validar si existe el email
        String email = usuario.getEmail();
        boolean existeUsuario = usuarioRepository.existsByEmail(email);

        if (existeUsuario){
            bindingResult.rejectValue("email", "EmailYaExiste");
        }

        //validamos la coincidencia de las contraseñas
        if (!usuario.getPassword1().equals(usuario.getPassword2())){
            bindingResult.rejectValue("password1", "PasswordNoEsIgual");
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("usuario", usuario);
            return "registro";
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword1()));
        usuario.setRol(Usuario.Rol.ALUMNO);
        usuarioRepository.save(usuario);

        redirectAttributes.addAttribute("registroExitoso", "");
        return "redirect:/login";
    }
}
