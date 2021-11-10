package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Certificado;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.AsistenciaRepositorio;
import com.CalificAR.demo.Repositorio.CertificadoRepositorio;
import com.CalificAR.demo.Servicios.CertificadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/certificado")
public class CertificadoController {

    @Autowired
    AsistenciaRepositorio asistenciaRepositorio;

    @Autowired
    CertificadoRepositorio certificadoRepositorio;

    @Autowired
    AlumnoRepositorio alumnoRepositorio;

    CertificadoServicio certificadoServicio = new CertificadoServicio();
    
    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
    @RequestMapping(value = "/generarCertificado", method = RequestMethod.POST)
    public void newCertificado(@RequestBody Alumno alumno) throws ErrorServicio {
        certificadoServicio.solicitarCertificado(alumno.getId());
    }
    
    //@PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')" + " || hasAnyRole('ROLE_PROFESOR_REGISTRADO')" )
    @GetMapping("/validarCertificado")
    public String validarCertificado(ModelMap modelo) {
        return "validarCertificado.html";
    }
    
    //AQUÍ TIENE QUE RETORNAR UN HTML CON LOS DATOS DE UN ALUMNO
    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
    @PostMapping("/consultarCertificado")
    public Alumno consultarCertificado(@RequestParam String certificado_codigo) throws ErrorServicio {
        Alumno alumno = certificadoServicio.consultarCertificados(certificado_codigo);
        return alumno;
    }

}
