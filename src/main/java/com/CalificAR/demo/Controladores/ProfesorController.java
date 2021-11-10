package com.CalificAR.demo.Controladores;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.ProfesorServicio;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {

    @Autowired
    ProfesorServicio profesorServicio;

    @GetMapping("/validarProfesor")
    public String validarProfesor() {
        return "validarProfesor";
    }

    @PostMapping("/validacionProfesor")
    public String validarProfesor(ModelMap modelo, @RequestParam String claveingresada) {
        try {
            profesorServicio.validarProfesor(claveingresada);
            modelo.addAttribute("profesor", new Profesor());
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "validarProfesor.html";
        }
        return "redirect:/profesor/registroProfesor";
    }

    @GetMapping("/registroProfesor")
    public String registro(ModelMap modelo) {
        modelo.addAttribute("profesor", new Profesor());
        return "registroProfesor";
    }

    @PostMapping("/registrarProfesor")
    public String newProfesor(ModelMap modelo, @ModelAttribute Profesor profesor, String clave2, MultipartFile archivo) throws ErrorServicio {
        try {
            profesorServicio.registrar(archivo, profesor.getDni(), profesor.getNombre(), profesor.getApellido(),
                    profesor.getMail(), profesor.getClave(), clave2, profesor.getFechaNac());
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", profesor.getNombre());
            modelo.put("apellido", profesor.getApellido());
            modelo.put("mail", profesor.getMail());
            modelo.put("fechaNac", profesor.getFechaNac());
            return "registroProfesor.html";
        }
        modelo.put("titulo", "Bienvenido a CalificAR");
        modelo.put("descripcion", "Su usuario fue registrado de manera satisfactoria");
        return "inicio.html";
    }
    // REDIRECCIÓN DEL FORMULARIO DE REGISTRO PROFESOR
//	@PostMapping("/registrarProfesor")
//	public String registrarProfesor(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre,
//			@RequestParam String apellido, @RequestParam String dni, @RequestParam String mail,
//			@RequestParam String clave1, @RequestParam String clave2, @RequestParam LocalDate fechaNac)
//			throws ErrorServicio {
//		try {
//			profesorServicio.registrar(archivo, dni, nombre, apellido, mail, clave1, clave2, fechaNac);
//		} catch (ErrorServicio ex) {
//			modelo.put("error", ex.getMessage());
//			modelo.put("nombre", nombre);
//			modelo.put("apellido", apellido);
//			modelo.put("mail", mail);
//			modelo.put("clave1", clave1);
//			modelo.put("clave2", clave2);
//			modelo.put("fechaNac", fechaNac);
//			return "registrarProfesor.html";
//		}
//		modelo.put("titulo", "Bienvenido a CalificAR");
//		modelo.put("descripcion", "Su usuario fue registrado de manera satisfactoria");
//		return "inicio.html";
//	}

    @GetMapping("/modificarProfesor")
    public String modificar(ModelMap modelo) {
        modelo.addAttribute("profesor", new Profesor());
        return "registroProfesor";
    }

    //@PreAuthorize("hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @PostMapping("/modificarProfesor")
    public String modificarProfesor(ModelMap modelo, @ModelAttribute Profesor profesor, MultipartFile archivo) throws ErrorServicio {
        
        try {
            profesorServicio.modificar(profesor.getId(), archivo, profesor.getDni(),
                    profesor.getNombre(), profesor.getApellido(), profesor.getMail(), profesor.getClave(),
                    profesor.getFechaNac());
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", profesor.getNombre());
            modelo.put("apellido", profesor.getApellido());
            modelo.put("mail", profesor.getMail());
            modelo.put("fechaNac", profesor.getFechaNac());
            return "modificarProfesor.html";
        }
        
        return "Perfil.html";
    }

//    @PreAuthorize("hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @GetMapping("/inicio")
    public String crearMateria(ModelMap modelo) {
        return "materia/crearMateria.html";
    }

    @RequestMapping(value = "/getProfesores", method = RequestMethod.GET)
    public List<Profesor> getAllProfesores() {
        List<Profesor> profesores = profesorServicio.todos();
        return profesores;
    }

    // PARA TESTEAR REGISTRO EN POSTMAN
//    {
//    "dni":"39504711",
//    "nombre":"Chino",
//    "apellido":"Vega",
//    "mail":"chinofirmat@gmail.com",
//    "clave":"12345678",
//    "clave2":"12345678",
//    "fechaNac":"1996-04-10"
//    }
}
