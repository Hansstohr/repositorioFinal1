package com.CalificAR.demo.Entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Materia {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String idMateria;
	private String nombre;

	//@OneToMany		Da error con esto
	@ElementCollection
	private List<Double> nota;

	public Materia(String idMateria, String nombre, List<Double> nota) {
		this.idMateria = idMateria;
		this.nombre = nombre;
		this.nota = nota;
	}

	public Materia() {
	}

	public String getIdMateria() {
		return idMateria;

	}

	public void setIdMateria(String idMateria) {
		this.idMateria = idMateria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Double> getNota() {
		return nota;
	}

	public void setNota(ArrayList<Double> nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return "Materia{" + "idMateria=" + idMateria + ", nombre=" + nombre + '}';
	}

}
