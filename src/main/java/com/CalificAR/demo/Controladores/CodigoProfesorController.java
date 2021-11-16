package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.CodigoProfesorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/codigo")
public class CodigoProfesorController {

    @Autowired
    CodigoProfesorServicio codigoProfesorServicio;

    @GetMapping("/validarProfesor")
    public String validarProfesor() {
        return "validarProfesor";
    }

    @PostMapping("/validacionProfesor")
    public String validarProfesor(ModelMap modelo, @RequestParam String claveingresada) {
        try {
            codigoProfesorServicio.validarProfesor(claveingresada);
            modelo.addAttribute("profesor", new Profesor());
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "validarProfesor.html";
        }
        return "redirect:/profesor/registroProfesor";
    }

}
