package pe.isil.Saturno_1431.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pe.isil.Saturno_1431.model.Promedio;
import pe.isil.Saturno_1431.repository.PromedioRepository;

import java.util.logging.Logger;


@Controller

public class PromedioController {
    @Autowired
    PromedioRepository promedioRepository;

    @GetMapping("/promedio")
    public String InsertarNotas(Model model){
        model.addAttribute("promedio",new Promedio());
        return "promedio";
    }
    @PostMapping("/promedio")
    public String Calcular(Model model , @Validated Promedio promedio){
        float promEps =( (promedio.getEp1() + promedio.getEp2()+promedio.getEp3()+promedio.getEp4() ) /4);
        promedio.setPromedio_ep(promEps);
         float promtotal = (promedio.getEp1() + promedio.getEp2() + promedio.getEp3() +
                promedio.getEp4()+ (promedio.getParcial() * 3) + (promedio.getFinall()*3))/10;
        promedio.setNotafinal(promtotal);
        System.out.println("El promedio de eps es "+promedio.getPromedio_ep() );
        System.out.println("El promedio es "+promedio.getNotafinal());
        promedioRepository.save(promedio);
        return "redirect:/promedio";
    }

}
