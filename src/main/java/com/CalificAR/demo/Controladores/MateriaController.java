package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.MateriaRepositorio;
import com.CalificAR.demo.Servicios.MateriaServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materia")
public class MateriaController {

    @Autowired
    MateriaRepositorio materiaRepositorio;

    MateriaServicio materiaServicio = new MateriaServicio();

    @PreAuthorize("hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @GetMapping("/crearMateria")
    public String crearMateria(HttpSession session) {
        Profesor loginUsuario = (Profesor) session.getAttribute("profesorsession");
        if (loginUsuario == null || !loginUsuario.getId().equals(session.getId())) {
            return "redirect:/index";
        }
        return "crearMateria.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @RequestMapping(value = "/crearMateria", method = RequestMethod.POST)
    public String crearMateria(HttpSession session, ModelMap modelo, @RequestBody String nombreMateria) throws ErrorServicio {

        Profesor loginUsuario = (Profesor) session.getAttribute("profesorsession");
        if (loginUsuario == null || !loginUsuario.getId().equals(session.getId())) {
            return "redirect:/index";
        }

        try {
            materiaServicio.crearMateria(nombreMateria);
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "crearMateria.html";
        }
        //donde va??
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
    @GetMapping("/inscribirMateria")
    public String inscribirMateria(HttpSession session, ModelMap modelo, @RequestParam Materia idMateria, @RequestParam String idAlumno) throws ErrorServicio {

        Alumno loginUsuario = (Alumno) session.getAttribute("alumnosession");
        if (loginUsuario == null || !loginUsuario.getId().equals(session.getId())) {
            return "redirect:/index";
        }

        try {
            materiaServicio.inscribirMateria(idMateria, idAlumno);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "inicio.html";
        }
        modelo.put("mensaje", "Inscripto correctamente!");
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
    @RequestMapping(value = "/todasLasMateriasAlumno", method = RequestMethod.GET)
    public String enlistarMateriasAlumno(HttpSession session , ModelMap modelo) {

        Alumno loginUsuario = (Alumno) session.getAttribute("alumnosession");
        if (loginUsuario == null || !loginUsuario.getId().equals(session.getId())) {
            return "redirect:/index";
        }
        List<Materia> todas = materiaServicio.todos();
        modelo.put("materias" , todas);
        return "inscribirse";

    }

}
