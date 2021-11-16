package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Materia;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.CalificAR.demo.Entidades.Nota;
import com.CalificAR.demo.Entidades.Notas;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.NotaRepositorio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import com.CalificAR.demo.Servicios.NotaServicio;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    NotaServicio notaServicio;
    
    @Autowired
    AlumnoServicio alumnoServicio;

    @PreAuthorize("hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @GetMapping("/agregarNotas")
    public String agregarNotas(ModelMap modelo, HttpSession session, @ModelAttribute Materia materia) {
        Profesor loginUsuario = (Profesor) session.getAttribute("profesorsession");
        if (loginUsuario == null || !loginUsuario.getId().equals(session.getId())) {
            return "redirect:/index";
        }
        List<Alumno> alumnos = alumnoServicio.alumnosPorMateria(materia.getIdMateria());
        
        modelo.put("alumnos", alumnos);
        modelo.put("materia", materia);
        
        return "agregarNota.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @RequestMapping(value = "/guardarNotas", method = RequestMethod.POST)
    public String guardarNotas(ModelMap modelo, HttpSession session, @ModelAttribute Notas notas) throws ErrorServicio {

        Profesor loginUsuario = (Profesor) session.getAttribute("profesorsession");
        if (loginUsuario == null || !loginUsuario.getId().equals(session.getId())) {
            return "redirect:/index";
        }
        List<Nota> listaNotas = notaServicio.crearNotas(notas);
        modelo.put("notas", listaNotas);
        modelo.put("materia", listaNotas.get(0).getMateria());
        
        return "VerNotas.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')|| hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @PostMapping("/obtenerNota")
    public String obtenerNota(ModelMap modelo, HttpSession session, @RequestParam String idMateria) {

        Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
        Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
        boolean alumnoNoLogueado = loginAlumno == null || !loginAlumno.getId().equals(session.getId());
        boolean profesorNoLogueado = loginProfesor == null || !loginProfesor.getId().equals(session.getId());
        if (alumnoNoLogueado && profesorNoLogueado) {
            return "redirect:/inicio";
        }
        List<Nota> notas = new ArrayList<>();
        // Es un alumno
        if (!alumnoNoLogueado) {
            notas = notaServicio.obtenerNotasAlumno(loginAlumno.getId(), idMateria);
        }
        // Es un profesor
        if (!profesorNoLogueado) {
            notas = notaServicio.obtenerNotas(idMateria);
        }

        modelo.put("notas", notas);
        
        return "VerNotas.html";
    }

}
