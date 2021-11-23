package com.CalificAR.demo.Servicios;
import java.time.LocalDate;
import java.time.Period;
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
		LocalDate hoy = LocalDate.now();
		if (notas.getFecha() == null || (Period.between(notas.getFecha(), hoy).getYears() > 1)
				|| (hoy.getDayOfMonth() < notas.getFecha().getDayOfMonth())) {
			throw new ErrorServicio("Ingrese una fecha válida");
		}
		for (Nota nota : notas.getNotas()) {
			if (nota != null && (nota.getNota() > 10 || nota.getNota() < 0)) {
				throw new ErrorServicio("La nota debe tener un valor entre 1 y 10.");
			}
		}
		for (Nota nota : notas.getNotas()) {
			// No me dejaba guardar el objeto nota ya que alumno y materia no estaban
			// completos.
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
	public List<Nota> notasPorAlumno(String idAlumno, String idMateria) {
		return notaRepositorio.obtenerNotasAlumno(idAlumno, idMateria);
	}
}
