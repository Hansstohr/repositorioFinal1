package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.CodigoProfesorServicio;
import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/codigo")
public class CodigoProfesorController {

	@Autowired
	CodigoProfesorServicio codigoProfesorServicio;

        @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
        @GetMapping("/crearCodigo")
        public String crearCodigo(HttpSession session) {
            Login loginAdmin = (Login) session.getAttribute("adminsession");
		if (loginAdmin == null) {
			return "redirect:/index";
		}
            return "crearCodigo";
        }
        
        @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
        @PostMapping("/cargarCodigo")
        public String cargarCodigo(ModelMap modelo, @RequestParam String codigo) {
            try {
                codigoProfesorServicio.cargarCodigo(codigo);
            } catch (ErrorServicio ex) {
                modelo.put("error", ex.getMessage());
                return "cargarCodigo";
            }
            
            modelo.put("exito", "Su código ha sido creado satisfactoriamente");
            return "inicio";
        }
        
	// TESTEADO
	@GetMapping("/validarProfesor")
	public String validarProfesor() {
		return "validarProfesor";
	}

	// TESTEADO
	@PostMapping("/validacionProfesor")
	public String validarProfesor(ModelMap modelo, @RequestParam String claveingresada) {
		try {
			codigoProfesorServicio.validarProfesor(claveingresada, false);
			modelo.addAttribute("profesor", new Profesor());
		} catch (ErrorServicio ex) {
			modelo.put("error", ex.getMessage());
			return "validarProfesor.html";
		}
		modelo.addAttribute("codigo", claveingresada);
		modelo.addAttribute("profesor", new Profesor());
		return "registroProfesor";
	}

}
