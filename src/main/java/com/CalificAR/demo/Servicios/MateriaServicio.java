package com.CalificAR.demo.Servicios;

import com.CalificAR.demo.Entidades.Alumno;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.MateriaRepositorio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MateriaServicio {

    @Autowired
    private MateriaRepositorio materiaRepositorio;

    @Autowired
    private AlumnoRepositorio alumnoRepositorio;

    @Autowired
    private ProfesorRepositorio profesorRepositorio;

    @Transactional
    public Materia crearMateria(String nombreMateria) throws ErrorServicio {
        // validar
        validarMateria(nombreMateria);
        Materia materia = new Materia();
        materia.setNombre(nombreMateria);
        return materiaRepositorio.save(materia);
    }

    public void validarMateria(String nombreMateria) throws ErrorServicio {
        // Validamos que el nombre de la materia no sea nulo
        if (nombreMateria == null || nombreMateria.isEmpty()) {
            throw new ErrorServicio("El nombre de la materia no debe ser nulo");
        }
        // Validamos que no haya otra materia con el mismo nombre
        // Preguntar si es distinto de nulo y si es así devuelvo excepción.
        Materia respuesta = materiaRepositorio.buscarPorNombre(nombreMateria);
        if (respuesta != null) {
            throw new ErrorServicio("El nombre de la materia ingresada ya existe");
        }
    }

    public void inscribirMateria(Materia idMateria, String idAlumno) {
        Optional<Alumno> respuesta = alumnoRepositorio.findById(idAlumno);
        if (respuesta.isPresent()) {

            Alumno alumno = respuesta.get();

            List<Materia> materia = new ArrayList();
            materia.add(idMateria);

            alumno.setMateria(materia);
        }

    }

    //ESTE MÉTODO TENDRÍA QUE VERIFICAR PRIMERO SI ES ALUMNO O PROFESOR, PARA NO TENER QUE HACER DOS MÉTODOS
    //PARA TRAER "MIS MATERIAS"
    public List<Materia> materiasAlumno(String idAlumno) {
        Optional<Alumno> respuesta = alumnoRepositorio.findById(idAlumno);

        Alumno alumno = respuesta.get();
        List<Materia> materias = materiaRepositorio.buscarMateriasporAlumno(idAlumno);

        return materias;
    }

    //PARA TRAER "MIS MATERIAS"
    public List<Materia> materiasProfesor(String idProfesor) {
        Optional<Profesor> respuesta = profesorRepositorio.findById(idProfesor);

        Profesor profesor = respuesta.get();
        List<Materia> materias = materiaRepositorio.buscarMateriasporProfesor(idProfesor);

        return materias;
    }

    public List<Materia> todos() {
        return materiaRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Materia buscarPorId(String id) throws ErrorServicio {
        Optional<Materia> respuesta = materiaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Materia materia = respuesta.get();
            return materia;
        } else {
            throw new ErrorServicio("No se encontró la materia solicitada");
        }
    }

//	// Método para testeos con Postman
//	public List<Materia> todos(MateriaRepositorio materiaRepositorio) {
//		this.materiaRepositorio = materiaRepositorio;
//		return todos();
//	}
//
//	// Método para testeos con Postman
//	public Materia crearMateria(MateriaRepositorio materiaRepositorio, String nombreMateria) throws ErrorServicio {
//		this.materiaRepositorio = materiaRepositorio;
//		return crearMateria(nombreMateria);
//	}
}
