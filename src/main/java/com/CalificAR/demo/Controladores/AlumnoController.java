package com.CalificAR.demo.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired
    AlumnoServicio alumnoServicio;

    @GetMapping("/registroAlumno")
    public String registro(ModelMap modelo) {
        modelo.addAttribute("alumno", new Alumno());
        return "registroAlumno";
    }

    @PostMapping("/crearAlumno")
    public String newAlumno(ModelMap modelo, @ModelAttribute Alumno alumno, String clave2, MultipartFile archivo)
            throws ErrorServicio {
        try {
            alumnoServicio.registrar(archivo, alumno.getDni(), alumno.getNombre(), alumno.getApellido(),
                    alumno.getMail(), alumno.getClave(), clave2, alumno.getFechaNac());
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", alumno.getNombre());
            modelo.put("apellido", alumno.getApellido());
            modelo.put("mail", alumno.getMail());
            modelo.put("fechaNac", alumno.getFechaNac());
            return "registroAlumno.html";
        }
        modelo.put("titulo", "Bienvenido a CalificAR");
        modelo.put("descripcion", "Su usuario fue registrado de manera satisfactoria");
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
    @RequestMapping(value = "/modificarAlumno", method = RequestMethod.POST)
    public String modificarAlumno(HttpSession session , ModelMap modelo, @RequestBody Alumno alumno) throws ErrorServicio {

        Alumno loginUsuario = (Alumno) session.getAttribute("alumnosession");
        if (loginUsuario == null || !loginUsuario.getId().equals(session.getId())) {
            return "redirect:/index";
        }

        try {
            alumnoServicio.modificar(alumno.getId(), (MultipartFile) alumno.getFoto(), alumno.getDni(),
                    alumno.getNombre(), alumno.getApellido(), alumno.getMail(), alumno.getClave(),
                    alumno.getFechaNac());
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "registro.html";
        }
        modelo.put("titulo", "Su usuario fue modificado correctamente");
        return "perfil.html";
    }
}
