package com.CalificAR.demo.Servicios;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Asistencia;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Repositorio.AsistenciaRepositorio;

@Service
public class AsistenciaServicio {

	@Autowired
	private AsistenciaRepositorio asistenciaRepositorio;

	@Transactional
	public Asistencia crearAsistencia(Boolean estado, Materia materia, Alumno alumno) {
		Asistencia asistencia = new Asistencia();
		// Primero anio con 4 digitos, 2do mes con dos digitos // 3ero dia con 2 digitos
//        Date fecha = new Date(leer.nextInt(),leer.nextInt(),leer.nextInt());

//       LocalDate fechaI= LocalDate.of(anioI, mesI, diaI);
		asistencia.setFecha(new Date());
		asistencia.setAlumno(alumno);
		asistencia.setEstado(estado);
		asistencia.setMateria(materia);
		asistenciaRepositorio.save(asistencia);
		return asistencia;
	}

	@Transactional
	public Asistencia consultarAsistencia(Date fecha) {
		Asistencia consulta = asistenciaRepositorio.buscarAsistenciaFecha(fecha);
//        if (respuesta.isPresent()) {
//        Asistencia consulta = respuesta.get();
		return consulta;
	}

	public Asistencia crearAsistencia(AsistenciaRepositorio asistenciaRepositorio, Asistencia asistencia) {
		this.asistenciaRepositorio = asistenciaRepositorio;
		return crearAsistencia(asistencia.getEstado(), asistencia.getMateria(), asistencia.getAlumno());
	}

	// Método para testeos con Postman
	public Asistencia consultarAsistencia(AsistenciaRepositorio asistenciaRepositorio, Date fecha) {
		this.asistenciaRepositorio = asistenciaRepositorio;
		return consultarAsistencia(fecha);

	}
}
