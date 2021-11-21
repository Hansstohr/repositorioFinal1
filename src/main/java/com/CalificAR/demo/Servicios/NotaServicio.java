package com.CalificAR.demo.Servicios;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Materia;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.CalificAR.demo.Entidades.Nota;
import com.CalificAR.demo.Entidades.Notas;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.NotaRepositorio;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotaServicio {

    @Autowired
    private NotaRepositorio notaRepositorio;

    @Autowired
    private NotaServicio notaServicio;

    @Transactional
    public List<Nota> crearNotas(Notas notas) throws ErrorServicio {

        for (Nota nota : notas.getNotas()) {
            // Se guarda el valor de materia adentro de cada nota. Se hizo así para poder pasar una sola vez la materia en el mensaje que llega desde UI
            nota.setMateria(notas.getMateria());
            nota.setFecha(notas.getFecha());
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
    public List<Nota> crearListaNotas(Alumno alumno, Materia materia, LocalDate fecha, Double nota) throws ErrorServicio {
        validarNotas(nota ,materia ,alumno);
        List<Nota> listaNotas = new ArrayList();
        Nota notas = new Nota(alumno, materia, LocalDate.now(), nota);
        listaNotas.add(notas);

        return listaNotas;
    }

    private void validarNotas(Double nota , Materia materia , Alumno alumno) throws ErrorServicio {
        if (nota == null || nota > 10) {
            throw new ErrorServicio("La nota no puede ser nula o estar vacia, además debe ser entre 1 y 10.");
        }
        
        List<Double> notasTotales = notaServicio.notasPorAlumno(alumno.getId(), materia.getIdMateria());
        
        if (notasTotales.size() >= 3) {
            throw new ErrorServicio("Solo puede ingresar 3 calificaciones por alumno.");
        }
    }

    private void validarNotasTotales(int cont) throws ErrorServicio {
    }

    @Transactional(readOnly = true)
    public List<Double> notasPorAlumno(String idAlumno, String idMateria) {
        List<Nota> respuesta = notaServicio.obtenerNotasAlumno(idAlumno, idMateria);
        List<Double> notasTotales = new ArrayList();
        for (Nota notas : respuesta) {
            notasTotales.add(notas.getNota());
        }
        return notasTotales;
    }
}
