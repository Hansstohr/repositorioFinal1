package com.CalificAR.demo.Entidades;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Nota {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String idNota;
	@ManyToOne
	private Alumno alumno;
	@ManyToOne(cascade = CascadeType.ALL)
	private Materia materia;
	private LocalDate fecha;
	private Double nota;

	public Nota(Alumno alumno, Materia materia, LocalDate fecha, Double nota) {
		this.alumno = alumno;
		this.materia = materia;
		this.fecha = fecha;
		this.nota = nota;
	}

	public Nota() {
	}

	public Nota(Alumno alumno, Materia materia) {
		this.alumno = alumno;
		this.materia = materia;
	}

	public String getIdNota() {
		return idNota;
	}

	public void setIdNota(String idNota) {
		this.idNota = idNota;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Nota{" + "idNota=" + idNota + ", alumno=" + alumno + ", materia=" + materia + ", fecha=" + fecha
				+ ", nota=" + nota + '}';
	}
}
