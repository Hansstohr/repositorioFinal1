package com.CalificAR.demo.Entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMateria;

    private String nombre;
    private List<Double> nota; //PROBLEMA EXCEPCIÓN

    public Materia(Long idMateria, String nombre, List<Double> nota) {
        this.idMateria = idMateria;
        this.nombre = nombre;
        this.nota = nota;
    }

    public Materia() {
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Double> getNota() {
        return nota;
    }

    public void setNota(List<Double> nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Materia{" + "idMateria=" + idMateria + ", nombre=" + nombre + ", nota=" + nota + '}';
    }
    
    
    
}
