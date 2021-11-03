package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.MateriaRepositorio;
import com.CalificAR.demo.Servicios.MateriaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {
    
    @Autowired
    MateriaRepositorio materiaRepositorio;
    
    MateriaServicio materiaServicio = new MateriaServicio();
    
    @RequestMapping(value = "/crearMateria", method = RequestMethod.POST)
    public ResponseEntity newMateria(@RequestBody Materia materia) throws ErrorServicio {
        Materia materiaCreada = materiaServicio.crearMateria(materiaRepositorio, materia.getNombre());
        return new ResponseEntity(materiaCreada, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/getMaterias", method = RequestMethod.GET)
    public List<Materia> getAllMaterias() {
        List<Materia> materias = materiaServicio.todos(materiaRepositorio);
        return materias;
    }
    
    /*
    1) Crear materia
    POST: http://localhost:8080/api/materias/crearMateria
    {
    "nombre":"Fisica"
	}
    
    2) Consultar materias
    GET: http://localhost:8080/api/materias/getMaterias
    */
}
