package com.CalificAR.demo.Entidades;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "profesor")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Profesor extends Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;

    @OneToMany(cascade = CascadeType.ALL)
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
