package com.CalificAR.demo.Entidades;

import java.util.List;

public class Asistencias {

    private Materia materia;

    private List<Asistencia> asistencias;

    public Asistencias(Materia materia, List<Asistencia> asistencias) {
        this.materia = materia;
        this.asistencias = asistencias;
    }

    private Asistencias() {
    }

    public Materia getMateria() {
        return materia;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

}
