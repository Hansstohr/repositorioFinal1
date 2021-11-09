package com.CalificAR.demo.Servicios;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Asistencia;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.AsistenciaRepositorio;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsistenciaServicio {

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;

    @Autowired
    private AlumnoRepositorio alumnoRepositorio;

//    @Transactional
//    public Asistencias crearAsistencia(Asistencias asistencias) {
//        LocalDate fecha = LocalDate.now();
//        for(Asistencia asistencia : asistencias.getAsistencias()) {
//            asistencia.setFecha(fecha);
//            asistencia.setMateria(asistencias.getMateria());
//            asistenciaRepositorio.save(asistencia);
//        }
//        return asistencias;
//    }
    @Transactional
    public void crearAsistencia(String id, Boolean estado, Materia materia) throws ErrorServicio {

        LocalDate fecha = LocalDate.now();
        Asistencia asistencia = new Asistencia();
        Optional<Alumno> respuesta = alumnoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            
            Alumno alumno = respuesta.get();
            
            asistencia.setAlumno(alumno);
            asistencia.setFecha(fecha);
            asistencia.setEstado(estado);
            asistencia.setMateria(materia);
        }

        asistenciaRepositorio.save(asistencia);

    }

    @Transactional
    public List<Asistencia> consultarAsistencia(String idAlumno) {
        List<Asistencia> asistencias = asistenciaRepositorio.buscarAsistenciaPorAlumno(idAlumno);
        return asistencias;
    }

//    // Método para testeos con Postman
//    public Asistencias crearAsistencia(AsistenciaRepositorio asistenciaRepositorio, Asistencias asistencias) {
//        this.asistenciaRepositorio = asistenciaRepositorio;
//        return crearAsistencia(asistencias);
//    }
//
//    // Método para testeos con Postman
//    public List<Asistencia> consultarAsistencia(AsistenciaRepositorio asistenciaRepositorio, String idAlumno) {
//        this.asistenciaRepositorio = asistenciaRepositorio;
//        return consultarAsistencia(idAlumno);
//    }
}
