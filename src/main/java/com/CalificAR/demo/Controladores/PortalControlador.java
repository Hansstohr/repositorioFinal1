package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import com.CalificAR.demo.Servicios.ProfesorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalControlador {

    @Autowired
    private ProfesorServicio profesorServicio;

    @Autowired
    private ProfesorRepositorio profesorRepositorio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    //PANTALLA DE INICIO, CUANDO EL USUARIO SE LOGUEA EXITOSAMENTE.
    //@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }

    //RECUADRO DE LOGIN, ESTÁ EN EL INDEX(VER COMO LO PONEMOS EN EL INDEX)
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o clave incorrectos");
        }
        if (logout != null) {
            model.put("logout", "Ha salido correctamente de la plataforma.");
        }
        return "login.html";
    }

    //PANTALLA DE REGISTRO CON FORMULARIO. SE LLEGA ACÁ LUEGO DE PRESONAR "REGISTRARSE EN EL INDEX"
    @GetMapping("/registroAlumno")
    public String registroAlumno(ModelMap modelo) {
        return "registroAlumno.html";
    }

    @GetMapping("/registroProfesor")
    public String registroProfesor(ModelMap modelo) {
        return "validarProfesor.html";
    }
    
    
    

}
