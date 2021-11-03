package com.CalificAR.demo.Servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.CalificAR.demo.Entidades.Nota;
import com.CalificAR.demo.Entidades.Notas;
import com.CalificAR.demo.Repositorio.NotaRepositorio;

@Service
public class NotaServicio {

	@Autowired
	private NotaRepositorio notaRepositorio;

	public Notas crearNotas(Notas notas) {
		for (Nota nota : notas.getNotas()) {
			// Se guarda el valor de materia adentro de cada nota. Se hizo así para poder pasar una sola vez la materia en el mensaje que llega desde UI
			nota.setMateria(notas.getMateria());
			notaRepositorio.save(nota);
		}
		return notas;
	}

	public List<Nota> crearNotas(List<Nota> notas) {
		for (Nota nota : notas) {
			notaRepositorio.save(nota);
		}
		return notas;
	}

	public List<Nota> obtenerNotas(String idAlumno, String idMateria) {
		return notaRepositorio.obtenerNotas(idAlumno, idMateria);
	}

	public List<Nota> todos() {
		return notaRepositorio.findAll();
	}

	public void consultarNota() {
	}
//    public void modificarNota() {
//        
//    }

	// Testeo Postman
	public Notas crearNotas(NotaRepositorio notaRepositorio, Notas notas) {
		this.notaRepositorio = notaRepositorio;
		return this.crearNotas(notas);
	}

	// Testo Postman
	public List<Nota> todos(NotaRepositorio notaRepositorio) {
		this.notaRepositorio = notaRepositorio;
		return todos();
	}

	// Testo Postman
	public List<Nota> obtenerNotas(NotaRepositorio notaRepositorio, String idAlumno, String idMateria) {
		this.notaRepositorio = notaRepositorio;
		return obtenerNotas(idAlumno, idMateria);
	}
}
