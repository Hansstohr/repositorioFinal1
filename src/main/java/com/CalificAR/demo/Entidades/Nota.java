package com.CalificAR.demo.Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Nota {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idNota;
    
    private Double nota;

    public Nota(String idNota, Double nota) {
        this.idNota = idNota;
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

    @Override
    public String toString() {
        return "Nota{" + "idNota=" + idNota + ", nota=" + nota + '}';
    }

    
}
