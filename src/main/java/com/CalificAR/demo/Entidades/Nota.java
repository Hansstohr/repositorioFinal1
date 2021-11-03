package com.CalificAR.demo.Entidades;

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
    
    @ManyToOne
    private Materia materia;
    
    private Double nota;

    public Nota(String idNota, Alumno alumno, Materia materia, Double nota) {
        this.idNota = idNota;
        this.alumno = alumno;
        this.materia = materia;
        this.nota = nota;
    }


    public Nota() {
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

    @Override
    public String toString() {
        return "Nota{" + "idNota=" + idNota + ", nota=" + nota + '}';
    }

    
}
