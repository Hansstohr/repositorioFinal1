package com.CalificAR.demo.Controladores;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.MateriaRepositorio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import com.CalificAR.demo.Servicios.MateriaServicio;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/materia")
public class MateriaController {

    @Autowired
    MateriaRepositorio materiaRepositorio;
    @Autowired
    AlumnoRepositorio alumnoRepositorio;
    @Autowired
    ProfesorRepositorio profesorRepositorio;
    @Autowired
    MateriaServicio materiaServicio;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/crearMateria")
    public String crearMateria(HttpSession session) {
        Profesor loginUsuario = (Profesor) session.getAttribute("profesorsession");
        System.out.println(loginUsuario);
        if (loginUsuario == null) {
            return "redirect:/index";
        }
        return "crearMateria";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/guardarMateria")
    public String crearMateria(HttpSession session, ModelMap modelo, @RequestParam String nombreMateria)
            throws ErrorServicio {
        Profesor loginUsuario = (Profesor) session.getAttribute("profesorsession");
        if (loginUsuario == null) {
            return "redirect:/index";
        }
        try {
            materiaServicio.crearMateria(nombreMateria, loginUsuario.getLogin().getDni());

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "crearMateria.html";
        }
        return "Materia";
    }

    // @RequestParam(required = false) String id
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/Materia")
    public String materia(HttpSession session, ModelMap modelo, @RequestParam(required = false) String idMateria) throws ErrorServicio {
        Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
        Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
        boolean alumnoNoLogueado = loginAlumno == null;
        boolean profesorNoLogueado = loginProfesor == null;
        if (alumnoNoLogueado && profesorNoLogueado) {
            return "redirect:/inicio";
        }
        Materia materia = materiaServicio.buscarPorId(idMateria);
        modelo.put("materia", materia);
        return "Materia";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/misMaterias")
    public String listarMaterias(HttpSession session, ModelMap modelo) {
        Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
        Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
        boolean alumnoNoLogueado = loginAlumno == null;
        boolean profesorNoLogueado = loginProfesor == null;
        if (alumnoNoLogueado && profesorNoLogueado) {
            return "redirect:/inicio";
        }
        List<Materia> materias = new ArrayList<>();
        // Es un alumno
        if (!alumnoNoLogueado) {
            materias = materiaServicio.materiasPorAlumno(loginAlumno.getLogin().getDni());
        }
        // Es un profesor
        if (!profesorNoLogueado) {
            materias = materiaServicio.materiasPorProfesor(loginProfesor.getLogin().getDni());//ACA TOQUÉ
        }

        modelo.put("materias", materias);
        return "misMaterias";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inscribirseMateria")
    public String incribirseMaterias(HttpSession session, ModelMap modelo) {
        Alumno loginUsuario = (Alumno) session.getAttribute("alumnosession");
        if (loginUsuario == null) {
            return "redirect:/index";
        }
        List<Materia> todas = materiaServicio.todos();
        modelo.put("materias", todas);
        return "InscribirseMaterias";
    }

    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
    @PostMapping("/inscribirMateria")
    public String inscribirMateria(HttpSession session, ModelMap modelo, @RequestParam String idMateria) throws ErrorServicio {
        Alumno loginUsuario = (Alumno) session.getAttribute("alumnosession");
        if (loginUsuario == null) {
            return "redirect:/index";
        }
        try {
            materiaServicio.inscribirMateria(idMateria, loginUsuario.getLogin().getDni());
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            return "inicio.html";
        }
        modelo.put("mensaje", "Inscripto correctamente!");
        return "inicio.html";
    }
}
