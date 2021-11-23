package com.CalificAR.demo.Servicios;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Usuario;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.MateriaRepositorio;

@Service
public class AlumnoServicio extends UsuarioServicio {
	@Autowired
	private AlumnoRepositorio alumnoRepositorio;
	@Autowired
	private MateriaRepositorio materiaRepositorio;

	@Transactional
	public Alumno registrar(MultipartFile archivo, String dni, String nombre, String apellido, String mail,
			String clave, String clave2, LocalDate fechaNacimiento) throws ErrorServicio {
		// Valida los datos del usuario y devuelve una instancia de Usuario.
		Usuario usuario = super.registrarUsuario(alumnoRepositorio, archivo, dni, nombre, apellido, mail, clave, clave2,
				fechaNacimiento, null);
		Alumno alumno = usuario.crearAlumno();
		return alumnoRepositorio.save(alumno);
	}

	@Transactional
	public Alumno modificar(MultipartFile archivo, String dni, String nombre, String apellido, String mail,
			String claveNueva, LocalDate fechaNacimiento, String claveAnterior) throws ErrorServicio {
		String id = alumnoRepositorio.buscarPorDniModificar(dni);
		return super.modificar(alumnoRepositorio, id, archivo, dni, nombre, apellido, mail, claveNueva, fechaNacimiento,
				claveAnterior);
	}

//ESTO
	@Transactional(readOnly = true)
	public List<Alumno> alumnosPorMateria(String idMateria) throws ErrorServicio {
		List<Alumno> alumnos = alumnoRepositorio.findAll();
		Iterator<Alumno> it = alumnos.iterator();
		while (it.hasNext()) {
			Alumno alumno = it.next();
			// Obtiene las materias de un alumno, chequea si alguna de las materias tiene el
			// mismo id que el idMateria pasado por parámetro y en caso de no tener ninguna,
			// remueve el alumno de la lista de alumno
			if (alumno.getMaterias().stream().allMatch(m -> !m.getIdMateria().equals(idMateria))) {
				it.remove();
			}
		}
		if (alumnos.isEmpty()) {
			throw new ErrorServicio("No hay alumnos inscriptos en esta materia");
		}
		return alumnos;
	}

	@Transactional(readOnly = true)
	public Optional<Alumno> buscarPorMail(String mail) {
		return super.buscarPorMail(alumnoRepositorio, mail);
	}

	@Transactional(readOnly = true)
	public Optional<Alumno> buscarPordDni(String dni) {
		return super.buscarPordDni(alumnoRepositorio, dni);
	}

	public ResponseEntity<byte[]> obtenerFoto(String id) throws ErrorServicio {
		return super.obtenerFoto(id, alumnoRepositorio);
	}

	public Optional<Alumno> buscarPordId(String id) {
		return alumnoRepositorio.findById(id);
	}
}
