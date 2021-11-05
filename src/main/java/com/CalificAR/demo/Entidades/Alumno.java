package com.CalificAR.demo.Entidades;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "alumno")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Alumno extends Usuario {

    @OneToOne
    private Certificado certificado;

    public Alumno(String dni, String nombre, String apellido, String mail, String clave, LocalDate fechaNac, Foto foto, List<Materia> materias) {
        super(dni, nombre, apellido, mail, clave, fechaNac, foto, materias);
    }

    public Alumno() {
    }
    
    public Certificado getCertificado() {
        return certificado;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }
}
