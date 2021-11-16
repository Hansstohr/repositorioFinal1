package com.CalificAR.demo.Servicios;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Certificado;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Nota;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.CertificadoRepositorio;
import com.CalificAR.demo.Repositorio.NotaRepositorio;

@Service
public class CertificadoServicio {

    //constatnte
    private static final Double NOTA_PROMEDIO_REGULAR = 6.0;
    @Autowired
    private AlumnoRepositorio alumnoRepositorio;
    @Autowired
    private NotaRepositorio notaRepositorio;
    @Autowired
    private CertificadoRepositorio certificadoRepositorio;

    @Transactional(readOnly = true)
    public void validarCertificado(String codigoIngresado, String idAlumno) throws ErrorServicio {
        Optional<Certificado> respuesta = certificadoRepositorio.buscarCodigo(codigoIngresado);
        if (respuesta.isPresent()) {
            solicitarCertificado(idAlumno);
        } else {
            throw new ErrorServicio("El código ingresado es invalido");
        }
    }


    //El certificado se entrega si en una de todas las materias en las que el alumno esta inscripto ese año
    //tiene promedio seis o mas en los examenes dados
    public String solicitarCertificado(String idAlumno) {
        boolean certificadoValido = false;
        String codigo = "";
        Alumno alumno = alumnoRepositorio.findById(idAlumno).get();
        List<Materia> materias = alumno.getMaterias();

        for (Materia materia : materias) {
            Double promedio = 0.0;
            List<Nota> notas = notaRepositorio.obtenerNotasAlumno(idAlumno, materia.getIdMateria());
            for (Nota nota : notas) {
                Double notaValor = nota.getNota();
                promedio = promedio + (notaValor == null ? 0 : notaValor);
            }
            promedio = promedio / notas.size();

            if (promedio >= NOTA_PROMEDIO_REGULAR) {
                certificadoValido = true;
                break;
            }
        }

        if (certificadoValido) {
            Certificado certificado = new Certificado();
            certificado = certificadoRepositorio.save(certificado);
            alumno.setCertificado(certificado);
            alumnoRepositorio.save(alumno);
            codigo = certificado.getCodigo();
        }
        return codigo;
    }

    @Transactional(readOnly = true)
    public Alumno consultarCertificados(String certificado_codigo) throws ErrorServicio {
        Optional<Certificado> certificado = certificadoRepositorio.findById(certificado_codigo);
        if (!certificado.isPresent()) {
            throw new ErrorServicio("No existe un certificado para el código ingresado");
        }
        if (certificado.get().getVencimiento().isBefore(LocalDate.now().plus(3L, ChronoUnit.MONTHS))) {
            throw new ErrorServicio("El certificado expiró. Debe volver a generarlo");
        }
        Alumno alumno = alumnoRepositorio.buscarPorCertificado(certificado_codigo);
        if (alumno == null) {
            throw new ErrorServicio("No existe un certificado para el código ingresado");
        }
        return alumno;
    }
}
