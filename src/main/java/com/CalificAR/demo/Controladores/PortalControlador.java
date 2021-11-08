package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import com.CalificAR.demo.Repositorio.UsuarioRepositorio;
import com.CalificAR.demo.Servicios.ProfesorServicio;
import com.CalificAR.demo.Servicios.UsuarioServicio;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
            model.put("logout", "Ha salido correctamente.");
        }
        return "login.html";
    }

    //PANTALLA DE REGISTRO CON FORMULARIO. SE LLEGA ACÁ LUEGO DE PRESONAR "REGISTRARSE EN EL INDEX"
    @GetMapping("/registroAlumno")
    public String registro(ModelMap modelo) {
        return "/alumnos/registro";
    }

    @GetMapping("/registroProfesor")
    public String registroProfesor() {
        return "/profesor/validarProfesor";
    }

    @PostMapping("/validarProfesor")
    public String validarProfesor(ModelMap modelo, @RequestParam String claveingresada) {
        try {
            profesorServicio.validarProfesor(claveingresada);
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "validarProfesor.html";
        }
        return "registrarProfesor.html";
    }

    //REDIRECCIÓN DEL FORMULARIO DE REGISTRO PROFESOR
    @PostMapping("/registrarProfesor")
    public String registrarProfesor(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String dni, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2, @RequestParam LocalDate fechaNac) throws ErrorServicio {

        try {
            profesorServicio.registrar(archivo, dni, nombre, apellido, mail, clave2, clave2, fechaNac);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);
            modelo.put("fechaNac", fechaNac);
            return "registro.html";
        }
        modelo.put("titulo", "Bienvenido a CalificAR");
        modelo.put("descripcion", "Su usuario fue registrado de manera satisfactoria");
        return "exito.html";
    }

}
