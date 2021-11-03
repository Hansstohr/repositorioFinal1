package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Certificado;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.AsistenciaRepositorio;
import com.CalificAR.demo.Servicios.CertificadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/certificado")
public class CertificadoController {
    
    @Autowired
    AsistenciaRepositorio asistenciaRepositorio;
    
    CertificadoServicio certificadoServicio = new CertificadoServicio();
    
    @RequestMapping(value = "/generarCertificado", method = RequestMethod.POST)
    public void newCertificado(@RequestBody Alumno alumno) throws ErrorServicio {
        certificadoServicio.solicitarCertificado(asistenciaRepositorio, alumno.getId());
    }

}
