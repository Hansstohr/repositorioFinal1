package com.CalificAR.demo.Controladores;
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
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import com.CalificAR.demo.Servicios.MateriaServicio;
import com.CalificAR.demo.Servicios.ProfesorServicio;

@Controller
@RequestMapping("/materia")
public class MateriaController {
	@Autowired
	AlumnoServicio alumnoServicio;
	@Autowired
	ProfesorServicio profesorServicio;
	@Autowired
	MateriaServicio materiaServicio;

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/crearMateria")
	public String crearMateria(HttpSession session, ModelMap modelo) {
		Profesor sessionProfesor = (Profesor) session.getAttribute("profesorsession");
		if (sessionProfesor == null) {
			return "redirect:/index";
		}
		Optional<Profesor> optProfesor = profesorServicio.buscarPordDni(sessionProfesor.getLogin().getDni());
		// Se actualiza la sesión, para que aparezca la nueva materia creada
		session.setAttribute("profesorsession", optProfesor.get());
		return "crearMateria";
	}

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@PostMapping("/guardarMateria")
	public String guardarMateria(HttpSession session, ModelMap modelo, @RequestParam String nombreMateria)
			throws ErrorServicio {
		Profesor loginUsuario = (Profesor) session.getAttribute("profesorsession");
		if (loginUsuario == null) {
			return "redirect:/index";
		}
		try {
			Materia materia = materiaServicio.crearMateria(nombreMateria, loginUsuario.getLogin().getDni());
			modelo.put("exito", "La materia " + materia.getNombre() + " se creó correctamente");
			Optional<Profesor> optProfesor = profesorServicio.buscarPordDni(loginUsuario.getLogin().getDni());
			session.setAttribute("profesorsession", optProfesor.get());
			return "/inicio";
		} catch (Exception ex) {
			modelo.put("error", ex.getMessage());
			return "crearMateria.html";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/materia")
	public String materia(HttpSession session, ModelMap modelo, @RequestParam(required = false) String idMateria)
			throws ErrorServicio {
		Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
		Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
		if (loginAlumno == null && loginProfesor == null) {
			return "redirect:/index";
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
		if (loginAlumno == null && loginProfesor == null) {
			return "redirect:/index";
		}
		List<Materia> materias = new ArrayList<>();
		// Es un alumno
		if (loginAlumno != null) {
			materias = materiaServicio.materiasPorAlumno(loginAlumno.getLogin().getDni());
		}
		// Es un profesor
		if (loginProfesor != null) {
			materias = materiaServicio.materiasPorProfesor(loginProfesor.getLogin().getDni());// ACA TOQUÉ
		}
		modelo.put("materias", materias);
		return "misMaterias";
	}

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/inscribirseMaterias")
	public String inscribirseMaterias(HttpSession session, ModelMap modelo, @RequestParam String idMateria)
			throws ErrorServicio {
		Alumno loginUsuario = (Alumno) session.getAttribute("alumnosession");
		if (loginUsuario == null) {
			return "redirect:/index";
		}
		try {
			materiaServicio.inscribirMateria(idMateria, loginUsuario.getLogin().getDni());
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			List<Materia> materias = materiaServicio.materiasParaInscribirse(loginUsuario.getLogin().getDni());
			Optional<Alumno> optAlumno = alumnoServicio.buscarPordDni(loginUsuario.getLogin().getDni());
			session.setAttribute("alumnosession", optAlumno.get());
			session.setAttribute("materias", materias);
			return "inicio";
		}
		Optional<Alumno> optAlumno = alumnoServicio.buscarPordDni(loginUsuario.getLogin().getDni());
		List<Materia> materias = materiaServicio.materiasParaInscribirse(loginUsuario.getLogin().getDni());
		session.setAttribute("alumnosession", optAlumno.get());
		session.setAttribute("materias", materias);
		modelo.put("exito", "Inscripto correctamente!");
		return "/inicio";
	}
}
