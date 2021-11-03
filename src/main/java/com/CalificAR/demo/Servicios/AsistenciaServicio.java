package com.CalificAR.demo.Servicios;

import com.CalificAR.demo.Entidades.Asistencia;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Repositorio.AsistenciaRepositorio;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.CalificAR.demo.Entidades.Alumno;

@Service
public class AsistenciaServicio {

	@Autowired
	private AsistenciaRepositorio asistenciaRepositorio;
	

	@Transactional
	public Asistencia crearAsistencia(Boolean estado, Materia materia, Alumno alumno) {
		Asistencia asistencia = new Asistencia();
		// Primero anio con 4 digitos, 2do mes con dos digitos // 3ero dia con 2 digitos
//      LocalDate fecha = new LocalDate(leer.nextInt(),leer.nextInt(),leer.nextInt());

//      LocalLocalDate fechaI= LocalLocalDate.of(anioI, mesI, diaI);

		asistencia.setFecha(LocalDate.now());
		asistencia.setAlumno(alumno);
		asistencia.setEstado(estado);
		asistencia.setMateria(materia);
		asistenciaRepositorio.save(asistencia);
		return asistencia;
	}

	@Transactional
	public List<Asistencia> consultarAsistencia(String idAlumno) {
		List<Asistencia> asistencias = asistenciaRepositorio.buscarAsistenciaPorAlumno(idAlumno);
		return asistencias;
	}

	public Asistencia crearAsistencia(AsistenciaRepositorio asistenciaRepositorio, Asistencia asistencia) {
		this.asistenciaRepositorio = asistenciaRepositorio;
		return crearAsistencia(asistencia.getEstado(), asistencia.getMateria(), asistencia.getAlumno());
	}

	// Método para testeos con Postman
	public List<Asistencia> consultarAsistencia(AsistenciaRepositorio asistenciaRepositorio, String idAlumno) {
		this.asistenciaRepositorio = asistenciaRepositorio;
		return consultarAsistencia(idAlumno);
	}
}
