package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.MateriaRepositorio;
import com.CalificAR.demo.Servicios.MateriaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void newMateria(@RequestBody Materia materia) throws ErrorServicio {
        materiaServicio.crearMateria(materiaRepositorio, materia.getNombre());
    }
    
    @RequestMapping(value = "/getMaterias", method = RequestMethod.GET)
    public List<Materia> getAllMaterias() {
        List<Materia> materias = materiaServicio.todos(materiaRepositorio);
        return materias;
    }
    
    
}
