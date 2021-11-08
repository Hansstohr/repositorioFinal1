package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.MateriaRepositorio;
import com.CalificAR.demo.Servicios.MateriaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materia")
public class MateriaController {
    
    @Autowired
    MateriaRepositorio materiaRepositorio;
    
    MateriaServicio materiaServicio = new MateriaServicio();
    
    
    @GetMapping("/crearMateria")
    public String crearMateria(){
        return "crearMateria.html";
    }
    
    
    
    @RequestMapping(value = "/crearMateria", method = RequestMethod.POST)
    public String crearMateria(ModelMap modelo, @RequestBody String nombreMateria) throws ErrorServicio{
        try {
            materiaServicio.validarMateria(nombreMateria);
        } catch (Exception ex) {
           modelo.put("error",ex.getMessage());
           return "crearMateria.html";
        }
        //donde va??
        return "index.html";
    }
    
    @RequestMapping(value = "/todasLasMaterias", method = RequestMethod.GET)
    public List<Materia> enlistarMaterias() {
        List<Materia> todas = materiaServicio.todos(); 
        return todas;
    }
    
    
    
    
    
    
    

}
