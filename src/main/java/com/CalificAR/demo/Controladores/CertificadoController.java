package com.CalificAR.demo.Controladores;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.AsistenciaRepositorio;
import com.CalificAR.demo.Repositorio.CertificadoRepositorio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import com.CalificAR.demo.Servicios.CertificadoServicio;

@Controller
@RequestMapping("/certificado")
public class CertificadoController {

    @Autowired
    AsistenciaRepositorio asistenciaRepositorio;

    @Autowired
    CertificadoRepositorio certificadoRepositorio;

    @Autowired
    AlumnoRepositorio alumnoRepositorio;

    @Autowired
    CertificadoServicio certificadoServicio;
    
    @Autowired
    AlumnoServicio alumnoServicio;
    
    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
    @RequestMapping(value = "/generarCertificado", method = RequestMethod.POST)
    public String newCertificado(HttpSession session, ModelMap modelo) throws ErrorServicio {

        Alumno alumno = (Alumno) session.getAttribute("alumnosession");

        if (alumno == null || !alumno.getId().equals(session.getId())) {
            return "redirect:/index";
        }
        try {

            certificadoServicio.solicitarCertificado(alumno.getId());

        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            return "perfil.html";
        }
        return "certificado.html";
    }

    @GetMapping("/validarCertificado")
    public String validarCertificado(ModelMap modelo) {
        return "validarCertificado.html";
    }

    //AQUÍ TIENE QUE RETORNAR UN HTML CON LOS DATOS DE UN ALUMNO
    @PostMapping("/consultarCertificado")
    public String consultarCertificado(HttpSession session, @RequestParam String certificado_codigo) throws ErrorServicio {

        Alumno loginUsuario = (Alumno) session.getAttribute("alumnosession");

        if (loginUsuario == null || !loginUsuario.getId().equals(session.getId())) {

            return "redirect:/index";
        }
        Alumno alumno = certificadoServicio.consultarCertificados(certificado_codigo);

        return "certificado.html";

    }
    
    @GetMapping("/getCertificado")
    public String getCertificado(ModelMap modelo) throws ErrorServicio {
    	Alumno alumno = alumnoServicio.buscarPordDni(alumnoRepositorio, "12312312").get();
        modelo.put("alumno", alumno);
        return "certificado.html";
    }
}
