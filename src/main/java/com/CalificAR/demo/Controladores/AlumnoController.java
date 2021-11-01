package com.CalificAR.demo.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.AlumnoExtendido;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import com.CalificAR.demo.Servicios.AlumnoServicio;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

	@Autowired
	AlumnoRepositorio alumnoRepositorio;
	
	@Autowired
	ProfesorRepositorio profesorRepositorio;
	
	AlumnoServicio alumnoServicio = new AlumnoServicio();

	@RequestMapping(value = "/getAlumnos", method = RequestMethod.GET)
	public List<Alumno> getAllAlumnos() {
		List<Alumno> alumnos = alumnoServicio.todos(alumnoRepositorio);
		return alumnos;
	}

	@RequestMapping(value = "/newAlumno", method = RequestMethod.POST)
	public void newAlumno(@RequestBody AlumnoExtendido alumno) throws ErrorServicio {
		alumnoServicio.registrar(alumnoRepositorio, null, alumno.getDni(), alumno.getNombre(), alumno.getApellido(), alumno.getMail(),
				alumno.getClave(), alumno.getClave2(), alumno.getFechaNac());
	}

}
