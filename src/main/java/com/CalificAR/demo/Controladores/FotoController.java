package com.CalificAR.demo.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import com.CalificAR.demo.Servicios.ProfesorServicio;

@Controller
@RequestMapping("/foto")
public class FotoController {

	@Autowired
	private AlumnoServicio alumnoServicio;
	@Autowired
	private ProfesorServicio profesorServicio;

	@PreAuthorize("hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
	@GetMapping("/profesor/{id}")
	public ResponseEntity<byte[]> fotoProfesor(@PathVariable String id) throws ErrorServicio {
		return profesorServicio.obtenerFoto(id);
	}

	// @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
	@GetMapping("/alumno/{id}")
	public ResponseEntity<byte[]> foto(@PathVariable String id) throws ErrorServicio {
		return alumnoServicio.obtenerFoto(id);
	}
}
