package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.AlumnoExtendido;
import com.CalificAR.demo.Entidades.Notas;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.NotaRepositorio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import com.CalificAR.demo.Servicios.NotaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/notas")
public class NotaController {
    
    @Autowired
    NotaRepositorio notaRepositorio;

    NotaServicio notaServicio = new NotaServicio();
    
    @RequestMapping(value = "/agregarNota", method = RequestMethod.POST)
    public void agregarNotas(@RequestBody Notas notas) throws ErrorServicio {
        notaServicio.crearNotas(notas);
    }
    
}
