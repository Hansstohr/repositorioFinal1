package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Servicios.AsistenciaServicio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaController {
    
    @Autowired
    private AsistenciaServicio asistenciaServicio;
    
    @RequestMapping(value = "/crearAsistencia", method = RequestMethod.POST, consumes = "application/json")
    public void crearAsistencia(@RequestPart("estado") String estado){
        asistenciaServicio.crearAsistencia(Boolean.valueOf(estado), null);
    }
    
    @RequestMapping(value = "/consultarAsistenciaAlumno", method = RequestMethod.POST)
    public void consultarAsistencia(Date fecha){
        asistenciaServicio.consultarAsistencia(fecha);
    }
}
