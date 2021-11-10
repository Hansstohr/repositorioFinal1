package com.CalificAR.demo.Controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalControlador {

	@GetMapping("/")
	public String index() {
		return "index.html";
	}

	@PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')" + " || hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
	@GetMapping("validarCertificado")
	public String validarCertificado() {
		return "/certificado/validarCertificado";
	}

	// PANTALLA DE INICIO, CUANDO EL USUARIO SE LOGUEA EXITOSAMENTE.
	@PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')" + " || hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
	@GetMapping("/inicio")
	public String inicio() {
		return "inicio.html";
	}
	
    @GetMapping("/registroProfesor")
    public String registroProfesor(ModelMap modelo) {
        return "validarProfesor.html";
    }

	// RECUADRO DE LOGIN, ESTÁ EN EL INDEX(VER COMO LO PONEMOS EN EL INDEX)
	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout,
			ModelMap model) {
		if (error != null) {
			model.put("error", "Usuario o clave incorrectos");
		}
		if (logout != null) {
			model.put("logout", "Ha salido correctamente de la plataforma.");
		}
		return "login.html";
	}
}
