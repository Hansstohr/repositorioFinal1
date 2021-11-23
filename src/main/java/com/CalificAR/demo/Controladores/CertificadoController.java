package com.CalificAR.demo.Controladores;
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
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.CertificadoRepositorio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import com.CalificAR.demo.Servicios.CertificadoServicio;
import com.CalificAR.demo.Servicios.MateriaServicio;

@Controller
@RequestMapping("/certificado")
public class CertificadoController {
	@Autowired
	CertificadoRepositorio certificadoRepositorio;
	@Autowired
	AlumnoRepositorio alumnoRepositorio;
	@Autowired
	CertificadoServicio certificadoServicio;
	@Autowired
	AlumnoServicio alumnoServicio;
	@Autowired
	MateriaServicio materiaServicio;

	@PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
	@GetMapping("/generarCertificado")
	public String generarCertificado(HttpSession session, ModelMap modelo) throws ErrorServicio {
		Alumno alumnoSession = (Alumno) session.getAttribute("alumnosession");
		if (alumnoSession == null) {
			return "redirect:/index";
		}
		Alumno alumno = alumnoServicio.buscarPordDni(alumnoSession.getLogin().getDni()).get();
		String codigo = certificadoServicio.solicitarCertificado(alumnoSession.getId());
		if (codigo.isEmpty()) {
			modelo.put("error", "El alumno no cumple las condiciones de regularidad");
			return "/inicio";
		}
		modelo.put("codigo", codigo);
		modelo.put("alumno", alumno);
		return "certificado.html";
	}

	@GetMapping("/validarCertificado")
	public String validarCertificado(ModelMap modelo) {
		return "validarCertificado.html";
	}

	// AQUÍ TIENE QUE RETORNAR UN HTML CON LOS DATOS DE UN ALUMNO
	@PostMapping("/consultarCertificado")
	public String consultarCertificado(HttpSession session, @RequestParam String certificado_codigo, ModelMap modelo)
			throws ErrorServicio {
		try {
			Alumno alumno = certificadoServicio.validarCertificado(certificado_codigo);
			modelo.put("alumno", alumno);
		} catch (ErrorServicio ex) {
			modelo.put("error", ex.getMessage());
			return "validarCertificado.html";
		}
		return "certificado.html";
	}
}
