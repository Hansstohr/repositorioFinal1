package com.CalificAR.demo.Entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Profesor extends Usuario {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;
    @OneToMany
    private List<Materia> materia;

    public Profesor() {
    }

    public Profesor(List<Materia> materia, String dni, String nombre, String apellido, String mail, String clave, Date fechaNac, Foto foto) {
        super(dni, nombre, apellido, mail, clave, fechaNac, foto);
        this.materia = materia;
    }

    public List<Materia> getMateria() {
        return materia;
    }

    public void setMateria(List<Materia> materia) {
        this.materia = materia;
    }

    @Override
    public String toString() {
        return "Profesor{" + "materia=" + materia + '}';
    }
    
    
    
}
