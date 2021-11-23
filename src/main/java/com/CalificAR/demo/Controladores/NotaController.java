package com.CalificAR.demo.Controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Nota;
import com.CalificAR.demo.Entidades.Notas;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import com.CalificAR.demo.Servicios.MateriaServicio;
import com.CalificAR.demo.Servicios.NotaServicio;

@Controller
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    NotaServicio notaServicio;
    @Autowired
    AlumnoServicio alumnoServicio;
    @Autowired
    MateriaServicio materiaServicio;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/notas")
    public String cargarMateriaNota(ModelMap modelo, @RequestParam(required = false) String idMateria) {
        try {
            List<Alumno> alumnos = alumnoServicio.alumnosPorMateria(idMateria);
            Materia materia = materiaServicio.buscarPorId(idMateria);
            List<Double> notas = new ArrayList<Double>();
            modelo.put("alumnos", alumnos);
            modelo.put("materia", materia);
            modelo.put("notas", notas);
            return "notas.html";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            return "inicio";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @PostMapping("/cargarNotas")
    public String cargarNotas(ModelMap modelo, @RequestParam String idMateria) throws ErrorServicio {
        Materia materia = materiaServicio.buscarPorId(idMateria);
        modelo.put("materia", materia);
        try {
            List<Alumno> alumnos = alumnoServicio.alumnosPorMateria(idMateria);
            Notas notas = new Notas(materia, alumnos);
            modelo.put("notas", notas);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            return "inicio.html";
        }
        return "cargarNotas.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @PostMapping("/guardarNotas")
    public String guardarNotas(ModelMap modelo, @ModelAttribute Notas notas) throws ErrorServicio {
        try {
            notaServicio.crearNotas(notas);
            modelo.put("exito", "Notas cargadas correctamente");
        } catch (ErrorServicio e) {
            Materia materia = materiaServicio.buscarPorId(notas.getNotas().get(0).getMateria().getIdMateria());
            modelo.put("materia", materia);
            modelo.put("error", e.getMessage());
            return "cargarNotas.html";
        }
        return "inicio";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/verNotas")
    public String verNotas(ModelMap modelo, @RequestParam String idMateria, @RequestParam String mail)
            throws ErrorServicio {
        Materia materia = materiaServicio.buscarPorId(idMateria);
        Optional<Alumno> alumno = alumnoServicio.buscarPorMail(mail);
        List<Nota> notas = notaServicio.notasPorAlumno(alumno.get().getId(), idMateria);
        modelo.put("nombreAlumno", alumno.get().getNombre());
        modelo.put("apellidoAlumno", alumno.get().getApellido());
        modelo.put("materia", materia);
        modelo.put("notas", notas);
        return "verNotasAlumno.html";
    }
}
