package com.CalificAR.demo.Entidades;

import java.time.LocalDate;
import java.util.List;

public class Notas {

    private Materia materia;
    private List<Nota> notas;
    private LocalDate fecha;

    public Notas() {
    }

    public Notas(Materia materia, List<Nota> notas, LocalDate fecha) {
        this.materia = materia;
        this.notas = notas;
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
}
