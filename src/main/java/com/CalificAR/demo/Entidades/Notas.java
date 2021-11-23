package com.CalificAR.demo.Entidades;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class Notas {
	private Materia materia;
	private List<Nota> notas;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fecha;

	public Notas() {
	}

	public Notas(Materia materia, List<Nota> notas, LocalDate fecha) {
		this.materia = materia;
		this.notas = notas;
		this.fecha = fecha;
	}

	public Notas(Materia materia, List<Alumno> alumnos) {
		this.fecha = LocalDate.now();
		this.materia = materia;
		notas = new ArrayList<>();
		for (Alumno alumno : alumnos) {
			Nota nota = new Nota(alumno, materia);
			notas.add(nota);
		}
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}
}
