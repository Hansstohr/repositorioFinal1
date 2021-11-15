package com.CalificAR.demo.Servicios;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Asistencia;
import com.CalificAR.demo.Entidades.Asistencias;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.AsistenciaRepositorio;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsistenciaServicio {

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;

    @Autowired
    private AlumnoRepositorio alumnoRepositorio;

    @Transactional
    public Asistencias crearAsistencia(Asistencias asistencias) {
        LocalDate fecha = LocalDate.now();
        for (Asistencia asistencia : asistencias.getAsistencias()) {
            asistencia.setFecha(fecha);
            asistencia.setMateria(asistencias.getMateria());
            asistenciaRepositorio.save(asistencia);
        }
        return asistencias;
    }

    @Transactional(readOnly = true)
    public List<Asistencia> consultarAsistencia(String idAlumno, String idMateria) {
        List<Asistencia> asistencias = asistenciaRepositorio.buscarAsistenciaPorAlumnoYMateria(idAlumno, idMateria);
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
