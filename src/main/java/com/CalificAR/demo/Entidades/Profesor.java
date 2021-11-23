package com.CalificAR.demo.Entidades;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "profesor")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Profesor extends Usuario {
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Materia> materias;

	public Profesor() {
	}

	public Profesor(Login login, String nombre, String apellido, String mail, LocalDate fechaNac, Foto foto) {
		super(login, nombre, apellido, mail, fechaNac, foto);
	}

	public List<Materia> getMaterias() {
		return materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}
}
