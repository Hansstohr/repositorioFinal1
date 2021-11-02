package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Entidades.ProfesorExtendido;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import com.CalificAR.demo.Servicios.ProfesorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {
    
    //@Autowired
    //AlumnoRepositorio alumnoRepositorio;

    @Autowired
    ProfesorRepositorio profesorRepositorio;

    ProfesorServicio profesorServicio = new ProfesorServicio();

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

    @RequestMapping(value = "/modificarProfesor", method = RequestMethod.POST)
    public void modificarProfesor(@RequestBody ProfesorExtendido profesor) throws ErrorServicio {
        profesorServicio.modificar(profesorRepositorio, null, profesor.getId(), profesor.getDni(), profesor.getNombre(), profesor.getApellido(), profesor.getMail(),
                profesor.getClave(), profesor.getFechaNac());
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
