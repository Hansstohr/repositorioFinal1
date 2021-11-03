package com.CalificAR.demo.Entidades;

import java.util.List;
import javax.persistence.Entity;


//@Entity
public class Notas {
    
    private List<Nota> notas;

    public Notas() {
    }

    public Notas(List<Nota> notas) {
        this.notas = notas;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

}
