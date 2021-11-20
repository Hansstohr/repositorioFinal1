package com.CalificAR.demo.Controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Nota;
import com.CalificAR.demo.Entidades.Notas;
import com.CalificAR.demo.Entidades.Profesor;
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

	@GetMapping("/cargarNota")
	public String cargarMateriaNota(ModelMap modelo, @RequestParam(required = false) String idMateria)
			throws ErrorServicio {
		List<Alumno> alumnos = alumnoServicio.alumnosPorMateria(idMateria);
		modelo.put("alumnos", alumnos);
		modelo.put("materia", idMateria);
		return "cargarNota.html";
	}

	@PostMapping("/agregarNotas")
	public String agregarNotas(ModelMap modelo, @RequestParam String idMateria, @RequestParam String mail,
			@RequestParam Double nota) throws ErrorServicio {
		Optional<Alumno> alumno = alumnoServicio.buscarPorMail(mail);
		Materia materia = materiaServicio.buscarPorId(idMateria);
		try {
			List<Nota> listaNotas = notaServicio.crearListaNotas(alumno.get(), materia, LocalDate.now(), nota);
			Notas nuevaNota = new Notas(materia, listaNotas, LocalDate.now());
			List<Nota> nuevaListaNotas = notaServicio.crearNotas(nuevaNota);
			modelo.put("notas", listaNotas);
			modelo.put("materia", listaNotas.get(0).getMateria());
		} catch (ErrorServicio e) {
			List<Alumno> alumnos = alumnoServicio.alumnosPorMateria(idMateria);
			modelo.put("materia", idMateria);
			modelo.put("alumnos", alumnos);
			modelo.put("error", e.getMessage());
			return "cargarNota";
		}
		List<Double> notasTotales = notaServicio.notasPorAlumno(alumno.get().getId(), idMateria);
		modelo.put("notasTotales", notasTotales);
		return "verNotas.html";
	}

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/guardarNotas")
	public String guardarNotas(ModelMap modelo, HttpSession session, String idAlumno, String idMateria, Double nota)
			throws ErrorServicio {
		Profesor loginUsuario = (Profesor) session.getAttribute("profesorsession");
		if (loginUsuario == null) {
			return "redirect:/index";
		}
//        List<Nota> listaNotas = new ArrayList();
//        Nota notas = new Nota(alumno , materia ,LocalDate.now() , nota );
//        listaNotas.add(notas);
//        
//        modelo.put("notas", listaNotas);
//        modelo.put("materia", listaNotas.get(0).getMateria());
		return "cargarMateriaNota.html";
	}

	@PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')|| hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
	@PostMapping("/obtenerNota")
	public String obtenerNota(ModelMap modelo, HttpSession session, @RequestParam String idMateria) {
		Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
		Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
		if (loginAlumno == null && loginProfesor == null) {
			return "redirect:/index";
		}
		List<Nota> notas = new ArrayList<>();
		// Es un alumno
		if (loginAlumno != null) {
			notas = notaServicio.obtenerNotasAlumno(loginAlumno.getId(), idMateria);
		}
		// Es un profesor
		if (loginProfesor != null) {
			notas = notaServicio.obtenerNotas(idMateria);
		}
		modelo.put("notas", notas);
		return "VerNotas.html";
	}
}
