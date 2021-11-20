package com.CalificAR.demo.Controladores;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import com.CalificAR.demo.Servicios.LoginServicio;
import com.CalificAR.demo.Servicios.MateriaServicio;
import com.CalificAR.demo.Servicios.ProfesorServicio;

@Controller
public class PortalControlador {

	@Autowired
	AlumnoServicio alumnoServicio;
	@Autowired
	ProfesorServicio profesorServicio;
	@Autowired
	LoginServicio loginServicio;
	@Autowired
	MateriaServicio materiaServicio;

	@GetMapping("/")
	public String index() {
		return "index.html";
	}

	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout,
			ModelMap model) {
		if (error != null) {
			model.put("error", "Usuario o clave incorrectos");
		}
		if (logout != null) {
			model.put("logout", "Ha salido correctamente.");
		}
		return "login.html";
	}

	// ESTO NO VA ACA
	@PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
	@GetMapping("validarCertificado")
	public String validarCertificado(HttpSession session) {
		Alumno loginUsuario = (Alumno) session.getAttribute("alumnosession");
		if (loginUsuario == null) {
			return "redirect:/index";
		}
		return "/certificado/validarCertificado";
	}

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/inicio")
	public String inicio(HttpSession session, ModelMap modelo) {
		Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
		Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
		// Es un alumno
		if (loginAlumno != null) {
			Optional<Alumno> optAlumno = alumnoServicio.buscarPordDni(loginAlumno.getLogin().getDni());
			if (optAlumno.isEmpty()) {
				return "/";
			}
			List<Materia> materias = materiaServicio.todas();
			modelo.put("materias", materias);
			modelo.put("alumno", optAlumno.get());
		}
		// Es un profesor
		if (loginProfesor != null) {
			modelo.put("profesor", loginProfesor);
		}
		return "inicio.html";
	}

	@GetMapping("/recuperarContraseña")
	public String recuperarContraseña() {
		return "recuperarContraseña.html";
	}

	@PostMapping("/validarMail")
	public String validarMail(String mail, ModelMap model) throws ErrorServicio {
		Optional<Alumno> alumno = alumnoServicio.buscarPorMail(mail);
		Optional<Profesor> profesor = profesorServicio.buscarPorMail(mail);
		if (alumno.isPresent() || profesor.isPresent()) {
			// Envia Mail
			if (alumno.isPresent()) {
				loginServicio.enviarContraseñaAlumno(alumno.get());
				return "exitoContraseña.html";
			} else {
				loginServicio.enviarContraseñaProfesor(profesor.get());
				return "exitoContraseña.html";
			}
		} else {
			model.put("error", "El mail ingresado es inexistente");
		}
		return "recuperarContraseña.html";
	}

	// TENDRÍA QUE SER EL MISMO PARA LOS DOS USUARIOS
	// TESTEADO
	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/modificarusuario")
	public String modificar(HttpSession session, ModelMap modelo) {
		Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
		Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
		if (loginAlumno == null && loginProfesor == null) {
			return "redirect:/index";
		}
		// Es un alumno
		if (loginAlumno != null) {
			Optional<Alumno> alumno = alumnoServicio.buscarPordDni(loginAlumno.getLogin().getDni());
			if (alumno.isEmpty()) {
				return "redirect:/index";
			}
			modelo.addAttribute("alumno", alumno.get());
			return "modificarUsuario";
		} else {
			Optional<Profesor> profesor = profesorServicio.buscarPordDni(loginProfesor.getLogin().getDni());
			if (profesor.isEmpty()) {
				return "redirect:/index";
			}
			modelo.addAttribute("profesor", profesor.get());
			return "modificarUsuario";
		}
	}

	// TENDRÍA QUE SER EL MISMO PARA LOS DOS USUARIOS
	// TESTEADO
	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/perfil")
	public String perfil(HttpSession session, ModelMap modelo) throws ErrorServicio {
		Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
		Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
		if (loginAlumno == null && loginProfesor == null) {
			return "redirect:/index";
		}
		// Es un alumno
		if (loginAlumno != null) {
			modelo.put("alumno", loginAlumno);
			modelo.put("usuario", loginAlumno);
		} else {
			modelo.put("profesor", loginProfesor);
			modelo.put("usuario", loginProfesor);
		}
		return "perfil";
	}
}
