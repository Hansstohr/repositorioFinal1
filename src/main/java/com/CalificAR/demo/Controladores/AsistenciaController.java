package com.CalificAR.demo.Controladores;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.CalificAR.demo.Entidades.Asistencia;
import com.CalificAR.demo.Repositorio.AsistenciaRepositorio;
import com.CalificAR.demo.Servicios.AsistenciaServicio;

@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaController {

	@Autowired
	private AsistenciaRepositorio asistenciaRepo;

	private AsistenciaServicio asistenciaServicio = new AsistenciaServicio();

	@RequestMapping(path = "/crearAsistencia", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Asistencia> crearAsistencia(@RequestBody Asistencia asistencia) {
		asistencia = asistenciaServicio.crearAsistencia(asistenciaRepo, asistencia);
		return new ResponseEntity(asistencia, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/consultarAsistenciaAlumno", method = RequestMethod.GET)
	public void consultarAsistencia(Date fecha) {
		asistenciaServicio.consultarAsistencia(asistenciaRepo, fecha);
	}
}
