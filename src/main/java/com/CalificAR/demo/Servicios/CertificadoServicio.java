package com.CalificAR.demo.Servicios;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Asistencia;
import com.CalificAR.demo.Entidades.Certificado;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.AsistenciaRepositorio;
import com.CalificAR.demo.Repositorio.CertificadoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificadoServicio {

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;

    @Autowired
    private AlumnoRepositorio alumnoRepositorio;

    @Autowired
    private CertificadoRepositorio certificadoRepositorio;

    public void solicitarCertificado(String alumno_id) throws ErrorServicio {
        //Ver Query (supongamos que esto devuelve un arrayList con tas las asistencias de un alumno) 
        List<Asistencia> respuesta = asistenciaRepositorio.buscarAsistenciaPorAlumno(alumno_id);

        if (respuesta.size() > 0) {
            ValidarCertificado(respuesta, alumno_id);
        } else {
            throw new ErrorServicio("No existen asistencias cargadas. Imposible Generar Certificado");
        }
    }

    private void ValidarCertificado(List<Asistencia> respuesta, String alumno_id) throws ErrorServicio {
        int cont = 0;
        for (Asistencia asistencia : respuesta) {
            if (asistencia.getEstado()) {
                cont++;
            }
        }

        if ((cont / respuesta.size()) > 0.7) {
            Certificado certificado = certificadoRepositorio.save(new Certificado());
            Optional<Alumno> alumno = alumnoRepositorio.findById(alumno_id);
            alumno.get().setCertificado(certificado);
            alumnoRepositorio.save(alumno.get());
        } else {
            throw new ErrorServicio("Su asistencia es inferior al 70% -  Imposible Generar Certificado");
        }
    }

    public Alumno consultarCertificados(String certificado_codigo) throws ErrorServicio {

        Alumno alumno = alumnoRepositorio.buscarPorCertificado(certificado_codigo);

        if (alumno != null) {
            return alumno;
        } else {
            throw new ErrorServicio("No existe un certificado para el alumno indicado");
        }
    }

    //TEST POSTMAN
//    public void solicitarCertificado(AsistenciaRepositorio asistenciarepositorio, CertificadoRepositorio certificadoRepositorio, AlumnoRepositorio alumnoRepositorio, String alumno_id) throws ErrorServicio {
//        this.asistenciaRepositorio = asistenciarepositorio;
//        this.certificadoRepositorio = certificadoRepositorio;
//        this.alumnoRepositorio = alumnoRepositorio;
//        this.solicitarCertificado(alumno_id);
//    }

    //TESTPOSTMAN
//    public Alumno consultarCertificados(AlumnoRepositorio alumnoRepositorio, String certificado_codigo) throws ErrorServicio {
//        this.alumnoRepositorio = alumnoRepositorio;
//        return this.consultarCertificados(certificado_codigo);
//    }
}

