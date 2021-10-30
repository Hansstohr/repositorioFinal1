package com.CalificAR.demo.Controladores;
import com.CalificAR.demo.Entidades.Usuario;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.CalificAR.demo.Entidades.Alumno;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {
    @Autowired
    AlumnoRepositorio alumnoRepository;
    
    @RequestMapping("/getAlumnos")
    public List<Alumno> getAllAlumnos(){
        List<Alumno> alumnos = alumnoRepository.findAll();
        return alumnos;
    }
    
    @RequestMapping("/newAlumno")
    public void newAlumno(String name){
        Alumno a = new Alumno();
        a.setNombre(name);
        alumnoRepository.save(a);
    }
    
    
}