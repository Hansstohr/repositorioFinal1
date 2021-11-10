package com.CalificAR.demo.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.AlumnoExtendido;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    AlumnoRepositorio alumnoRepositorio;

    AlumnoServicio alumnoServicio = new AlumnoServicio();

    @GetMapping("/registroAlumno")
    public String registro(ModelMap modelo) {
        return "registroAlumno";
    }

//    @RequestMapping(value = "/getAlumnos", method = RequestMethod.GET)
//    public List<Alumno> getAllAlumnos() {
//        List<Alumno> alumnos = alumnoServicio.todos(alumnoRepositorio);
//        return alumnos;
//    }

    @RequestMapping(value = "/crearAlumno", method = RequestMethod.POST)
    public String newAlumno(ModelMap modelo, @RequestBody AlumnoExtendido alumno) throws ErrorServicio {
        try {
            alumnoServicio.registrar((MultipartFile) alumno.getFoto(), alumno.getDni(), alumno.getNombre(), alumno.getApellido(), alumno.getMail(), alumno.getClave(), alumno.getClave2(), alumno.getFechaNac());
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", alumno.getNombre());
            modelo.put("apellido", alumno.getApellido());
            modelo.put("mail", alumno.getMail());
            modelo.put("clave1", alumno.getClave());
            modelo.put("clave2", alumno.getClave2());
            modelo.put("fechaNac", alumno.getFechaNac());
            return "registroAlumno.html";
        }
        modelo.put("titulo", "Bienvenido a CalificAR");
        modelo.put("descripcion", "Su usuario fue registrado de manera satisfactoria");
        return "inicio.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
    @RequestMapping(value = "/modificarAlumno", method = RequestMethod.POST)
    public String modificarAlumno(ModelMap modelo, @RequestBody Alumno alumno) throws ErrorServicio {
        try {
            alumnoServicio.modificar(alumno.getId(), (MultipartFile) alumno.getFoto(), alumno.getDni(), alumno.getNombre(), alumno.getApellido(), alumno.getMail(), alumno.getClave(), alumno.getFechaNac());
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "registro.html";
        }
        modelo.put("titulo", "Su usuario fue modificado correctamente");
        return "perfil.html";
    }

}
