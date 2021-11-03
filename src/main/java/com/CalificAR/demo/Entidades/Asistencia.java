package com.CalificAR.demo.Entidades;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Asistencia {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String idAsistencia;

//  @Temporal(TemporalType.DATE)
	private LocalDate fecha;
	private Boolean estado;

	@ManyToOne
	private Materia materia;

	@ManyToOne
	private Alumno alumno;

	public Asistencia(LocalDate fecha, Boolean estado, Materia materia) {
		this.fecha = fecha;
		this.estado = estado;
		this.materia = materia;
	}

	public Asistencia() {
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	@Override
	public String toString() {
		return "Asistencia [idAsistencia=" + idAsistencia + ", fecha=" + fecha + ", estado=" + estado + ", materia="
				+ materia + ", alumno=" + alumno + "]";
	}
}
