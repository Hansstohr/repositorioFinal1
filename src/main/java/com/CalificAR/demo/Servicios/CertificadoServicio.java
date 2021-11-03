package com.CalificAR.demo.Servicios;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Asistencia;
import com.CalificAR.demo.Entidades.Certificado;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.AsistenciaRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificadoServicio {

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;

    @Autowired
    private AlumnoRepositorio alumnorepositorio;

    public Certificado solicitarCertificado(String alumno_id) throws ErrorServicio {
        //Ver Query (supongamos que esto devuelve un arrayList con tas las asistencias de un alumno) 
        List<Asistencia> respuesta = asistenciaRepositorio.buscarAsistenciaPorAlumno(alumno_id);

        if (respuesta.size()>0) {
            return ValidarCertificado(respuesta);
        } else {
            throw new ErrorServicio("No existen asistencias cargadas. Imposible Generar Certificado");
        }
    }

    private Certificado ValidarCertificado(List<Asistencia> respuesta) throws ErrorServicio {
        int cont = 0;
        for (Asistencia asistencia : respuesta) {
            if (asistencia.getEstado()) {
                cont++;
            }
        }
        
        if ((cont / respuesta.size()) > 0.7) {
            return new Certificado();
        } else {
            throw new ErrorServicio("Su asistencia es inferior al 70% -  Imposible Generar Certificado");
        }
    }

    public Alumno consultarCertificados(String certificado_codigo) throws ErrorServicio {

        Alumno alumno = alumnorepositorio.buscarPorCertificado(certificado_codigo);

        if (alumno != null) {
            return alumno;
        } else {
            throw new ErrorServicio("No existe un certificado para el alumno indicado");
        }
    }

    //TEST POSTMAN
    public Certificado solicitarCertificado(AsistenciaRepositorio asistenciarepositorio, String alumno_id) throws ErrorServicio {
        this.asistenciaRepositorio = asistenciarepositorio;
        return this.solicitarCertificado(alumno_id);
    }
}
