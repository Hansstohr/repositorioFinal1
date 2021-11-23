package com.CalificAR.demo.Servicios;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Nota;
import com.CalificAR.demo.Entidades.Notas;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.NotaRepositorio;

@Service
public class NotaServicio {
	@Autowired
	private NotaRepositorio notaRepositorio;
	@Autowired
	private NotaServicio notaServicio;
	@Autowired
	private AlumnoServicio alumnoServicio;
	@Autowired
	private MateriaServicio materiaServicio;

	@Transactional
	public List<Nota> crearNotas(Notas notas) throws ErrorServicio {
		for (Nota nota : notas.getNotas()) {
			Optional<Alumno> optAlumno = alumnoServicio.buscarPordId(nota.getAlumno().getId());
			Materia materia = materiaServicio.buscarPorId(nota.getMateria().getIdMateria());
			nota.setFecha(notas.getFecha());
			nota.setAlumno(optAlumno.get());
			nota.setMateria(materia);
			notaRepositorio.save(nota);
		}
		return notas.getNotas();
	}

	@Transactional(readOnly = true)
	public List<Nota> obtenerNotasAlumno(String idAlumno, String idMateria) {
		return notaRepositorio.obtenerNotasAlumno(idAlumno, idMateria);
	}

	@Transactional(readOnly = true)
	public List<Nota> obtenerNotas(String idMateria) {
		return notaRepositorio.obtenerNotas(idMateria);
	}

	@Transactional(readOnly = true)
	public List<Nota> crearListaNotas(Alumno alumno, Materia materia, LocalDate fecha, Double nota)
			throws ErrorServicio {
		validarNotas(nota, materia, alumno, fecha);
		List<Nota> listaNotas = new ArrayList();
		Nota notas = new Nota(alumno, materia, fecha, nota);
		listaNotas.add(notas);
		return listaNotas;
	}

	private void validarNotas(Double nota, Materia materia, Alumno alumno, LocalDate fecha) throws ErrorServicio {
		if (nota == null || nota > 10) {
			throw new ErrorServicio("La nota no puede ser nula o estar vacia, además debe ser entre 1 y 10.");
		}
		List<Double> notasTotales = notaServicio.notasPorAlumno(alumno.getId(), materia.getIdMateria());
		if (notasTotales.size() >= 3) {
			throw new ErrorServicio("Solo puede ingresar 3 calificaciones por alumno.");
		}
		LocalDate hoy = LocalDate.now();
		if (fecha == null || (Period.between(fecha, hoy).getYears() > 1)
				|| (hoy.getDayOfMonth() < fecha.getDayOfMonth())) {
			throw new ErrorServicio("Ingrese una fecha válida");
		}
	}

	@Transactional(readOnly = true)
	public List<Double> notasPorAlumno(String idAlumno, String idMateria) {
		List<Nota> respuesta = notaServicio.obtenerNotasAlumno(idAlumno, idMateria);
		List<Double> notasTotales = new ArrayList<Double>();
		for (Nota notas : respuesta) {
			notasTotales.add(notas.getNota());
		}
		return notasTotales;
	}
}
