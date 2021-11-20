package com.CalificAR.demo.Controladores;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.NotificacionServicio;
import com.CalificAR.demo.Servicios.ProfesorServicio;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {

    @Autowired
    ProfesorServicio profesorServicio;
    @Autowired
    NotificacionServicio notificacionServicio;

    @GetMapping("/validarProfesor")
    public String validarProfesor() {
        return "validarProfesor";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/registroProfesor")
    public String registro(ModelMap modelo) {
        modelo.addAttribute("profesor", new Profesor());
        return "registroProfesor";
    }

    @PostMapping("/registrarProfesor")
    public String newProfesor(ModelMap modelo, @ModelAttribute Profesor profesor, String dni, String clave,
            String clave2, MultipartFile archivo) throws ErrorServicio {
        try {
            profesor = profesorServicio.registrar(archivo, dni, profesor.getNombre(), profesor.getApellido(),
                    profesor.getMail(), clave, clave2, profesor.getFechaNac());
            notificacionServicio.enviarBienvenidaProfe(profesor, dni, clave);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", profesor.getNombre());
            modelo.put("apellido", profesor.getApellido());
            modelo.put("mail", profesor.getMail());
            modelo.put("fechaNac", profesor.getFechaNac());
            return "registroProfesor.html";
        }
        modelo.put("titulo", "Bienvenido a CalificAR");
        modelo.put("descripcion", "Su usuario fue registrado de manera satisfactoria");
        modelo.put("profesor", profesor);
        return "inicio.html";
    }

    // TESTEADO
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/guardarProfesor")
    public String modificarProfesor(HttpSession session, ModelMap modelo, @ModelAttribute Profesor profesor,
            @RequestParam String claveNueva, MultipartFile archivo, @RequestParam String claveAnterior)
            throws ErrorServicio {
        Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
        if (loginProfesor == null) {
            return "redirect:/index";
        }
        try {
            profesor = profesorServicio.modificar(archivo, loginProfesor.getLogin().getDni(), profesor.getNombre(),
                    profesor.getApellido(), profesor.getMail(), claveNueva, profesor.getFechaNac(), claveAnterior);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", profesor.getNombre());
            modelo.put("apellido", profesor.getApellido());
            modelo.put("mail", profesor.getMail());
            modelo.put("fechaNac", profesor.getFechaNac());
            return "modificarProfesor.html";
        }
        modelo.addAttribute("alumno", profesor);
        session.setAttribute("alumnosession", profesor);
        return "inicio";
    }
}
