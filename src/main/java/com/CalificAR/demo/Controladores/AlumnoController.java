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
import com.CalificAR.demo.Entidades.AlumnoExtendido;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.AlumnoServicio;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {

	@Autowired
	AlumnoServicio alumnoServicio;

	@GetMapping("/registroAlumno")
	public String registro(ModelMap modelo) {
		modelo.addAttribute("alumnoExtendido", new AlumnoExtendido());
		return "registroAlumno";
	}

	@PostMapping("/crearAlumno")
	public String newAlumno(ModelMap modelo, @ModelAttribute AlumnoExtendido alumnoExtendido, MultipartFile archivo) throws ErrorServicio {
		try {
			alumnoServicio.registrar(archivo, alumnoExtendido.getDni(), alumnoExtendido.getNombre(),
					alumnoExtendido.getApellido(), alumnoExtendido.getMail(), alumnoExtendido.getClave(), alumnoExtendido.getClave2(),
					alumnoExtendido.getFechaNac());
		} catch (ErrorServicio ex) {
			modelo.put("error", ex.getMessage());
			modelo.put("nombre", alumnoExtendido.getNombre());
			modelo.put("apellido", alumnoExtendido.getApellido());
			modelo.put("mail", alumnoExtendido.getMail());
			modelo.put("clave1", alumnoExtendido.getClave());
			modelo.put("clave2", alumnoExtendido.getClave2());
			modelo.put("fechaNac", alumnoExtendido.getFechaNac());
			return "registroAlumno.html";
		}
		modelo.put("titulo", "Bienvenido a CalificAR");
		modelo.put("descripcion", "Su usuario fue registrado de manera satisfactoria");
		return "inicio.html";
	}

	@PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
	@RequestMapping(value = "/modificarAlumno", method = RequestMethod.POST)
	public String modificarAlumno(ModelMap modelo, @RequestBody Alumno alumno) throws ErrorServicio {
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
