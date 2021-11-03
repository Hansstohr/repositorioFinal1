package com.CalificAR.demo.Entidades;

import java.util.List;

public class Notas {

	private Materia materia;
	private List<Nota> notas;

	public Notas() {
	}

	public Notas(List<Nota> notas, Materia materia) {
		this.notas = notas;
		this.materia = materia;
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
