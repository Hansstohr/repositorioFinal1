package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Entidades.ProfesorExtendido;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import com.CalificAR.demo.Servicios.ProfesorServicio;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    //@Autowired
    //AlumnoRepositorio alumnoRepositorio;
    @Autowired
    ProfesorRepositorio profesorRepositorio;

    ProfesorServicio profesorServicio = new ProfesorServicio();

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
    @GetMapping("/registrarProfesor")
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
            return "registrarProfesor.html";
        }
        modelo.put("titulo", "Bienvenido a CalificAR");
        modelo.put("descripcion", "Su usuario fue registrado de manera satisfactoria");
        return "inicio.html";
    }

    @GetMapping("/modificarProfesor")
    public String modificarAlumno(@RequestBody Profesor profesor) throws ErrorServicio {
        profesorServicio.modificar(profesor.getId(), (MultipartFile) profesor.getFoto(), profesor.getDni(), profesor.getNombre(), profesor.getApellido(), profesor.getMail(), profesor.getClave(), profesor.getFechaNac());
        return "PerfilProfesor.html";
    }

    @GetMapping("/inicio")
    public String registroProfesor(ModelMap modelo) {
        return "materia/crearMateria.html";
    }

    @RequestMapping(value = "/getProfesores", method = RequestMethod.GET)
    public List<Profesor> getAllProfesores() {
        List<Profesor> profesores = profesorServicio.todos(profesorRepositorio);
        return profesores;
    }

    @RequestMapping(value = "/newProfesor", method = RequestMethod.POST)
    public void newProfesor(@RequestBody ProfesorExtendido profesor) throws ErrorServicio {
        profesorServicio.registrar(profesorRepositorio, null, profesor.getDni(), profesor.getNombre(), profesor.getApellido(), profesor.getMail(),
                profesor.getClave(), profesor.getClave2(), profesor.getFechaNac());
    }

    //PARA TESTEAR REGISTRO EN POSTMAN 
//    {
//    "dni":"39504711",
//    "nombre":"Chino",
//    "apellido":"Vega",
//    "mail":"chinofirmat@gmail.com",
//    "clave":"12345678",
//    "clave2":"12345678",
//    "fechaNac":"1996-04-10"
//    }
}
