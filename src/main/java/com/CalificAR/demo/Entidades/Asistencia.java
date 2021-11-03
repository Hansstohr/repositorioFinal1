package com.CalificAR.demo.Entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Asistencia {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String idAsistencia;

	@Temporal(TemporalType.DATE)
	private Date fecha;
	private Boolean estado;

	@ManyToOne
	private Materia materia;

	@ManyToOne
	private Alumno alumno;

	public Asistencia(Date fecha, Boolean estado, Materia materia) {
		this.fecha = fecha;
		this.estado = estado;
		this.materia = materia;
	}

	public Asistencia() {
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
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
