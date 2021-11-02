package com.CalificAR.demo.Entidades;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alumno")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Alumno extends Usuario {

    @OneToMany
    private List<Asistencia> asistencia;

    @ManyToMany
    private List<Materia> materia;

    @OneToMany
    private List<Nota> nota;

    @OneToOne
    private Certificado certificado;

    public Alumno(String dni, String nombre, String apellido, String mail, String clave, LocalDate fechaNac, Foto foto, List<Materia> materias) {
        super(dni, nombre, apellido, mail, clave, fechaNac, foto, materias);
    }

    public Alumno() {
    }

    public List<Asistencia> getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(List<Asistencia> asistencia) {
        this.asistencia = asistencia;
    }

    public List<Materia> getMateria() {
        return materia;
    }

    public void setMateria(List<Materia> materia) {
        this.materia = materia;
    }

    public List<Nota> getNota() {
        return nota;
    }

    public void setNota(List<Nota> nota) {
        this.nota = nota;
    }

    public Certificado getCertificado() {
        return certificado;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }
}
